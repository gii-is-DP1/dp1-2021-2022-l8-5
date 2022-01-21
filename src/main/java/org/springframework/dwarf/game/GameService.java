package org.springframework.dwarf.game;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dwarf.board.Board;
import org.springframework.dwarf.board.BoardService;
import org.springframework.dwarf.mountainCard.MountainDeck;
import org.springframework.dwarf.player.Player;
import org.springframework.dwarf.player.PlayerService;
import org.springframework.dwarf.user.DuplicatedEmailException;
import org.springframework.dwarf.user.DuplicatedUsernameException;
import org.springframework.dwarf.user.InvalidEmailException;
import org.springframework.dwarf.web.LoggedUserController;
import org.springframework.dwarf.worker.WorkerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Diego Ruiz Gil
 * @author Francisco Javier Migueles Domínguez
 */

@Service
public class GameService {
	
	private GameRepository gameRepo;
	
	@Autowired
	private PlayerService playerService;
	@Autowired
	private WorkerService workerService;
	@Autowired
	private BoardService boardService;
	@Autowired
	private LoggedUserController loggedUserController;
	
	@Autowired
	public GameService(GameRepository gameRepository) {
		this.gameRepo = gameRepository;
	}
	
	@Transactional(readOnly = true)
	public int gamesCount() {
		return (int) gameRepo.count();
	}
	
	@Transactional(readOnly = true)
	public Iterable<Game> findAll() {
		return gameRepo.findAll();
	}
	
	@Transactional(readOnly = true)
	public Optional<Game> findByGameId(int id){
		return gameRepo.findById(id);
	}
	
	@Transactional(readOnly = true)
	public List<Game> findGamesToJoin(){
		List<Game> gamesToJoin = gameRepo.searchGamesToJoin();
		return gamesToJoin.stream().filter(g -> this.findBoardByGameId(g.getId()).orElse(null) == null)
				.collect(Collectors.toList());
	}
	
	@Transactional(readOnly = true)
	public List<Game> findUnfinishedGames(){
		return gameRepo.searchUnfinishedGames();
	}

	@Transactional(readOnly = true)
	public List<Game> findPlayerGames(Player player){
		return gameRepo.searchPlayerGames(player);
	}
	
	@Transactional(readOnly = true)
	public Optional<Game> findPlayerUnfinishedGames(Player player){
		return gameRepo.searchPlayerUnfinishedGames(player);
	}
	
	@Transactional(readOnly = true)
	public List<Game> findPlayerFinishedGames(Player player){
		return gameRepo.searchPlayerFinishedGames(player);
	}
	
	@Transactional(readOnly = true)
	public Optional<Board> findBoardByGameId(Integer gameId){
		return gameRepo.searchBoardByGameId(gameId);
	}
	
	@Transactional(readOnly = true)
	public List<Game> findFinishedGames() {
		return gameRepo.searchFinishedGames();
	}
	
	@Transactional(readOnly = true)
	public List<Game> findCurrentGames() {
		List<Game> games = gameRepo.searchUnfinishedGames();
		return games.stream()
				.filter(game -> findBoardByGameId(game.getId()).isPresent())
				.filter(game -> game.getPlayersList().size() > 1)
				.collect(Collectors.toList());
	}
  
	@Transactional
	public void delete(Game game) {
		gameRepo.delete(game);
	}
	
	@Transactional(readOnly = true)
	public Optional<MountainDeck> searchDeckByGameId(Integer gameId) {
		return gameRepo.searchDeckByGameId(gameId);
	}
	
	@Transactional
	public void exit(Game game, Player playerToRemove) throws DataAccessException {
		workerService.deletePlayerWorker(playerToRemove);
		
		List<Player> newPlayerList = game.getPlayersList();
		newPlayerList.remove(playerToRemove);
		
		int turnPlayerToRemove = -1;
		if(!this.findBoardByGameId(game.getId()).isEmpty())
			turnPlayerToRemove = game.getTurnList().indexOf(playerToRemove);
		
		game.setPlayersPosition(newPlayerList);
		
		if(!this.findBoardByGameId(game.getId()).isEmpty()) {
			this.setPlayersTurns(newPlayerList);
			turnPlayerToRemove = turnPlayerToRemove%game.getPlayersList().size();
			if(playerToRemove.equals(game.getCurrentPlayer()))
				game.setCurrentPlayer(game.getTurnList().get(turnPlayerToRemove));
		}
		
		gameRepo.save(game);
	}
	
	protected void setPlayersTurns(List<Player> newPlayerList) {
		for(int i=0; i<newPlayerList.size(); i++) {
			Player player = newPlayerList.get(i);
			player.setTurn(i+1);
			try {
				playerService.savePlayer(player);
			} catch (DataAccessException | DuplicatedUsernameException | DuplicatedEmailException
					| InvalidEmailException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Transactional
	public void kickOutInactives(Game game) {
		if(loggedUserController.loggedPlayer().equals(game.getCurrentPlayer()))
			this.exit(game, game.getCurrentPlayer());
	}
	
	@Transactional(rollbackFor = CreateGameWhilePlayingException.class)
	public void saveGame(Game game) throws DataAccessException, CreateGameWhilePlayingException {
		List<Player> playerList = game.getPlayersList();
		List<Game> unfinishedGames = gameRepo.searchUnfinishedGames();
		
		for(Game g: unfinishedGames) {
			for(Player player: playerList) {
				if(g.isPlayerInGame(player) && !(g.getId().equals(game.getId())))
					throw new CreateGameWhilePlayingException();
			}
		}
		
		gameRepo.save(game);
	}
	
	@Transactional(rollbackFor = CreateGameWhilePlayingException.class)
	public void joinGame(Game game, Player loggedPlayer) throws DataAccessException, CreateGameWhilePlayingException {
		if(!game.isPlayerInGame(loggedPlayer)) {
			if(game.getSecondPlayer() == null)
				game.setSecondPlayer(loggedPlayer);
			else if(game.getThirdPlayer() == null)
				game.setThirdPlayer(loggedPlayer);
		}
		
		this.saveGame(game);
	}
	
	@Transactional(rollbackFor = CreateGameWhilePlayingException.class)
	public void finishGame(Game game) throws DataAccessException, CreateGameWhilePlayingException {
		if(this.findBoardByGameId(game.getId()).isEmpty())
			return;
		
		Board board = this.findBoardByGameId(game.getId()).get();
		
		this.deleteAllWorkers(game);
		boardService.delete(board);
		
		this.saveGame(game);
	}
	
	protected void deleteAllWorkers(Game game) {
		for(Player player: game.getPlayersList()) {
			workerService.deletePlayerWorker(player);
		}
	}
    
	@Transactional(readOnly = true)
    public Integer getCurrentGameId(Player player) {
    	return gameRepo.searchPlayerIsInGame(player);
    }

	public Boolean alreadyInGame(Player player) {
    	Boolean already = false;
    	for(Game g : this.findUnfinishedGames()) {
    		already = already || g.isPlayerInGame(player);
    	}
    	
    	return already;
    	
    }
}

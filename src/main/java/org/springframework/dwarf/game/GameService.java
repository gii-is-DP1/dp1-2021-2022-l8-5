package org.springframework.dwarf.game;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dwarf.board.Board;
import org.springframework.dwarf.board.BoardService;
import org.springframework.dwarf.mountain_card.MountainDeck;
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
	public GameService(GameRepository gameRepository) {
		this.gameRepo = gameRepository;
	}
	
	@Transactional
	public int gamesCount() {
		return (int) gameRepo.count();
	}
	
	public Iterable<Game> findAll() {
		return gameRepo.findAll();
	}
	
	@Transactional(readOnly = true)
	public Optional<Game> findByGameId(int id){
		return gameRepo.findById(id);
	}
	
	@Transactional(readOnly = true)
	public List<Game> findGamesToJoin(){
		return gameRepo.searchGamesToJoin();
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
	
	public Optional<Board> findBoardByGameId(Integer gameId){
		return gameRepo.searchBoardByGameId(gameId);
	}
  
	public void delete(Game game) {
		gameRepo.delete(game);
	}
	
	public Optional<MountainDeck> searchDeckByGameId(Integer gameId) {
		return gameRepo.searchDeckByGameId(gameId);
	}
	
	public void exit(Game game) throws DataAccessException {	
		Player loggedPlayer = LoggedUserController.loggedPlayer();
		workerService.deletePlayerWorker(loggedPlayer);
		int playerIndex = game.getPlayerPosition(loggedPlayer);
		
		List<Player> newPlayerList = game.getPlayersList();
		newPlayerList.remove(playerIndex);
		game.setPlayersPosition(newPlayerList);
		this.setPlayersTurns(newPlayerList);
		
		gameRepo.save(game);
	}
	
	private void setPlayersTurns(List<Player> newPlayerList) {
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
		boardService.delete(board);
		
		this.deleteAllWorkers(game);
		
		this.saveGame(game);
	}
	
	private void deleteAllWorkers(Game game) {
		for(Player player: game.getPlayersList()) {
			workerService.deletePlayerWorker(player);
		}
	}
    
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

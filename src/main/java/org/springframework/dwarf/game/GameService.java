package org.springframework.dwarf.game;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dwarf.board.Board;
import org.springframework.dwarf.mountain_card.MountainDeck;
import org.springframework.dwarf.player.Player;
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
	
	public Player searchPlayerOneByGame(Integer gameId) {
		return gameRepo.searchPlayerOneByGame(gameId);
	}

	public Player searchPlayerTwoByGame(Integer gameId) {
		return gameRepo.searchPlayerTwoByGame(gameId);
	}

	public Player searchPlayerThreeByGame(Integer gameId) {
		return gameRepo.searchPlayerThreeByGame(gameId);
	}
	
	//All players from the game
	public List<Player> searchPlayersByGame(Integer gameId) {		
		return List.of(searchPlayerOneByGame(gameId), searchPlayerTwoByGame(gameId), searchPlayerThreeByGame(gameId));
	}
	
	public void exit(Game game, Player loggedPlayer) throws DataAccessException {	
		// the first player must delete the game when exit
			if(this.amISecondPlayer(game, loggedPlayer)) {
				game.setSecondPlayer(null);
			}else if (this.amIThirdPlayer(game, loggedPlayer)) {
				game.setThirdPlayer(null);
			}
		
		gameRepo.save(game);
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
	
	private Boolean amISecondPlayer(Game game, Player player){
        return game.getPlayerPosition(player) == 1;
    }
    
    private Boolean amIThirdPlayer(Game game, Player player){
        return game.getPlayerPosition(player) == 2;
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

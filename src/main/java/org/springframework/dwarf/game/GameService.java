package org.springframework.dwarf.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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
	
	public void delete(Game game) {
		gameRepo.delete(game);
	}
	
	public void exit(Game game, Player currentPlayer) throws DataAccessException {
		// the first player must delete the game when exit
		if(!this.amIFirstPlayer(game, currentPlayer)) {
			if(this.amISecondPlayer(game, currentPlayer)) {
				game.setSecondPlayer(null);
			}else {
				game.setThirdPlayer(null);
			}
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
	public void joinGame(Game game, Player currentPlayer) throws DataAccessException, CreateGameWhilePlayingException {
		if(!game.isPlayerInGame(currentPlayer)) {
			if(game.getSecondPlayer() == null)
				game.setSecondPlayer(currentPlayer);
			else if(game.getThirdPlayer() == null)
				game.setThirdPlayer(currentPlayer);
		}
		
		this.saveGame(game);
	}
	
	private Boolean amIFirstPlayer(Game game, Player player){
        return game.getPlayerPosition(player) == 0;
    }

    private Boolean amISecondPlayer(Game game, Player player){
        return game.getPlayerPosition(player) == 1;
    }
    
    public Integer getCurrentGameId(String currentUsername) {
    	if (alreadyInGame(currentUsername)) {
        	for(Game g : this.findUnfinishedGames()) {
        		if(g.firstPlayer != null) {
        			if(g.firstPlayer.getUser().getUsername().equals(currentUsername)) {
        				return g.getId();
        			}
        		}
        		if(g.secondPlayer != null) {
        			if(g.secondPlayer.getUser().getUsername().equals(currentUsername)) {
        				return g.getId();
        			}
        		}
        		if(g.thirdPlayer != null) {
        			if(g.thirdPlayer.getUser().getUsername().equals(currentUsername)) {
        				return g.getId();
        			}
        		}
        	}
    	}
    	
		return -1;
    }

	public Boolean alreadyInGame(String currentUsername) {
    	Boolean already = false;
    	for(Game g : this.findUnfinishedGames()) {
    		if(g.firstPlayer != null) {
    			if(g.firstPlayer.getUser().getUsername().equals(currentUsername)) {
    				return true;
    			}
    		}
    		if(g.secondPlayer != null) {
    			if(g.secondPlayer.getUser().getUsername().equals(currentUsername)) {
    				return true;
    			}
    		}
    		if(g.thirdPlayer != null) {
    			if(g.thirdPlayer.getUser().getUsername().equals(currentUsername)) {
    				return true;
    			}
    		}
    	}
    	
    	return already;
    	
    }
}

package org.springframework.dwarf.game;

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
	
	public void delete(Game game) {
		gameRepo.delete(game);
	}
	
	@Transactional(rollbackFor = CreateGameWhilePlayingException.class)
	public void saveGame(Game game) throws DataAccessException, CreateGameWhilePlayingException {
		List<Player> playerList = game.getPlayerList();
		List<Game> unfinishedGames = gameRepo.searchUnfinishedGames();
		
		for(Game g: unfinishedGames) {
			for(Player player: playerList) {
				if(g.isPlayerInGame(player) && !(g.getId().equals(game.getId())))
					throw new CreateGameWhilePlayingException();
			}
		}
		
		gameRepo.save(game);
	}
}

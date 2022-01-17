/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.dwarf.player;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dwarf.game.CreateGameWhilePlayingException;
import org.springframework.dwarf.game.Game;
import org.springframework.dwarf.game.GameService;
import org.springframework.dwarf.resources.ResourcesService;
import org.springframework.dwarf.user.AuthoritiesService;
import org.springframework.dwarf.user.DuplicatedEmailException;
import org.springframework.dwarf.user.DuplicatedUsernameException;
import org.springframework.dwarf.user.InvalidEmailException;
import org.springframework.dwarf.user.UserService;
import org.springframework.dwarf.worker.WorkerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Mostly used as a facade for all Petclinic controllers Also a placeholder
 * for @Transactional and @Cacheable annotations
 *
 * @author Michael Isvy
 * @author Pablo Marin
 * @autor Pablo Alvarez
 */
@Service
public class PlayerService {

	private PlayerRepository playerRepository;		
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthoritiesService authoritiesService;

	@Autowired
	private GameService gameService;
	
	@Autowired
	private ResourcesService resourcesService;
	
	@Autowired
	private WorkerService workerService;

	@Autowired
	public PlayerService(PlayerRepository playerRepository) {
		this.playerRepository = playerRepository;
	}
	
	@Transactional(readOnly = true)
	public Iterable<Player> findAll() throws DataAccessException {
		return playerRepository.findAll();
	}

	@Transactional(readOnly = true)
	public Player findPlayerById(int id) throws DataAccessException {
		return playerRepository.findById(id).get();
	}

	@Transactional(readOnly = true)
	public Collection<Player> findPlayerByLastName(String lastName) throws DataAccessException {
		return playerRepository.findByLastName(lastName);
	}
	
	@Transactional(readOnly = true)
	public Player findPlayerByUserName(String username) throws DataAccessException {
		Player player = playerRepository.findByUsername(username);
		return player;
	}

	@Transactional(rollbackFor = DuplicatedUsernameException.class)
	public void savePlayer(Player player) throws DataAccessException, DuplicatedUsernameException, DuplicatedEmailException, InvalidEmailException {
		if (getusernameDuplicated(player)){
			throw new DuplicatedUsernameException();
		}
		if (getEmailDuplicated(player)){
			throw new DuplicatedEmailException();
		}
		if (getEmailInvalid(player)){
			throw new InvalidEmailException();
		}
		
		//creating owner
		playerRepository.save(player);		
		//creating user
		userService.saveUser(player.getUser());
		//creating authorities
		authoritiesService.saveAuthorities(player.getUser().getUsername(), "player");
	}
	
	public Boolean getusernameDuplicated(Player player){
		Player otherPlayer=playerRepository.findByUsername(player.getUsername());
		Boolean res =  otherPlayer!= null;
		res= res && otherPlayer.getId()!= player.getId();
		res = res && otherPlayer.getUsername().equals(player.getUsername());
		return res;	
	}
	
		
	public Boolean getEmailDuplicated(Player player){
		Player otherPlayer=playerRepository.findByEmail(player.getEmail());
		Boolean res= otherPlayer!= null;
		res= res && otherPlayer.getId()!= player.getId();
		res = res && otherPlayer.getEmail().equals(player.getEmail());
		return res;	
	}
	
	public Boolean getEmailInvalid(Player player){
		Boolean res= player.getEmail().isBlank();
		return res;	
	}
	

	@Transactional(rollbackFor = DeletePlayerInGameException.class)
	public void delete(Player player) throws DeletePlayerInGameException{
		List<Game> games = gameService.findPlayerGames(player);
		Player placeholder = findPlayerById(0);
		games.stream().forEach(game -> {
			game.deletePlayer(player, placeholder);
			try {
				isExceptionalCase(game);
				gameService.saveGame(game);
			} catch (DataAccessException | CreateGameWhilePlayingException | DeletePlayerInGameException e) {
				e.printStackTrace();
			}
			});
		resourcesService.findByPlayerId(player.getId()).stream().forEach(res -> {
			res.deletePlayer(placeholder);
			try {
				resourcesService.saveResources(res);
			} catch (DataAccessException e) {
				e.printStackTrace();
			}
			});
		workerService.deletePlayerWorker(player);
		playerRepository.delete(player);
		//userService.delete(player.getUser());
	}

	protected void isExceptionalCase(Game game) throws DeletePlayerInGameException {
		if(game.getFinishDate()==null){
			throw new DeletePlayerInGameException();
		}
	}






}

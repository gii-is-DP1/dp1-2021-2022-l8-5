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


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dwarf.owner.Owner;
import org.springframework.dwarf.vet.VetRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Mostly used as a facade for all Petclinic controllers Also a placeholder
 * for @Transactional and @Cacheable annotations
 *
 * @author Pablo Marin
 * @author David Zamora
 * @author Pablo Álvarez
 *
 */
@Service
public class PlayerService {

	
	private PlayerRepository playerRepo;
	
	@Autowired
	public PlayerService(PlayerRepository PlayerRepository) {
		this.playerRepo = PlayerRepository;
	}		
	
	@Transactional
	public int playerCount() {
		return (int) playerRepo.count();
	}

	public Iterable<Player> findAll() {
		return playerRepo.findAll();
	}
	@Transactional(readOnly = true)
	public Optional<Player> findByPlayerId(int id){
		return playerRepo.findById(id);
	}
	
	public void delete(Player player) {
		playerRepo.delete(player);
	}
	
	@Transactional
	public void savePlayer(Player player) throws DataAccessException {
		//creating owner
		playerRepo.save(player);		

	}		
	
}

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

package org.springframework.dwarf.resources;


import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dwarf.game.Game;
import org.springframework.dwarf.player.Player;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author David Zamora
 * @author Jose Ignacio Garcia
 *
 */
@Service
public class ResourcesService {

	
	private ResourcesRepository ResourcesRepo;
	
	@Autowired
	public ResourcesService(ResourcesRepository ResourcesRepository) {
		this.ResourcesRepo = ResourcesRepository;
	}		
	
	@Transactional
	public int ResourcesCount() {
		return (int) ResourcesRepo.count();
	}

	public Iterable<Resources> findAll() {
		return ResourcesRepo.findAll();
	}
	@Transactional(readOnly = true)
	public Optional<Resources> findByResourcesId(int id){
		return ResourcesRepo.findById(id);
	}
	
	@Transactional(readOnly = true)
	public Collection<Resources> findByGameId(int id){
		return ResourcesRepo.findByGameId(id);
	}
	
	@Transactional(readOnly = true)
	public Collection<Resources> findByPlayerId(int id){
		return ResourcesRepo.findByPlayerId(id);
	}

	@Transactional(readOnly = true)
	public Optional<Resources> findByPlayerIdAndGameId(int pid,int gid){
		return ResourcesRepo.findByPlayerIdAndGameId(pid,gid);
	}
	
	
	public void delete(Resources Resources) {
		ResourcesRepo.delete(Resources);
	}
	
	@Transactional
	public void saveResources(Resources Resources) throws DataAccessException {
		ResourcesRepo.save(Resources);		

	}
	
	@Transactional
	public void createPlayerResource(Player player, Game game) {
		Resources playerResource = new Resources(game, player);
		this.saveResources(playerResource);
	}
	
}

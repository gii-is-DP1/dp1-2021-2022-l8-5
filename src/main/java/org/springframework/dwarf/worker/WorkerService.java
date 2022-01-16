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

package org.springframework.dwarf.worker;


import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dwarf.game.Game;
import org.springframework.dwarf.player.Player;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author David Zamora
 * @author José Ignacio García
 *
 */
@Service
@Getter
@Setter
public class WorkerService {

	
	private WorkerRepository workerRepo;
	
	@Autowired
	public WorkerService(WorkerRepository WorkerRepository) {
		this.workerRepo = WorkerRepository;
	}		
	
	@Transactional
	public int WorkerCount() {
		return (int) workerRepo.count();
	}

	public Iterable<Worker> findAll() {
		return workerRepo.findAll();
	}
	@Transactional(readOnly = true)
	public Optional<Worker> findByWorkerId(int id){
		return workerRepo.findById(id);
	}
	
	@Transactional(readOnly = true)
	public Collection<Worker> findByPlayerId(int id){
		return workerRepo.findByPlayerId(id);
	}

	@Transactional(readOnly = true)
	public Collection<Worker> findByPlayerIdAndGameId(int pid, int gid){
		return workerRepo.findByPlayerIdAndGameId(pid,gid);
	}
	
	public List<Worker> findNotPlacedByPlayerIdAndGameId(int pid, int gid){
		return workerRepo.findNotPlacedByPlayerIdAndGameId(pid, gid);
	}
	
	public List<Worker> findNotPlacedByGameId(int gid){
		return workerRepo.findNotPlacedByGameId(gid);
	}
	

	public List<Worker> findPlacedByGameId(int gid){
		return workerRepo.findPlacedByGameId(gid);
	}
	
	public void delete(Worker worker) {
		workerRepo.delete(worker);
	}
	
	public void deletePlayerWorker(Player player) {
		Collection<Worker> Workers = findByPlayerId(player.getId());
		Workers.stream().forEach(worker -> delete(worker));
	}
	
	@Transactional(rollbackFor = IllegalPositionException.class)
	public void saveWorker(Worker worker) throws DataAccessException, IllegalPositionException {
		if(getWorkerInvalid(worker)) {
			throw new IllegalPositionException();
		}
		
		workerRepo.save(worker);		

	}
	
	public Boolean getWorkerInvalid(Worker worker){
		Boolean res=false;
		if (worker.xposition==null || worker.yposition==null) {
			return false;
		} else {
		res = res || !(worker.xposition>=1 && worker.xposition<=3);
		res = res || !(worker.yposition>=0 && worker.yposition<=2);
		return res;	
		}
	}
	
	
	@Transactional
	public void createPlayerWorkers(Player player, Game game, Integer imageNumber) throws IllegalPositionException {
		Worker playerWorker1 = new Worker(player, game, imageNumber);
		Worker playerWorker2 = new Worker(player, game, imageNumber);
		this.saveWorker(playerWorker1);
		this.saveWorker(playerWorker2);
		
	}
	
}

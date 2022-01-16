package org.springframework.dwarf.worker;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Collection;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dwarf.game.Game;
import org.springframework.dwarf.player.Player;
import org.springframework.dwarf.player.PlayerService;
import org.springframework.stereotype.Service;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author David Zamora
 * @author Jose Ignacio Garcia
 */

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class WorkerServiceTest {
    

	@Autowired
	private WorkerService workerService;
	@Autowired
	protected PlayerService playerService;
	
	
	@Test
	public void testCountWithInitialData() {
		int count = workerService.WorkerCount();
		assertEquals(count,1);
	}

	@Test
	public void testFindAll() {
		Iterable<Worker> workers = this.workerService.findAll();
		assertEquals(workers.spliterator().getExactSizeIfKnown(), 1);
	}
	
	
	@Test
	public void testFindByWorkerId() {
		int id = 1;
		
		Optional<Worker> Worker = workerService.findByWorkerId(id);
		System.out.println("------------TEST FIND BY Worker ID------------");
		Worker p = Worker.orElse(null);
		assertEquals(p.getXposition(), 1);
	}
	
	@Test
	public void testFindByPlayerId() {
		int id = 1;
		
		Collection<Worker> Worker = workerService.findByPlayerId(id);
		System.out.println("------------TEST FIND BY Player ID------------");
		assertThat(Worker.size()).isEqualTo(1);
		
	}
	
	@Test
	public void testFindNotPlacedByPlayerIdAndGameId() {
		int pid = 1;
		int gid = 1;
		
		Collection<Worker> Worker = workerService.findNotPlacedByPlayerIdAndGameId(pid,gid);
		assertThat(Worker.size()).isEqualTo(1);
		
	}
	
	@Test
	public void tesFfindNotPlacedByGameId() {
		int gid = 1;
		
		Collection<Worker> Worker = workerService.findNotPlacedByGameId(gid);
		assertThat(Worker.size()).isEqualTo(1);
		
	}
	
	@Test
	public void tesFfindPlacedByGameId() {
		int gid = 1;
		
		Collection<Worker> Worker = workerService.findPlacedByGameId(gid);
		assertThat(Worker.size()).isEqualTo(0);
		
	}
	
	@Test
	public void testFindByPlayerIdAndGameId() {
		int pid = 1;
		int gid = 1;
		
		Collection<Worker> Worker = workerService.findByPlayerIdAndGameId(pid,gid);
		assertThat(Worker.size()).isEqualTo(1);
		
	}
	
	
	@Test
	public void testDeletePlayerWorker() {
		Player player = playerService.findPlayerById(1);
		
		workerService.deletePlayerWorker(player);
		Collection<Worker> Worker = workerService.findByPlayerId(1);
		assertThat(Worker.size()).isEqualTo(0);
	}
	
	@Test
	public void testSaveWorker() throws IllegalPositionException {
		Worker WorkerTest = new Worker();
		WorkerTest.setStatus(true);
		WorkerTest.setXposition(3);
		WorkerTest.setYposition(2);
		
		workerService.saveWorker(WorkerTest);
		int id = WorkerTest.getId();
		
		Optional<Worker> Worker = workerService.findByWorkerId(id);
		System.out.println("------------TEST SAVE Worker------------");
		Worker p = Worker.orElse(null);
		assertEquals(p.getXposition(), 3);
	}
	
	
	@Test
	public void testInvalidWorker() {
		Worker WorkerTest = new Worker();
		WorkerTest.xposition=0;
		WorkerTest.yposition=2;
		
		Boolean res = workerService.getWorkerInvalid(WorkerTest);
		
		assertEquals(res, true);
	}
	
	@Test
	public void testNullWorker() {
		Worker WorkerTest = new Worker();
		WorkerTest.xposition=null;
		WorkerTest.yposition=null;
		
		Boolean res = workerService.getWorkerInvalid(WorkerTest);
		
		assertEquals(res, false);
	}
	
	@Test
	public void testSaveWorkerInvalid() {
		Worker WorkerTest = new Worker();
		WorkerTest.xposition=0;
		WorkerTest.yposition=2;
		
		 assertThrows(IllegalPositionException.class, () -> {
			 workerService.saveWorker(WorkerTest);
		    });
	}
	
	@Test
	public void testCreatePlayerWorkers() throws IllegalPositionException {
		
		Player player = playerService.findPlayerById(1);
		Game game = new Game();
		game.setId(1);
		
		int count = workerService.findByPlayerIdAndGameId(1, 1).size();
		
		workerService.createPlayerWorkers(player, game, 1);
		
		int countAfter = workerService.findByPlayerIdAndGameId(1, 1).size();
		
		assertEquals(count+2, countAfter);
		

	}
	
	@Test
	public void testDeleteWorker() {
		int id = 1;
		
		Optional<Worker> Worker = workerService.findByWorkerId(id);
		System.out.println("------------TEST DELETE Worker------------");
		Worker p = Worker.orElse(null);
		
		if(p != null) {
			workerService.delete(p);
			Worker deletedWorker = workerService.findByWorkerId(id).orElse(null);
			assertEquals(deletedWorker, null);
		}else {
			System.out.println("Worker not found");
		}
		System.out.println("------------------------");
	}
}
package org.springframework.dwarf.worker;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class WorkerServiceTest {
    

	@Autowired
	private WorkerService workerService;
	
	
	@Test
	public void testCountWithInitialData() {
		int count = workerService.WorkerCount();
		assertEquals(count,1);
	}
	
	
	@Test
	public void testFindByWorkerId() {
		int id = 1;
		
		Optional<Worker> Worker = workerService.findByWorkerId(id);
		System.out.println("------------TEST FIND BY Worker ID------------");
		Worker p = Worker.orElse(null);
		//assertEquals(p, null);
		
		if(p != null) {
			System.out.println("Worker username with id: " + id );
		}else {
			System.out.println("Worker not found");
		}
		System.out.println("------------------------");
	}
	
	@Test
	public void testSaveWorker() {
		Worker WorkerTest = new Worker();
		//WorkerTest.setEnabled(true);
		WorkerTest.setPosition(3);
		
		workerService.saveWorker(WorkerTest);
		int id = WorkerTest.getId();
		
		Optional<Worker> Worker = workerService.findByWorkerId(id);
		System.out.println("------------TEST SAVE Worker------------");
		Worker p = Worker.orElse(null);
		if(p != null) {
			System.out.println("Worker username with id " + id );
		}else {
			System.out.println("Worker not found");
		}
		System.out.println("------------------------");
	}
	
	@Test
	public void testDeleteWorker() {
		int id = 2;
		
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


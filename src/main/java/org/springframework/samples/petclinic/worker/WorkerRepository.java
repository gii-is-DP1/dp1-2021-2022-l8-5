package org.springframework.samples.petclinic.worker;

import org.springframework.data.repository.CrudRepository;


public interface WorkerRepository extends  CrudRepository<Worker, Integer>{
	
}

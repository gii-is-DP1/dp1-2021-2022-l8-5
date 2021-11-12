package org.springframework.samples.petclinic.resources;

import org.springframework.data.repository.CrudRepository;


public interface ResourcesRepository extends  CrudRepository<Resources, Integer>{
    
}

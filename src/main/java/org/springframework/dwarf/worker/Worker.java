package org.springframework.samples.petclinic.worker;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.samples.petclinic.model.BaseEntity;

/**
 * Simple JavaBean domain object representing a player.
 *
 * @author Jose Ingacio
 * @author David Zamora
 */

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "workers")
public class Worker extends BaseEntity{
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	
    Integer position;
	

}

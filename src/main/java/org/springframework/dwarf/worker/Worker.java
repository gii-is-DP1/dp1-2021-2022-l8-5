package org.springframework.dwarf.worker;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.dwarf.model.BaseEntity;

/**
 * Simple JavaBean domain object representing a worker.
 *
 * @author Jose Ignacio Garcia
 * @author David Zamora
 */

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "workers")
public class Worker extends BaseEntity{
	
	@Column(name = "position")
    Integer position;
    
	@Column(name = "status")
	Boolean status;

}

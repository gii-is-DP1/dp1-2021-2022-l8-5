package org.springframework.samples.petclinic.worker;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.samples.petclinic.model.BaseEntity;
import org.springframework.samples.petclinic.model.Person;

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
	
	
	/*@OneToMany(cascade = CascadeType.ALL, mappedBy = "player")
	private Set<Authorities> authorities;*/
}

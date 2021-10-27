package org.springframework.samples.petclinic.player;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.samples.petclinic.model.Person;

/**
 * Simple JavaBean domain object representing a player.
 *
 * @author Pablo Marin
 * @author David Zamora
 */

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "players")
public class Player{
	@Id
	String username;
	
	String password;
	
	boolean enabled;
	
	/*@OneToMany(cascade = CascadeType.ALL, mappedBy = "player")
	private Set<Authorities> authorities;*/
}

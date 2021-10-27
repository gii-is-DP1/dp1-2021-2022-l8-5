package org.springframework.samples.petclinic.player;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

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
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	Integer id;
	
	String username;
	
	String password;

	boolean enabled;
	
	@Column(name = "TOTALPOINTS")
	@NotEmpty
	Integer totalPoints;
	
	/*@OneToMany(cascade = CascadeType.ALL, mappedBy = "player")
	private Set<Authorities> authorities;*/
}

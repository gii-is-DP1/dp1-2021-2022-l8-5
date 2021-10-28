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
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.samples.petclinic.model.BaseEntity;
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
public class Player extends BaseEntity{
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	Integer id;
	
	String username;
	
	String password;

	boolean enabled;
	
	@Column(name = "TOTALPOINTS")
	@NotNull
	Integer totalPoints;
	
	/*@OneToMany(cascade = CascadeType.ALL, mappedBy = "player")
	private Set<Authorities> authorities;*/
}

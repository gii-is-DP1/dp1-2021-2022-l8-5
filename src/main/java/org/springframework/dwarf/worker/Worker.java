package org.springframework.dwarf.worker;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import javax.persistence.UniqueConstraint;

import org.springframework.dwarf.game.Game;
import org.springframework.dwarf.model.BaseEntity;
import org.springframework.dwarf.player.Player;

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
@Table(name = "workers", uniqueConstraints = @UniqueConstraint(columnNames = {"playerID", "gameID"}))
public class Worker extends BaseEntity{
	
	@Column(name = "position")
    Integer position;
    
	@Column(name = "status")
	Boolean status;

	@OneToOne(cascade = CascadeType.ALL,orphanRemoval = true)
	@JoinColumn(name= "playerID")
	private Player player;
	
	@OneToOne
	@JoinColumn(name= "gameID")
	private Game game;

}

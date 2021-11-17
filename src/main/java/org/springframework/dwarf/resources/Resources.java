package org.springframework.dwarf.resources;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.springframework.dwarf.game.Game;
import org.springframework.dwarf.model.BaseEntity;
/**
 * Simple JavaBean domain object representing the resources of the game.
 *
 * @author David Zamora
 * @author Jose Ignacio Garcia
 */
import org.springframework.dwarf.player.Player;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "resources", uniqueConstraints = @UniqueConstraint(columnNames = {"playerID", "gameID"}))
public class Resources extends BaseEntity{
	
	@Column(name = "iron")
    Integer iron;
    
	@Column(name = "gold")
    Integer gold;
    
	@Column(name = "steel")
    Integer steel;
    
	@Column(name = "items")
    Integer items;
    
	@Column(name = "badges")
    Integer badges;
	
	@OneToOne
	@JoinColumn(name= "playerID")
	private Player player;
	
	@OneToOne
	@JoinColumn(name= "gameID")
	private Game game;
	

}

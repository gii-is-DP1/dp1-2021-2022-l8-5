package org.springframework.dwarf.resources;

import java.lang.reflect.Method;

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
	
	public Resources (Game game, Player player) {
		this.badges = 0;
		this.gold = 0;
		this.iron = 0;
		this.steel = 0;
		this.game = game;
		this.player = player;
		this.items = 0;
	}
	
	public Resources () {

	}
	
	
	public void deletePlayer(Player sustitute) {
		this.setPlayer(sustitute);
	}
	
	public void setResource(ResourceType resource, Integer amountToAdd) throws Exception {
		String resourceName = this.getResourceName(resource);

		amountToAdd += this.getResourceAmount(resource);

		Method setter = this.getClass().getMethod("set" + resourceName, Integer.class);
		amountToAdd = amountToAdd < 0 ? 0:amountToAdd;
		setter.invoke(this, amountToAdd);
	}
	
	public Integer getResourceAmount(ResourceType resource) throws Exception {
		String resourceName = this.getResourceName(resource);
		Method getter = this.getClass().getMethod("get" + resourceName);
		return (Integer)getter.invoke(this);
	}
	
	// Ej: BADGES --> Badges
	private String getResourceName(ResourceType resource){
		String methodName = resource.toString().toLowerCase();
		methodName = methodName.substring(0,1).toUpperCase() + methodName.substring(1, methodName.length());
		return methodName;
	}

}

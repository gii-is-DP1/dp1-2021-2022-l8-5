package org.springframework.dwarf.card;


import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.jpatterns.gof.StrategyPattern;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.dwarf.model.BaseEntity;
import org.springframework.dwarf.player.Player;

/**
 * Simple JavaBean domain object representing a card.
 *
 * @author Pablo Marin
 * @author Pablo Álvarez
 */

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
@Table(name = "cards")
public class Card extends BaseEntity{
	
	@Column(name = "name")
	@NotEmpty
	String name;
	
	@Column(name = "description")
	@NotEmpty
	String description;
	
	@Column(name = "image")
	String image;
	
	@Transient
	@StrategyPattern.StrategyField
	CardStrategy cardStrategy;
	
	@Column(name = "actiontype")
	@NotNull
	@Enumerated(value = EnumType.STRING)
	StrategyName actionType;
	
	public void cardAction(Player player, ApplicationContext applicationContext, Boolean isSpecial) throws Exception {
		this.setStrategy(applicationContext, isSpecial);
		this.cardStrategy.actions(player,this.getName());
	}
	
	// Ej: DRAGONS_KNOCKERS --> DragonsKnockers
	private String getStrategyClassName(Boolean isSpecial) {
		String strategyName = "";
		String[] split = this.actionType.toString().split("_");
		
		for(String s: split) {
			s = s.toLowerCase();
			s = s.substring(0,1).toUpperCase() + s.substring(1, s.length());
			strategyName += s;
		}
		
		if (isSpecial) {
			return "org.springframework.dwarf.specialCardStrategies." + strategyName;
		}
		
		return "org.springframework.dwarf.mountainCardStrategies." + strategyName;
	}
	
	private void setStrategy(ApplicationContext applicationContext, Boolean isSpecial) {
		try {
			this.cardStrategy = (CardStrategy)applicationContext.getBean(Class.forName(this.getStrategyClassName(isSpecial)));
		} catch (BeansException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}

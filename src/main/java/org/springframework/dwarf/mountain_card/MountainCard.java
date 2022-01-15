package org.springframework.dwarf.mountain_card;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;
import org.jpatterns.gof.StrategyPattern;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.dwarf.card.Card;
import org.springframework.dwarf.card.CardStrategy;
import org.springframework.dwarf.card.StrategyName;
import org.springframework.dwarf.player.Player;

/**
 * Simple JavaBean domain object representing a mountain card.
 *
 * @author Pablo Marin
 * @author Pablo Álvarez
 */

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "mountainCards")
public class MountainCard extends Card{

	@Column(name = "xposition")
	@NotNull
	@Range(min= 0, max= 4)
	Integer xPosition;
	
	@Column(name = "yposition")
	@NotNull
	@Range(min= 0, max= 2)
	Integer yPosition;
	
	@Column(name = "type")
	@NotNull
	@Enumerated(value = EnumType.STRING)
	CardType cardType;

	@Column(name = "cardgroup") 
	@NotNull
	@Range(min= 0, max= 3)
	Integer group;
	
	@Column(name = "actiontype")
	@NotNull
	@Enumerated(value = EnumType.STRING)
	StrategyName actionType;
	
	@Transient
	@StrategyPattern.StrategyField
	CardStrategy cardStrategy;
	
	public void cardAction(Player player, ApplicationContext applicationContext) {
		this.setStrategy(applicationContext);
		this.cardStrategy.actions(player,this.getName());
	}
	
	// Ej: DRAGONS_KNOCKERS --> DragonsKnockers
	private String getStrategyClassName() {
		String strategyName = "";
		String[] split = this.actionType.toString().split("_");
		
		for(String s: split) {
			s = s.toLowerCase();
			s = s.substring(0,1).toUpperCase() + s.substring(1, s.length());
			strategyName += s;
		}
		
		return "org.springframework.dwarf.mountainCardStrategies." + strategyName;
	}
	
	private void setStrategy(ApplicationContext applicationContext) {
		try {
			this.cardStrategy = (CardStrategy)applicationContext.getBean(Class.forName(this.getStrategyClassName()));
		} catch (BeansException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public Integer getPositionXInPixels(Integer size) {
    	return (xPosition)*size;
    }
    
    public Integer getPositionYInPixels(Integer size) {
    	return (yPosition)*size;
    }
}

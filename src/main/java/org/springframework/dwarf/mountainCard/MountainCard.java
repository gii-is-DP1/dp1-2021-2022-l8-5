package org.springframework.dwarf.mountainCard;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;
import org.springframework.dwarf.card.Card;

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
	
	public Integer getPositionXInPixels(Integer size) {
    	return (xPosition)*size;
    }
    
    public Integer getPositionYInPixels(Integer size) {
    	return (yPosition)*size;
    }
}

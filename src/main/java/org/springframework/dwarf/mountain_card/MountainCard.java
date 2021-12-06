package org.springframework.dwarf.mountain_card;



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

	//las cartas de grupo 1 son las 9 iniciales, las de grupo 2 y grupo 3 se mezclan por separado y después se situan las de grupo 2 sobre las de grupo 3, las de grupo 0 son el revés de las cartas especiales
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

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

	@Column(name = "position")
	@NotNull
	@Range(min= 0, max= 8)
	Integer position;
	
	@Column(name = "type")
	@NotNull
	@Enumerated(value = EnumType.STRING)
	CardType cardType;
		
}

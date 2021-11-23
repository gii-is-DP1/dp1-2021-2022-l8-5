package org.springframework.dwarf.special_card;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.dwarf.card.Card;
import org.springframework.dwarf.mountain_card.MountainCard;

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
@Table(name = "specialCards")
public class SpecialCard extends Card{

	@OneToOne(optional = false)
	@JoinColumn(name = "backCard")
	private MountainCard backCard;
		
	
}

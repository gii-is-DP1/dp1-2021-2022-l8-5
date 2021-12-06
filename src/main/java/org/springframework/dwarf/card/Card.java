package org.springframework.dwarf.card;


import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.springframework.dwarf.model.BaseEntity;

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
}

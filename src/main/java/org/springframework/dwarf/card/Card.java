package org.springframework.dwarf.card;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.dwarf.model.BaseEntity;
import org.springframework.dwarf.model.Person;

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


}

package org.springframework.samples.petclinic.card;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.samples.petclinic.model.BaseEntity;
import org.springframework.samples.petclinic.model.Person;

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
@Entity
@Table(name = "cards")
public class Card extends BaseEntity{
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	Integer id;
	
	@Column(name = "name")
	String name;
	
	@Column(name = "description")
	String description;


}

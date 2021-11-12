package org.springframework.samples.petclinic.game;

import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.samples.petclinic.model.BaseEntity;

/**
 * @author Diego Ruiz Gil
 * @author Francisco Javier Migueles Domínguez
 */

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "boardCells")
public class BoardCell extends BaseEntity{
	
	/*
	 * Cuando se añada el tipo MountainCard cambiarlo por Integer
	 * Por alguna razon da problemas por ser lista de Integer
	@NotNull
	private List<Integer> boardCell;
	*/
	
	@ManyToOne
	@JoinColumn(name = "game_id")
	private Game game; 
}

package org.springframework.dwarf.game;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.dwarf.model.BaseEntity;
import org.springframework.dwarf.mountain_card.MountainCard;

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
	
	@NotNull
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "boardCell")
	private List<MountainCard> boardCell;
	
	@ManyToOne
	@JoinColumn(name = "game_id")
	private Game game; 
}

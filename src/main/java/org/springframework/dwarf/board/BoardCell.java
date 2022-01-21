package org.springframework.dwarf.board;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Range;
import org.springframework.dwarf.model.BaseEntity;
import org.springframework.dwarf.mountainCard.MountainCard;
import org.springframework.dwarf.player.Player;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "boardcells")
public class BoardCell extends BaseEntity{
	@Range(min= 1, max= 3)
	Integer xposition;
	
	@Range(min= 0, max= 2)
	Integer yposition;

	@OneToOne
	Player occupiedBy;
	
	Boolean isDisabled;
	
	@ManyToMany
	List<MountainCard> mountaincards;
	
	// para empezar una partida se pide un constructor por defecto
	public BoardCell() {

	}
	
	public BoardCell(Integer xposition, Integer yposition, List<MountainCard> mountaincards) {
		this.xposition = xposition;
		this.yposition = yposition;
		this.mountaincards = new ArrayList<>(mountaincards);
		this.occupiedBy = null;
		this.isDisabled = false;
	}
	
	public boolean isCellOccupied() {
		return this.occupiedBy != null;
	}
}

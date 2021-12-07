package org.springframework.dwarf.board;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Range;
import org.springframework.dwarf.model.BaseEntity;
import org.springframework.dwarf.mountain_card.MountainCard;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "boardcells")
public class BoardCell extends BaseEntity{
	

	Integer xposition;
	
	@Range(min= 0, max= 2)
	Integer yposition;
	
	@ManyToMany
	List<MountainCard> mountaincards;
	
	// para empezar una partida se pide un constructor por defecto
	public BoardCell() {
		//this.xposition = 1;
		//this.yposition = 0;
		//this.mountaincards = new ArrayList<MountainCard>();
	}
	
	public BoardCell(Integer xposition, Integer yposition, List<MountainCard> mountaincards) {
		this.xposition = xposition;
		this.yposition = yposition;
		this.mountaincards = mountaincards;
	}
}

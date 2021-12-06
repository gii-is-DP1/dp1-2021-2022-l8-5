package org.springframework.dwarf.board;


import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

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
	
	@Range(min= 1, max= 3)
	Integer xposition;
	
	@Range(min= 0, max= 2)
	Integer yposition;
	
	@NotNull
	@ManyToMany
	List<MountainCard> mountaincards;
	
	public BoardCell(Integer xposition, Integer yposition, List<MountainCard> mountaincards) {
		this.xposition = xposition;
		this.yposition = yposition;
		this.mountaincards = mountaincards;
	}
}

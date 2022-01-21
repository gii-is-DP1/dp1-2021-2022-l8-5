package org.springframework.dwarf.specialCard;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;
import org.springframework.dwarf.model.BaseEntity;
import org.springframework.dwarf.player.Player;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "specialDecks")
public class SpecialDeck extends BaseEntity {
	
	@Column(name ="xposition")
	@Range(min=0,max=0)
	int xPosition;
	
	@Column(name ="yposition")
    @Range(min=0,max=2)
    int yPosition;
	
	@OneToOne
	Player occupiedBy;

    public Integer getPositionXInPixels(Integer size) {
        return (xPosition)*size;
    }

    public Integer getPositionYInPixels(Integer size) {
        return (yPosition)*size;
    }
	@NotNull
	@ManyToMany
	private List<SpecialCard> specialCard;

	public SpecialDeck() {
		super();
	}

	public SpecialDeck(@Range(min = 0, max = 0) int xPosition, @Range(min = 0, max = 2) int yPosition) {
		super();
		this.xPosition = xPosition;
		this.yPosition = yPosition;
		this.occupiedBy = null;
	}
	
	

}

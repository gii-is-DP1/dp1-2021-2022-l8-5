package org.springframework.dwarf.special_card;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Positive;

import org.hibernate.validator.constraints.Range;
import org.springframework.dwarf.model.BaseEntity;
import org.springframework.dwarf.mountain_card.MountainCard;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "specialDeck")
public class SpecialDeck extends BaseEntity {
	
	@Range(min=0,max=4)
    int xPosition;
    @Range(min=0,max=2)
    int yPosition;

    public Integer getPositionXInPixels(Integer size) {
        return (xPosition)*size;
    }

    public Integer getPositionYInPixels(Integer size) {
        return (yPosition)*size;
    }
	
	@OneToMany
	private List<SpecialCard> specialCard;

}

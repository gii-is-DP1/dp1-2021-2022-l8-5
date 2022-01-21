package org.springframework.dwarf.mountainCard;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;
import org.springframework.dwarf.model.BaseEntity;

/**
 * @author Diego Ruiz Gil
 */

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "mountainDecks")
public class MountainDeck extends BaseEntity{
	@Column(name ="xposition")
	@Range(min=0,max=4)
    int xPosition;
	@Column(name ="yposition")
    @Range(min=0,max=2)
    int yPosition;
    
    public Integer getPositionXInPixels(Integer size) {
    	return (xPosition)*size;
    }
    
    public Integer getPositionYInPixels(Integer size) {
    	return (yPosition)*size;
    }
    
    @NotNull
	@ManyToMany
    List<MountainCard> mountainCards;
    
    
    String image;
    
    public MountainDeck() {
    	this.xPosition = 4;
    	this.yPosition = 2;
    	this.image= "/resources/images/mountainDekc_img.png";
    }
}

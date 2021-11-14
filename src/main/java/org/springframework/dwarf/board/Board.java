package org.springframework.dwarf.board;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.springframework.dwarf.model.BaseEntity;
import org.springframework.dwarf.mountain_card.MountainDeck;

/**
 * @author Diego Ruiz Gil
 * @author Francisco Javier Migueles Domínguez
 */

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "boards")
public class Board extends BaseEntity{
	@Positive
    int width;
    @Positive
    int height;
    
    public Board(){
        this.width=800;
        this.height=800;
    }
    
    @NotNull
    @OneToMany
    List<MountainDeck> mountainDeck;
}

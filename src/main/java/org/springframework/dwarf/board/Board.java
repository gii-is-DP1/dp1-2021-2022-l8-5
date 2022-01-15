package org.springframework.dwarf.board;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.springframework.dwarf.game.Game;
import org.springframework.dwarf.model.BaseEntity;
import org.springframework.dwarf.mountain_card.MountainDeck;
import org.springframework.dwarf.special_card.SpecialDeck;

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
	String background;
	@Positive
    int width;
    @Positive
    int height;
    
    //@OneToMany(mappedBy = "board")
    @OneToMany
    @JoinColumn(name = "BOARD_ID")
    List<BoardCell> boardCells;
    
    @NotNull
    @OneToOne
    @JoinColumn(name = "MOUNTAINDECK")
    MountainDeck mountainDeck;
    
    @OneToMany
    @JoinColumn(name = "BOARD_ID")
    List<SpecialDeck> specialDecks;
    
    @NotNull
    @OneToOne(optional=false)
    @JoinColumn(name = "GAME")
    Game game;
    
    public Board(){
    	this.background ="/resources/images/oro_erebor.jpg";
        this.width=750;
        this.height=600;
    }
    
    public Board(List<BoardCell> boardCells, MountainDeck mountainDeck, Game game, List<SpecialDeck> specialDecks){
    	this.background ="/resources/images/oro_erebor.jpg";
        this.width=750;
        this.height=600;
        
        this.boardCells = boardCells;
        this.mountainDeck = mountainDeck;
        this.game = game;
        this.specialDecks = specialDecks;
    }
    
    public BoardCell getBoardCell(Integer xposition, Integer yposition) {
    	// xposition -> [1,3]
    	// yposition -> [0,2]
    	Integer index = (xposition-1)+(3*yposition);
    	return this.getBoardCells().get(index);
    }
}

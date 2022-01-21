package org.springframework.dwarf.board;

import java.time.LocalTime;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Positive;

import org.springframework.dwarf.game.Game;
import org.springframework.dwarf.model.BaseEntity;
import org.springframework.dwarf.mountainCard.MountainDeck;
import org.springframework.dwarf.specialCard.SpecialDeck;

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
    
    @OneToMany
    @JoinColumn(name = "BOARD_ID")
    List<BoardCell> boardCells;
    
    @OneToOne
    @JoinColumn(name = "MOUNTAINDECK")
    MountainDeck mountainDeck;
    
    @OneToMany
    @JoinColumn(name = "BOARD_ID")
    List<SpecialDeck> specialDecks;
    
    @OneToOne(optional=false)
    @JoinColumn(name = "GAME")
    Game game;
    
    LocalTime inactivityTimer;
    
    public Board(){
    	this.background ="/resources/images/oro_erebor.jpg";
        this.width=750;
        this.height=600;
        this.inactivityTimer = LocalTime.of(0, 2);
    }
    
    public Board(List<BoardCell> boardCells, MountainDeck mountainDeck, Game game, List<SpecialDeck> specialDecks){
    	this.background ="/resources/images/oro_erebor.jpg";
        this.width=750;
        this.height=600;
        this.inactivityTimer = LocalTime.of(0, 2);
        
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

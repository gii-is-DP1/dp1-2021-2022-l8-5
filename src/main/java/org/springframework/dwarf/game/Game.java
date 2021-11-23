package org.springframework.dwarf.game;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.springframework.dwarf.model.BaseEntity;
import org.springframework.dwarf.player.Player;

/**
 * @author Diego Ruiz Gil
 * @author Francisco Javier Migueles Domínguez
 */

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "games")
public class Game extends BaseEntity{
	
	@Transient
	private GameState gameState;

	public Game () {
		this.currentPhase = GamePhase.MINERAL_EXTRACTION;
		this.currentRound = 1;
		this.startDate = LocalDateTime.now();
		setPhase(new MineralExtraction());
	}

	private void setPhase(GameState gameState){
		this.gameState = gameState;
		this.gameState.setGame(this);
	}
	
	@NotNull
	@OneToOne
	@JoinColumn(name = "CURRENTPLAYER", referencedColumnName="id")
	Player currentPlayer;
	
	@NotNull
	@Enumerated(value = EnumType.STRING)
	@Column(name = "CURRENTPHASE")
	GamePhase currentPhase;
	
	@NotNull
	@Column(name = "CURRENTROUND")
	Integer currentRound;
	
	@NotNull
	@OneToOne
	@JoinColumn(name = "FIRSTPLAYER", referencedColumnName="id")
	Player firstPlayer;
	
	@OneToOne
	@JoinColumn(name = "SECONDPLAYER", referencedColumnName="id")
	Player secondPlayer;
	
	@OneToOne
	@JoinColumn(name = "THIRDPLAYER", referencedColumnName="id")
	Player thirdPlayer;
	
	@NotNull
	@Column(name = "STARTDATE")
	LocalDateTime startDate;
	
	@Column(name = "FINISHDATE")
	LocalDateTime finishDate;
	
	

    public Boolean allPlayersSet(){
        Boolean allSet = true;

        allSet = allSet && this.firstPlayer != null;
        allSet = allSet && this.secondPlayer != null;
        allSet = allSet && this.thirdPlayer != null;

        return allSet;
    }
    
}

package org.springframework.dwarf.game;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
	
	
	public List<Player> getPlayerList(){
		List<Player> pList = new ArrayList<Player>();
		
		if(this.firstPlayer != null)
			pList.add(this.firstPlayer);
		if(this.secondPlayer != null)
			pList.add(this.secondPlayer);
		if(this.thirdPlayer != null)
			pList.add(this.thirdPlayer);
		
		return pList;
	}
	
    public Boolean allPlayersSet(){
        return this.getPlayerList().size() == 3;
    }
    
    public Boolean isPlayerInGame(Player player) {
		return this.getPlayerList().contains(player);
	}
    
}

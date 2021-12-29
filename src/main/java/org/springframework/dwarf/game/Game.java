package org.springframework.dwarf.game;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.jpatterns.gof.StatePattern;
import org.springframework.dwarf.board.BoardCellService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dwarf.game.GameState.GamePhase;
import org.springframework.dwarf.model.BaseEntity;
import org.springframework.dwarf.mountain_card.MountainDeckService;
import org.springframework.dwarf.player.Player;
import org.springframework.dwarf.worker.WorkerService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Diego Ruiz Gil
 * @author Francisco Javier Migueles Domínguez
 * @author Pablo Álvarez Caro
 */

@Getter
@Setter
@Entity
@Table(name = "games")
@StatePattern.Context
public class Game extends BaseEntity{

	public Game () {
		this.currentPhaseName = GamePhaseEnum.MINERAL_EXTRACTION;
		this.currentRound = 1;
		this.startDate = LocalDateTime.now();
	}
	
	public void setPhase(GamePhase gamePhase) {
		this.currentPhaseName = gamePhase.getPhaseName();
	}
	
	public void phaseResolution(WorkerService ws, GameService gs, MountainDeckService mds, BoardCellService bcs) {
		this.getPhase(ws,gs,mds,bcs).phaseResolution(this);
	}
	
	public GamePhase getPhase(WorkerService ws, GameService gs, MountainDeckService mds, BoardCellService bcs) {
		GamePhase phase;
		switch (this.currentPhaseName) {
			case MINERAL_EXTRACTION:
				phase = new MineralExtraction(ws, gs, mds, bcs);
				break;
			case ACTION_SELECTION:
				phase = new ActionSelection(ws, gs, mds, bcs);
				break;
			case ACTION_RESOLUTION:
				phase = new ActionResolution(ws, gs, mds, bcs);
				break;
			default:
				phase = new MineralExtraction(ws, gs, mds, bcs);
				break;
		}
		return phase;
	}
	
	@NotNull
	@OneToOne
	@JoinColumn(name = "CURRENTPLAYER", referencedColumnName="id")
	Player currentPlayer;
	
	@NotNull
	@Enumerated(value = EnumType.STRING)
	@Column(name = "CURRENTPHASE")
	GamePhaseEnum currentPhaseName;
	
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
	
	
	public List<Player> getPlayersList(){
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
        return this.getPlayersList().size() == 3;
    }
    
    public Boolean isPlayerInGame(Player player) {
		return this.getPlayersList().contains(player);
	}
    
    public Integer getPlayerPosition(Player player) {
    	return this.getPlayersList().indexOf(player);
    }

    // must be called when a player is deleted
	public void deletePlayer(Player player, Player sustitute) {
		Integer position = this.getPlayerPosition(player)+1;
		if(currentPlayer.equals(player)) {
			this.setCurrentPlayer(sustitute);
		}
		if(position==1) {
			this.setFirstPlayer(sustitute);
		}
		else if(position==2) {
			this.setSecondPlayer(sustitute);
		}
		else if(position==3) {
			this.setThirdPlayer(sustitute);
		}
	}
}

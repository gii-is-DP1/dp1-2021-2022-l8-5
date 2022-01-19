package org.springframework.dwarf.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.jpatterns.gof.StatePattern;
import org.springframework.context.ApplicationContext;
import org.springframework.dwarf.model.BaseEntity;
import org.springframework.dwarf.player.Player;
import org.springframework.dwarf.player.PlayerComparator;
import org.springframework.format.annotation.DateTimeFormat;

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
		this.startDate = new Date();
		this.canResolveActions = true;
		this.musterAnArmyEffect = false;
	}
	
	public void setPhase(GamePhaseEnum gamePhaseName) {
		this.currentPhaseName = gamePhaseName;
	}
	
	public void phaseResolution(ApplicationContext applicationContext) throws Exception {
		this.getPhase(applicationContext).phaseResolution(this);
	}
	
	public GamePhase getPhase(ApplicationContext applicationContext) {
		GamePhase phase;
		switch (this.currentPhaseName) {
			case MINERAL_EXTRACTION:
				phase = applicationContext.getBean(MineralExtraction.class);
				break;
			case ACTION_SELECTION:
				phase = applicationContext.getBean(ActionSelection.class);
				break;
			case ACTION_RESOLUTION:
				phase = applicationContext.getBean(ActionResolution.class);
				break;
			default:
				phase = applicationContext.getBean(MineralExtraction.class);
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
	
	//@NotNull
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
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@Column(name = "STARTDATE")
	Date startDate;
	
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@Column(name = "FINISHDATE")
	Date finishDate;
	
	@Column(name = "CANRESOLVEACTIONS")
	Boolean canResolveActions;
	
	@Column(name = "MUSTERANARMYEFFECT")
	Boolean musterAnArmyEffect;
	
	// @Getter no pilla el atributo, pero el @Setter si
	public Boolean getCanResolveActions() {
		return this.canResolveActions;
	}
	// @Getter no pilla el atributo, pero el @Setter si
	public Boolean getMusterAnArmyEffect() {
		return this.musterAnArmyEffect;
	}
	
	public List<Player> getPlayersList() {
		List<Player> pList = new ArrayList<Player>();
		
		if(this.firstPlayer != null)
			pList.add(this.firstPlayer);
		if(this.secondPlayer != null)
			pList.add(this.secondPlayer);
		if(this.thirdPlayer != null)
			pList.add(this.thirdPlayer);
		
		return pList;
	}
	
	public void setPlayersPosition(List<Player> players) {
		Integer listSize = players.size();
		for(int i=0; i<3; i++) {
			if(i <= listSize-1)
				this.setPlayer(i, players.get(i));
			else
				this.setPlayer(i, null);
		}
	}
	
	private void setPlayer(int index, Player player) {
		switch (index) {
			case 0:
				this.setFirstPlayer(player);
				break;
			case 1:
				this.setSecondPlayer(player);
				break;
			case 2:
				this.setThirdPlayer(player);
				break;
			default:
				break;
		}
	}
	
	public List<Player> getTurnList(){
		List<Player> pList = this.getPlayersList();
		Collections.sort(pList, new PlayerComparator());
		
		return pList;
	}
	
    public Boolean allPlayersSet(){
    	// can start a game with 2 players
        return this.getPlayersList().size() >= 2;
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

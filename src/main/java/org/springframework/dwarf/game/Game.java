package org.springframework.dwarf.game;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
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
	
	@NotNull
	@OneToOne
	@JoinColumn(name = "SECONDPLAYER", referencedColumnName="id")
	Player secondPlayer;
	
	@NotNull
	@OneToOne
	@JoinColumn(name = "THIRDPLAYER", referencedColumnName="id")
	Player thirdPlayer;
	
	@NotNull
	@Column(name = "STARTDATE")
	LocalDateTime startDate;
	
	@Column(name = "FINISHDATE")
	LocalDateTime finishDate;
    
}

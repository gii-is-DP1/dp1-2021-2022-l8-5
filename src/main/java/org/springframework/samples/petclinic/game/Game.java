package org.springframework.samples.petclinic.game;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.samples.petclinic.model.BaseEntity;
import org.springframework.samples.petclinic.player.Player;

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
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "game")
	private List<BoardCell> board;
	
	@NotNull
	@OneToOne
	@JoinColumn(name = "currentPlayer")
	private Player currentPlayer;
	
	@NotNull
	private GamePhase currentPhase;
	
	@NotNull
	private Integer currentRound;
	
	@NotNull
	@OneToOne
	@JoinColumn(name = "firstPlayer")
	private Player firstPlayer;
	
	@NotNull
	@OneToOne
	@JoinColumn(name = "secondPlayer")
	private Player secondPlayer;
	
	@NotNull
	@OneToOne
	@JoinColumn(name = "thirdPlayer")
	private Player thirdPlayer;
	
	@NotNull
	@Column(name = "startDate")
	private LocalDate startDate;
	
	@Column(name = "finishDate")
	private LocalDate finishDate;
    
}

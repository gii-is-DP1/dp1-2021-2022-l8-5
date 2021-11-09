package org.springframework.samples.petclinic.game;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.samples.petclinic.model.BaseEntity;
import org.springframework.samples.petclinic.player.Player;

/**
 * Simple JavaBean domain object representing a player.
 *
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
	
	@NotEmpty
	// cuando se añada el tipo MountainCard cambiarlo por Integer
	List<List<Integer>> board;
	
	@NotEmpty
	Player currentPlayer;
	
	@NotEmpty
	GamePhase currentPhase;
	
	@NotEmpty
	Integer currentRound;
	
	@Column(name = "firstPlayer")
	@NotEmpty
	Player firstPlayer;
	
	@Column(name = "secondPlayer")
	@NotEmpty
	Player secondPlayer;
	
	@Column(name = "thirdPlayer")
	@NotEmpty
	Player thirdPlayer;
	
	
	@Column(name = "startDate")
	@NotEmpty
	LocalDate startDate;
	
	@Column(name = "finishDate")
	LocalDate finishDate;
    
}

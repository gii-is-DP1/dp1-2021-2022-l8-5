package org.springframework.dwarf.specialCardStrategies;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.jpatterns.gof.StrategyPattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dwarf.board.Board;
import org.springframework.dwarf.board.BoardCell;
import org.springframework.dwarf.board.BoardCellService;
import org.springframework.dwarf.board.BoardService;
import org.springframework.dwarf.card.CardStrategy;
import org.springframework.dwarf.card.StrategyName;
import org.springframework.dwarf.game.GameService;
import org.springframework.dwarf.mountainCard.MountainCard;
import org.springframework.dwarf.player.Player;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@StrategyPattern.ConcreteStrategy
@Component
public class RunAmok implements CardStrategy {
	
	
	//Barajar todas las cartas de cada posición y volver a colocarlas.
	@Autowired
	private GameService gs;
	@Autowired
	private BoardCellService bcs;
	@Autowired
	private BoardService bs;
	
	@Override
	public void actions(Player player, String cardName) {
		log.debug(player.getUsername() + ", con id" + player.getId() + ", ha realizado la accion " + this.getName().toString());
		
		Integer gameId = gs.getCurrentGameId(player);
		Board tablero = gs.findBoardByGameId(gameId).get();	
	
		List<BoardCell> cartasTablero = tablero.getBoardCells();
		
		for(int i=0; i<cartasTablero.size(); i++) {
			BoardCell celdai = cartasTablero.get(i);
			List<MountainCard> cartasCeldai = celdai.getMountaincards();
			
			ArrayList<MountainCard> cartasCeldaiAux = new ArrayList<>(cartasCeldai);	//Shuffle necesita usar ArrayList, por eso este auxiliar
			Collections.shuffle(cartasCeldaiAux);	//"Baraja" la lista de cartas de la celda i
			
			celdai.setMountaincards(cartasCeldaiAux.stream().collect(Collectors.toList()));
			
			bcs.saveBoardCell(celdai);
		}
		
		bs.saveBoard(tablero);	
	}

	@Override
	public StrategyName getName() {
		return StrategyName.RUN_AMOK;
	}

}

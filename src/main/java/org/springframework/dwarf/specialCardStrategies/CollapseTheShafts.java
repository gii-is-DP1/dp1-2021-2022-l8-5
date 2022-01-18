package org.springframework.dwarf.specialCardStrategies;

import java.util.List;

import org.jpatterns.gof.StrategyPattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dwarf.board.Board;
import org.springframework.dwarf.board.BoardCell;
import org.springframework.dwarf.board.BoardCellService;
import org.springframework.dwarf.board.BoardService;
import org.springframework.dwarf.card.CardStrategy;
import org.springframework.dwarf.game.GameService;
import org.springframework.dwarf.mountain_card.MountainCard;
import org.springframework.dwarf.player.Player;
import org.springframework.stereotype.Component;
import org.springframework.dwarf.card.StrategyName;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@StrategyPattern.ConcreteStrategy
@Component
public class CollapseTheShafts implements CardStrategy {
	
	//Colocar la carta superior de cada celda del tablero en la parte inferior.
	
	@Autowired
	private GameService gs;
	@Autowired
	private BoardCellService bcs;
	@Autowired
	private BoardService bs;
	
	@Override
	public void actions(Player player, String cardName) {
		Integer gameId = gs.getCurrentGameId(player);
		Board tablero = gs.findBoardByGameId(gameId).get();	
		fromTopToBottom(tablero);
		bs.saveBoard(tablero);
	}
	
	protected void fromTopToBottom (Board tablero) {
		List<BoardCell> boardcells = tablero.getBoardCells();
		for(int i=0; i<boardcells.size(); i++) {
			BoardCell celdai = boardcells.get(i);
			List<MountainCard> cartasCeldai = celdai.getMountaincards();
			if(cartasCeldai.size() > 1) {	//Si solo hay una carta en la celda no hacemos nada
				MountainCard cartaArriba = cartasCeldai.remove(0);
				cartasCeldai.add(cartaArriba);
			}
			celdai.setMountaincards(cartasCeldai);
			bcs.saveBoardCell(celdai); 
		}
	}

	@Override
	public StrategyName getName() {
		return StrategyName.COLLAPSE_THE_SHAFTS;
	}

}

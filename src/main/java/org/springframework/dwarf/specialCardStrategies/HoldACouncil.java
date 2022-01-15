package org.springframework.dwarf.specialCardStrategies;

import java.util.ArrayList;
import java.util.List;
import org.jpatterns.gof.StrategyPattern;
import org.springframework.dwarf.board.Board;
import org.springframework.dwarf.board.BoardCell;
import org.springframework.dwarf.board.BoardCellService;
import org.springframework.dwarf.board.BoardService;
import org.springframework.dwarf.card.CardStrategy;
import org.springframework.dwarf.game.Game;
import org.springframework.dwarf.game.GameService;
import org.springframework.dwarf.mountain_card.MountainCard;
import org.springframework.dwarf.mountain_card.MountainDeck;
import org.springframework.dwarf.mountain_card.MountainDeckService;
import org.springframework.dwarf.player.Player;
import org.springframework.dwarf.card.StrategyName;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@StrategyPattern.ConcreteStrategy
public class HoldACouncil implements CardStrategy {
	
	GameService gameService;
	BoardService boardService;
	BoardCellService boardCellService;
	MountainDeckService mountainDeckService;
	
	//Quitar todas las cartas superiores del tablero (si solo hay una en una posición, se deja) y devolverlas al mazo de montaña.
	
	@Override
	public void actions(Player player, String cardName) {
		log.debug(player.getUsername() + ", con id" + player.getId() + ", ha realizado la accion " + this.getName().toString());
		
		Game currentGame = gameService.findPlayerUnfinishedGames(player).get();
		Board board = gameService.findBoardByGameId(currentGame.getId()).get();
		// se podria hacer una query que te diese el mazo de montaña con el game id
		MountainDeck mountainDeck = board.getMountainDeck();
		List<MountainCard> removedCards = this.removeTopCards(currentGame);
		
		mountainDeck.getMountainCards().addAll(removedCards);
		mountainDeckService.saveMountainDeck(mountainDeck);
	}
	
	private List<MountainCard> removeTopCards(Game currentGame){
		List<MountainCard> cardsToRemove = new ArrayList<MountainCard>();
		Board board = gameService.findBoardByGameId(currentGame.getId()).get();
		
		for(BoardCell boardCell: board.getBoardCells()) {
			List<MountainCard> listRemoveTop = boardCell.getMountaincards();
			MountainCard cardRemoved = listRemoveTop.remove(0);
			
			boardCell.setMountaincards(listRemoveTop);
			cardsToRemove.add(cardRemoved);
			
			boardCellService.saveBoardCell(boardCell);
		}
		
		
		return cardsToRemove;
	}

	@Override
	public StrategyName getName() {
		return StrategyName.HOLD_COUNCIL;
	}
	
}

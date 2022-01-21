package org.springframework.dwarf.specialCardStrategies;

import java.util.ArrayList;
import java.util.List;
import org.jpatterns.gof.StrategyPattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dwarf.board.Board;
import org.springframework.dwarf.board.BoardCell;
import org.springframework.dwarf.board.BoardCellService;
import org.springframework.dwarf.board.BoardService;
import org.springframework.dwarf.card.CardStrategy;
import org.springframework.dwarf.game.Game;
import org.springframework.dwarf.game.GameService;
import org.springframework.dwarf.mountainCard.MountainCard;
import org.springframework.dwarf.mountainCard.MountainDeck;
import org.springframework.dwarf.mountainCard.MountainDeckService;
import org.springframework.dwarf.player.Player;
import org.springframework.stereotype.Component;
import org.springframework.dwarf.card.StrategyName;


@StrategyPattern.ConcreteStrategy
@Component
public class HoldACouncil implements CardStrategy {
	
	@Autowired
	GameService gameService;
	@Autowired
	BoardService boardService;
	@Autowired
	BoardCellService boardCellService;
	@Autowired
	MountainDeckService mountainDeckService;
	
		
	@Override
	public void actions(Player player, String cardName) {
		Game currentGame = gameService.findPlayerUnfinishedGames(player).get();
		Board board = gameService.findBoardByGameId(currentGame.getId()).get();
		
		MountainDeck mountainDeck = board.getMountainDeck();
		List<MountainCard> removedCards = this.removeTopCards(currentGame, board);
		
		mountainDeck.getMountainCards().addAll(removedCards);
		mountainDeckService.saveMountainDeck(mountainDeck);
	}
	
	protected List<MountainCard> removeTopCards(Game currentGame, Board board){
		List<MountainCard> cardsToRemove = new ArrayList<MountainCard>();
		
		for(BoardCell boardCell: board.getBoardCells()) {
			List<MountainCard> listRemoveTop = boardCell.getMountaincards();
			if (listRemoveTop.size()>1) {	
				MountainCard cardRemoved = listRemoveTop.remove(0);
				boardCell.setMountaincards(listRemoveTop);
				cardsToRemove.add(cardRemoved);
			}
			
			boardCellService.saveBoardCell(boardCell);
		}
		
		return cardsToRemove;
	}

	@Override
	public StrategyName getName() {
		return StrategyName.HOLD_A_COUNCIL;
	}
	
}

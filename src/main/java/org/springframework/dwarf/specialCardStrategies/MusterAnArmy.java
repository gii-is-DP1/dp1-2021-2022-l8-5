package org.springframework.dwarf.specialCardStrategies;

import java.util.ArrayList;
import java.util.List;

import org.jpatterns.gof.StrategyPattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dwarf.board.Board;
import org.springframework.dwarf.board.BoardCell;
import org.springframework.dwarf.game.Game;
import org.springframework.dwarf.game.GameService;
import org.springframework.dwarf.mountain_card.CardType;
import org.springframework.dwarf.player.Player;
import org.springframework.stereotype.Component;
import org.springframework.dwarf.card.StrategyName;
import org.springframework.dwarf.card.CardStrategy;

@StrategyPattern.ConcreteStrategy
@Component
public class MusterAnArmy implements CardStrategy{

	@Autowired
	GameService gameService;

	@Autowired
	public MusterAnArmy(GameService gameService) {
		this.gameService = gameService;
	}

	@Override
	public void actions(Player player, String cardName) {
		/*
			Game currentGame = gameService.findPlayerUnfinishedGames(player).get();
			List<BoardCell> listHelpCards = this.getGetHelpCardsInBoard(currentGame);

			for(BoardCell boardCell: listHelpCards) {
				boardCell.setCellOccupied(true);
			}
		 */
	}

	protected List<BoardCell> getGetHelpCardsInBoard(Game currentGame){
		List<BoardCell> listHelpCards = new ArrayList<BoardCell>();
		Board board = gameService.findBoardByGameId(currentGame.getId()).get();

		for(BoardCell boardCell: board.getBoardCells()) {
			if(boardCell.getMountaincards().get(0).getCardType().equals(CardType.AID))
				listHelpCards.add(boardCell);
		}

		return listHelpCards;
	}

	@Override
	public StrategyName getName() {
		return StrategyName.MUSTER_AN_ARMY;
	}
}

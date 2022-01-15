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
import org.springframework.dwarf.card.StrategyName;
import org.springframework.dwarf.card.CardStrategy;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@StrategyPattern.ConcreteStrategy
public class MusterAnArmy implements CardStrategy{

		
	GameService gameService;

	@Autowired
	public MusterAnArmy(GameService gameService) {
		this.gameService = gameService;
	}

	@Override
	public void actions(Player player, String cardName) {
		log.debug(player.getUsername() + ", con id" + player.getId() + ", ha realizado la accion " + this.getName().toString());
		/*
			Game currentGame = gameService.findPlayerUnfinishedGames(player).get();
			List<BoardCell> listHelpCards = this.getGetHelpCardsInBoard(currentGame);

			for(BoardCell boardCell: listHelpCards) {
				boardCell.setCellOccupied(true);
			}
		 */
	}

	private List<BoardCell> getGetHelpCardsInBoard(Game currentGame){
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
		return StrategyName.MUSERT_ARMY;
	}
}

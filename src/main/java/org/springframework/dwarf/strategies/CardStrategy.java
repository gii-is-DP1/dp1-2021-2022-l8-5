package org.springframework.dwarf.strategies;

import java.util.ArrayList;
import java.util.List;

import org.jpatterns.gof.StrategyPattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dwarf.board.Board;
import org.springframework.dwarf.board.BoardCell;
import org.springframework.dwarf.board.BoardCellService;
import org.springframework.dwarf.board.BoardService;
import org.springframework.dwarf.game.Game;
import org.springframework.dwarf.game.GameService;
import org.springframework.dwarf.mountain_card.CardType;
import org.springframework.dwarf.mountain_card.MountainCard;
import org.springframework.dwarf.mountain_card.MountainDeckService;
import org.springframework.dwarf.player.Player;
import org.springframework.dwarf.resources.ResourceType;
import org.springframework.dwarf.resources.ResourcesService;
import org.springframework.dwarf.worker.WorkerService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@StrategyPattern
public class CardStrategy {
	
	@StrategyPattern.Strategy
	interface InnerCardStrategy {

		void actions(Player player);
	
		StrategyName getName();
		
	}

	public enum StrategyName {
		// Mountain cards
		SEAM,FORGES_ALLOY,GET_HELP,ORC_RAIDERS,DRAGONS_KNOCKERS,SHIDE,
		// Special cards
		MUSERT_ARMY,HOLD_COUNCIL,SELL_ITEM,PAST_GLORIES,SPECIAL_ORDER,TURN_BACK,APPRENTICE,COLLAPSE_SHAFTS,RUN_AMOK
	}
	
	/*
	 * ---- mountain cards actions ----
	 */
	
	@StrategyPattern.ConcreteStrategy
	static class StrategySeam implements InnerCardStrategy{
		ResourcesService resourcesService;
		GameService gameService;

		private Integer amountToAdd;
		private ResourceType resource;
		
		@Autowired
		public StrategySeam(ResourcesService resourcesService, GameService gameService) {
			this.resourcesService= resourcesService;
			this.gameService = gameService;
		}
	
		public StrategySeam(String cardName) {
			if(cardName.equals("Iron Seam")){
				this.amountToAdd = 3;
				this.resource = ResourceType.IRON;
			}else if(cardName.equals("Gold Seam")){
				this.amountToAdd = 1;
				this.resource = ResourceType.GOLD;
			}
		}
	
		@Override
		public void actions(Player player) {
			log.debug(player.getUsername() + ", con id" + player.getId() + ", ha realizado la accion " + this.getName().toString());
			/*
			Game game = gameService.findPlayerUnfinishedGames(player).get();
			Resources playerResources = resourcesService.findByPlayerIdAndGameId(player.getId(),game.getId()).get();			
			try {
				playerResources.setResource(resource, amountToAdd);
			}catch(Exception e) {
				
			}
			resourcesService.saveResources(playerResources);
			*/
		}
	
		@Override
		public StrategyName getName() {
			return StrategyName.SEAM;
		}
		
	}

	@StrategyPattern.ConcreteStrategy
	static class GetHelp implements InnerCardStrategy {

		WorkerService workerService;
		GameService gameService;
		
		@Autowired
		public GetHelp (WorkerService workerService, GameService gameService) {
			this.workerService= workerService;
			this.gameService = gameService;
		}

		@Override
		public void actions(Player player) {
			log.debug(player.getUsername() + ", con id" + player.getId() + ", ha realizado la accion " + this.getName().toString());
			/*
			Game game = gameService.findPlayerUnfinishedGames(player).get();
			Worker extraWorker1 = new Worker(player, game);
			Worker extraWorker2 = new Worker(player, game);
			
			workerService.saveWorker(extraWorker1);
			workerService.saveWorker(extraWorker2);
			*/
		}

		@Override
		public StrategyName getName() {
			return StrategyName.GET_HELP;
		}
	
		
	}
	
	@StrategyPattern.ConcreteStrategy
	static class ForgesAlloy implements InnerCardStrategy{

		@Override
		public void actions(Player player) {
			log.debug(player.getUsername() + ", con id" + player.getId() + ", ha realizado la accion " + this.getName().toString());
		}

		@Override
		public StrategyName getName() {
			return StrategyName.FORGES_ALLOY;
		}
		
	}
	
	@StrategyPattern.ConcreteStrategy
	static class OrcRaiders implements InnerCardStrategy{

		@Override
		public void actions(Player player) {
			log.debug(player.getUsername() + ", con id" + player.getId() + ", ha realizado la accion " + this.getName().toString());
		}

		@Override
		public StrategyName getName() {
			return StrategyName.ORC_RAIDERS;
		}
		
	}
	
	@StrategyPattern.ConcreteStrategy
	static class DragonsKnockers implements InnerCardStrategy{

		@Override
		public void actions(Player player) {
			log.debug(player.getUsername() + ", con id" + player.getId() + ", ha realizado la accion " + this.getName().toString());
		}

		@Override
		public StrategyName getName() {
			return StrategyName.DRAGONS_KNOCKERS;
		}
		
	}
	
	@StrategyPattern.ConcreteStrategy
	static class Shide implements InnerCardStrategy{
		
		GameService gameService;
		
		@Autowired
		public Shide(GameService gameService) {
			this.gameService = gameService;
		}
		
		@Override
		public void actions(Player player) {
			log.debug(player.getUsername() + ", con id" + player.getId() + ", ha realizado la accion " + this.getName().toString());
		}

		@Override
		public StrategyName getName() {
			return StrategyName.SHIDE;
		}
		
	}

	/*
	 * ---- special cards actions ----
	 */
	
	@StrategyPattern.ConcreteStrategy
	static class musterAnArmy implements InnerCardStrategy{
		
		GameService gameService;
		
		@Autowired
		public musterAnArmy(GameService gameService) {
			this.gameService = gameService;
		}

		@Override
		public void actions(Player player) {
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
	
	@StrategyPattern.ConcreteStrategy
	static class holdACouncil implements InnerCardStrategy{

		GameService gameService;
		BoardService boardService;
		BoardCellService boardCellService;
		MountainDeckService mountainDeckService;
		
		@Override
		public void actions(Player player) {
			log.debug(player.getUsername() + ", con id" + player.getId() + ", ha realizado la accion " + this.getName().toString());
			/*
			Game currentGame = gameService.findPlayerUnfinishedGames(player).get();
			Board board = gameService.findBoardByGameId(currentGame.getId()).get();
			// se podria hacer una query que te diese el mazo de montaña con el game id
			MountainDeck mountainDeck = board.getMountainDeck();
			List<MountainCard> removedCards = this.removeTopCards(currentGame);
			
			mountainDeck.getMountainCards().addAll(removedCards);
			mountainDeckService.saveMountainDeck(mountainDeck);
			*/
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
	
	@StrategyPattern.ConcreteStrategy
	static class sellAnItem implements InnerCardStrategy{

		@Override
		public void actions(Player player) {
			log.debug(player.getUsername() + ", con id" + player.getId() + ", ha realizado la accion " + this.getName().toString());
			// habria que hacer un form para que el player seleccione que recursos quiere a cambio del objeto
		}

		@Override
		public StrategyName getName() {
			return StrategyName.SELL_ITEM;
		}
		
	}
	
	@StrategyPattern.ConcreteStrategy
	static class pastGlories implements InnerCardStrategy{

		@Override
		public void actions(Player player) {
			log.debug(player.getUsername() + ", con id" + player.getId() + ", ha realizado la accion " + this.getName().toString());
			
		}

		@Override
		public StrategyName getName() {
			return StrategyName.PAST_GLORIES;
		}
		
	}
	
	@StrategyPattern.ConcreteStrategy
	static class specialOrder implements InnerCardStrategy{

		@Override
		public void actions(Player player) {
			log.debug(player.getUsername() + ", con id" + player.getId() + ", ha realizado la accion " + this.getName().toString());
			
		}

		@Override
		public StrategyName getName() {
			return StrategyName.SPECIAL_ORDER;
		}
		
	}
	
	@StrategyPattern.ConcreteStrategy
	static class turnBack implements InnerCardStrategy{

		@Override
		public void actions(Player player) {
			log.debug(player.getUsername() + ", con id" + player.getId() + ", ha realizado la accion " + this.getName().toString());
			
		}

		@Override
		public StrategyName getName() {
			return StrategyName.TURN_BACK;
		}
		
	}
	
	@StrategyPattern.ConcreteStrategy
	static class apprentice implements InnerCardStrategy{

		@Override
		public void actions(Player player) {
			log.debug(player.getUsername() + ", con id" + player.getId() + ", ha realizado la accion " + this.getName().toString());
			
		}

		@Override
		public StrategyName getName() {
			return StrategyName.APPRENTICE;
		}
		
	}
	
	@StrategyPattern.ConcreteStrategy
	static class collapseTheShafts implements InnerCardStrategy{

		@Override
		public void actions(Player player) {
			log.debug(player.getUsername() + ", con id" + player.getId() + ", ha realizado la accion " + this.getName().toString());
			
		}

		@Override
		public StrategyName getName() {
			return StrategyName.COLLAPSE_SHAFTS;
		}
		
	}
	
	@StrategyPattern.ConcreteStrategy
	static class runAmok implements InnerCardStrategy{

		@Override
		public void actions(Player player) {
			log.debug(player.getUsername() + ", con id" + player.getId() + ", ha realizado la accion " + this.getName().toString());
			
		}

		@Override
		public StrategyName getName() {
			return StrategyName.RUN_AMOK;
		}
		
	}
}

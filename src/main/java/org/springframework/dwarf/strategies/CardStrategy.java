package org.springframework.dwarf.strategies;

import java.util.Collection;

import org.jpatterns.gof.StrategyPattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dwarf.game.Game;
import org.springframework.dwarf.game.GameService;
import org.springframework.dwarf.player.Player;
import org.springframework.dwarf.resources.ResourceType;
import org.springframework.dwarf.resources.Resources;
import org.springframework.dwarf.resources.ResourcesService;
import org.springframework.dwarf.worker.Worker;
import org.springframework.dwarf.worker.WorkerService;

@StrategyPattern
public class CardStrategy {
	@StrategyPattern.Strategy
	interface InnerCardStrategy {

		void actions(Player player);
	
		StrategyName getName();
		
	}

	public enum StrategyName {
		SEAM,FORGES_ALLOY,GET_HELP,ORC_RAIDERS,DRAGONS_KNOCKERS,SHIDE
	}
	
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
			if(cardName == "Iron Seam"){
				this.amountToAdd = 3;
				this.resource = ResourceType.IRON;
			}else if(cardName == "Gold Seam"){
				this.amountToAdd = 1;
				this.resource = ResourceType.GOLD;
			}
		}
	
		@Override
		public void actions(Player player) {
			Game game = gameService.findPlayerUnfinishedGames(player).get();
			Resources playerResources = resourcesService.findByPlayerIdAndGameId(player.getId(),game.getId()).get();			
			playerResources.setResource(resource, amountToAdd);
			resourcesService.saveResources(playerResources);
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
			Game game = gameService.findPlayerUnfinishedGames(player).get();
			Collection<Worker> workers = workerService.findByPlayerIdAndGameId(player.getId(), game.getId());
			Worker extraWorker1 = new Worker(player, game);
			Worker extraWorker2 = new Worker(player, game);
			
			workerService.saveWorker(extraWorker1);
			workerService.saveWorker(extraWorker2);
		}

		@Override
		public StrategyName getName() {
			// TODO Auto-generated method stub
			return StrategyName.GET_HELP;
		}
	
		
	}
	

}

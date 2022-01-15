package org.springframework.dwarf.mountainCardStrategies;

import org.jpatterns.gof.StrategyPattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dwarf.card.CardStrategy;
import org.springframework.dwarf.player.Player;
import org.springframework.dwarf.resources.Resources;
import org.springframework.dwarf.resources.ResourcesService;
import org.springframework.stereotype.Component;
import org.springframework.dwarf.card.StrategyName;
import org.springframework.dwarf.forgesAlloy.ForgesAlloyResources;
import org.springframework.dwarf.forgesAlloy.ForgesAlloyResourcesService;
import org.springframework.dwarf.forgesAlloy.ResourceAmount;
import org.springframework.dwarf.game.Game;
import org.springframework.dwarf.game.GameService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@StrategyPattern.ConcreteStrategy
@Component
public class ForgesAlloy implements CardStrategy{
	
	@Autowired
	private ForgesAlloyResourcesService farService;
	@Autowired
	private GameService gameService;
	@Autowired
	private ResourcesService resourcesService;
	
	ForgesAlloyResources far;
	

	@Override
	public void actions(Player player, String cardName) {
		//log.debug(player.getUsername() + ", con id" + player.getId() + ", ha realizado la accion " + this.getName().toString());
		
		this.setResourcesGivenRecived(cardName);
		
		Game game = gameService.findPlayerUnfinishedGames(player).get();
		Resources playerResources = resourcesService.findByPlayerIdAndGameId(player.getId(),game.getId()).get();
		
		playerResources = this.giveResources(playerResources);
		playerResources = this.receiveResources(playerResources);
		
		resourcesService.saveResources(playerResources);
		
		// change players turn if you are the first (not if the card is alloy steel)
	}
	
	private void setResourcesGivenRecived(String cardName) {
		this.far = this.farService.findByCardName(cardName);
	}
	
	private Resources giveResources(Resources playerResources) {
		for(ResourceAmount rAmount: far.getResourcesGiven()) {
			try {
				playerResources.setResource(rAmount.getResource(), rAmount.getAmount()*-1);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return playerResources;
	}
	
	private Resources receiveResources(Resources playerResources) {
		ResourceAmount receive = far.getResourcesReceived();
		
		try {
			playerResources.setResource(receive.getResource(), receive.getAmount());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return playerResources;
	}

	@Override
	public StrategyName getName() {
		return StrategyName.FORGES_ALLOY;
	}
}

package org.springframework.dwarf.mountainCardStrategies;

import java.util.List;

import org.jpatterns.gof.StrategyPattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dwarf.card.CardStrategy;
import org.springframework.dwarf.player.Player;
import org.springframework.dwarf.player.PlayerService;
import org.springframework.dwarf.resources.Resources;
import org.springframework.dwarf.resources.ResourcesService;
import org.springframework.dwarf.user.DuplicatedEmailException;
import org.springframework.dwarf.user.DuplicatedUsernameException;
import org.springframework.dwarf.user.InvalidEmailException;
import org.springframework.stereotype.Component;
import org.springframework.dwarf.card.StrategyName;
import org.springframework.dwarf.forgesAlloy.ForgesAlloyResources;
import org.springframework.dwarf.forgesAlloy.ForgesAlloyResourcesService;
import org.springframework.dwarf.forgesAlloy.ResourceAmount;
import org.springframework.dwarf.game.Game;
import org.springframework.dwarf.game.GameService;

@StrategyPattern.ConcreteStrategy
@Component
public class ForgesAlloy implements CardStrategy{
	
	@Autowired
	private ForgesAlloyResourcesService farService;
	@Autowired
	private GameService gameService;
	@Autowired
	private ResourcesService resourcesService;
	@Autowired
	private PlayerService playerService;
	
	ForgesAlloyResources far;
	

	@Override
	public void actions(Player player, String cardName) {
		
		this.setResourcesGivenRecived(cardName);
		
		Game game = gameService.findPlayerUnfinishedGames(player).get();
		Resources playerResources = resourcesService.findByPlayerIdAndGameId(player.getId(),game.getId()).get();
		
		try {
			if(this.canResolveAction(playerResources)) {
				playerResources = this.giveResources(playerResources);
				playerResources = this.receiveResources(playerResources);
				resourcesService.saveResources(playerResources);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(!cardName.equals("Alloy Steel")) {
			if(player.getTurn().equals(1))
				changePlayerNext(game);
		}
	}
	
	protected void changePlayerNext(Game game) {
		List<Player> turn = game.getTurnList();
		for(Player p:turn) {
			p.setTurn((p.getTurn()%3)+1);
			try {
				playerService.savePlayer(p);
			} catch (DataAccessException | DuplicatedUsernameException | DuplicatedEmailException
					| InvalidEmailException e) {
				e.printStackTrace();
			}
		}
	}
	
	protected void setResourcesGivenRecived(String cardName) {
		this.far = this.farService.findByCardName(cardName);
	}
	
	protected boolean canResolveAction(Resources playerResources) throws Exception {
		for(ResourceAmount ra: far.getResourcesGiven()) {
			if(playerResources.getResourceAmount(ra.getResource()) < ra.getAmount())
				return false;
		}
		return true;
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

package org.springframework.dwarf.strategies;

import java.util.Collection;

import org.jpatterns.gof.StrategyPattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dwarf.player.Player;
import org.springframework.dwarf.resources.Resources;
import org.springframework.dwarf.resources.ResourcesService;

@StrategyPattern.ConcreteStrategy
public class StrategySeam  implements CardStrategy{

    
    ResourcesService resourcesService;

    private Integer amount;
    private ResourceType resource;
    
    @Autowired
    public StrategySeam (ResourcesService resourcesService) {
        this.resourcesService= resourcesService;
    }

    public StrategySeam(String cardName) {
        if(cardName == "Iron Seam"){
            this.amount = 3;
            this.resource = ResourceType.IRON;
        }else if(cardName == "Gold Seam"){
            this.amount = 1;
            this.resource = ResourceType.GOLD;
        }
    }

    @Override
    public void actions(Player player) {
        Collection<Resources> playerResource = resourcesService.findByPlayerIdAndGameId(player.getId());
        
    }

    @Override
    public StrategyName getName() {
        return StrategyName.SEAM;
    }
    
}

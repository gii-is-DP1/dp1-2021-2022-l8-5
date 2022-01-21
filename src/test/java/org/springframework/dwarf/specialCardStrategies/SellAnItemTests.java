package org.springframework.dwarf.specialCardStrategies;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dwarf.card.StrategyName;
import org.springframework.dwarf.game.Game;
import org.springframework.dwarf.game.GameService;
import org.springframework.dwarf.player.Player;
import org.springframework.dwarf.player.PlayerService;
import org.springframework.dwarf.resources.ResourceType;
import org.springframework.dwarf.resources.Resources;
import org.springframework.dwarf.resources.ResourcesService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(value = { Service.class, Component.class }))
public class SellAnItemTests {

    @Autowired
    protected SellAnItem sai;

    @Autowired
    ResourcesService resourcesService;

    @Autowired
    private PlayerService playerService;

    @Autowired
    private GameService gameService;

    private Player p1;
    private Game game;
    private Resources playerResources;

    @BeforeEach
    void setup() throws Exception {

        game = gameService.findByGameId(2).get();

        p1 = playerService.findPlayerById(2);

        playerResources = new Resources(game, p1);
        playerResources.addResource(ResourceType.ITEMS, 1);
        resourcesService.saveResources(playerResources);

    }

    @Test
    void testGetName() {
        StrategyName name = sai.getName();
        assertThat(name).isEqualTo(StrategyName.SELL_AN_ITEM);

    }

    @Test
    void testGiveRandomResources() throws Exception {

        Map<ResourceType, Integer> testMap = sai.giveRandomResources();
        Integer totalSum = 0;
        for (ResourceType type : testMap.keySet()) {
            assertTrue(testMap.get(type) >= 0);
            assertTrue(testMap.get(type) <= 5);
            totalSum += testMap.get(type);

        }
        assertThat(totalSum).isEqualTo(5);
    }

    @Test
    void testSetResourcesToPlayer() throws Exception {
        Integer oldGold = playerResources.getGold();
        Integer oldIron = playerResources.getIron();
        Integer oldSteel = playerResources.getSteel();

        sai.setResourcesToPlayer(playerResources);

        Integer newGold = playerResources.getGold();
        Integer newIron = playerResources.getIron();
        Integer newSteel = playerResources.getSteel();

        assertTrue(oldGold != newGold || oldIron != newIron || oldSteel != newSteel);
    }

    @Test
    @WithMockUser(username = "test")
    void testActions() throws Exception {

        Integer oldGold = playerResources.getGold();
        Integer oldIron = playerResources.getIron();
        Integer oldSteel = playerResources.getSteel();
        Integer oldItems = playerResources.getItems();

        sai.actions(p1, "");

        Integer newGold = playerResources.getGold();
        Integer newIron = playerResources.getIron();
        Integer newSteel = playerResources.getSteel();
        Integer newItems = playerResources.getItems();

        assertTrue((oldGold != newGold || oldIron != newIron || oldSteel != newSteel) && (oldItems == newItems + 1));
    }

}

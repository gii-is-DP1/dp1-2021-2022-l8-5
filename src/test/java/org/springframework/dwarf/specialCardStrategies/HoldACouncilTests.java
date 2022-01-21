package org.springframework.dwarf.specialCardStrategies;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dwarf.board.Board;
import org.springframework.dwarf.board.BoardCell;
import org.springframework.dwarf.board.BoardService;
import org.springframework.dwarf.card.StrategyName;
import org.springframework.dwarf.game.Game;
import org.springframework.dwarf.game.GameService;
import org.springframework.dwarf.mountainCard.MountainCard;
import org.springframework.dwarf.mountainCard.MountainCardService;
import org.springframework.dwarf.player.Player;
import org.springframework.dwarf.player.PlayerService;
import org.springframework.dwarf.resources.ResourcesService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(value = { Service.class, Component.class }))
public class HoldACouncilTests {

    @Autowired
    protected HoldACouncil hac;

    @Autowired
    ResourcesService resourcesService;

    @Autowired
    private PlayerService playerService;

    @Autowired
    private GameService gameService;

    @Autowired
    private BoardService boardService;

    @Autowired
    private MountainCardService mountainCardService;

    private Player p1;
    private Game game;
    private Board board;

    @BeforeEach
    void setup() throws Exception {

        game = gameService.findByGameId(2).get();

        p1 = playerService.findPlayerById(2);

        board = boardService.createBoard(game);
        List<MountainCard> listacartas = new ArrayList<MountainCard>();
        listacartas.add(mountainCardService.findByMountainCardId(1).get());
        listacartas.add(mountainCardService.findByMountainCardId(10).get());
        for (BoardCell c : board.getBoardCells()) {
            c.setMountaincards(listacartas);
        }

    }

    @Test
    void testGetName() {
        StrategyName name = hac.getName();
        assertThat(name).isEqualTo(StrategyName.HOLD_A_COUNCIL);

    }

    @Test
    void testRemoveTopCards() throws Exception {

        MountainCard boardCell1 = board.getBoardCells().get(0).getMountaincards().get(0);

        List<MountainCard> removedCardsList = hac.removeTopCards(game, board);

        assertThat(!(removedCardsList.isEmpty())).isTrue();
        MountainCard boardCell2 = board.getBoardCells().get(0).getMountaincards().get(0);
        assertThat(boardCell1).isNotEqualTo(boardCell2);

        removedCardsList = hac.removeTopCards(game, board);

        assertThat((removedCardsList.isEmpty())).isTrue();
        MountainCard boardCell3 = board.getBoardCells().get(0).getMountaincards().get(0);
        assertThat(boardCell2).isEqualTo(boardCell3);
    }

    @Test
    @WithMockUser(username = "test")
    void testActions() throws Exception {

        Integer mountainDeckSize1 = board.getMountainDeck().getMountainCards().size();
        MountainCard lastCard1 = board.getMountainDeck().getMountainCards().get(mountainDeckSize1 - 1);

        hac.actions(p1, "");

        Integer mountainDeckSize2 = board.getMountainDeck().getMountainCards().size();
        MountainCard lastCard2 = board.getMountainDeck().getMountainCards().get(mountainDeckSize2 - 1);
        assertThat(lastCard1).isNotEqualTo(lastCard2);

        hac.actions(p1, "");

        Integer mountainDeckSize3 = board.getMountainDeck().getMountainCards().size();
        MountainCard lastCard3 = board.getMountainDeck().getMountainCards().get(mountainDeckSize3 - 1);
        assertThat(lastCard2).isEqualTo(lastCard3);

    }

}

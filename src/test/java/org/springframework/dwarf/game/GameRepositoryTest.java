package org.springframework.dwarf.game;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dwarf.mountain_card.MountainDeck;
import org.springframework.dwarf.player.Player;
import org.springframework.stereotype.Repository;


@DataJpaTest(includeFilters = @ComponentScan.Filter(Repository.class))
public class GameRepositoryTest {
    @Autowired
    protected GameRepository gameRepository;

        @Test
        void testSearchDeckByGameId() throws Exception {
            Optional<MountainDeck> mountainDeck = gameRepository.searchDeckByGameId(1);
            assertThat(mountainDeck.isPresent()).isTrue();
        }

        @Test
        void testSearchPlayerOne() throws Exception {
            Player p = gameRepository.searchPlayerOneByGame(1);	//Juego sin terminar
            assertEquals(p.getId(), 6);
        }
        
        @Test
        void testSearchPlayerTwo() throws Exception {
            Player p = gameRepository.searchPlayerTwoByGame(2);
            assertEquals(p.getId(), 5);
        }
        
        @Test
        void testSearchPlayerThree() throws Exception {
            Player p = gameRepository.searchPlayerThreeByGame(3);	//Juego terminado
            assertEquals(p.getId(), 2);
        }
        
        
}
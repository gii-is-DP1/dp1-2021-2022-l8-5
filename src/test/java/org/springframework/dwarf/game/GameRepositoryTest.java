package org.springframework.dwarf.game;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collection;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dwarf.mountain_card.MountainDeck;
import org.springframework.stereotype.Repository;


@DataJpaTest(includeFilters = @ComponentScan.Filter(Repository.class))
public class GameRepositoryTest {
    @Autowired
    protected GameRepository gameRepository;

        @Test
        void searchDeckByGameId() throws Exception {
            Optional<MountainDeck> mountainDeck = gameRepository.searchDeckByGameId(1);
            assertThat(mountainDeck.isPresent()).isTrue();
        }

}
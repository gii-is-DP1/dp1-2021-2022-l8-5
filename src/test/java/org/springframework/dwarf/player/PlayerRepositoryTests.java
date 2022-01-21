package org.springframework.dwarf.player;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Repository;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Repository.class))
class PlayerRepositoryTests {
    @Autowired
    protected PlayerRepository playerRepository;

    @Test
    void findUserById() throws Exception {
        Player player = playerRepository.findById(1).get();
        assertEquals(player.getUsername(), "pabmargom3");
    }

    @Test
    void findUserByEmail() throws Exception {
        Player player = playerRepository.findByEmail("test2@test.com");
        assertEquals(player.getUsername(), "pabmargom3");
    }

}
package org.springframework.dwarf.game;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Repository;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Repository.class))
public class GameRepositoryTests {
	
	  @Autowired
	    protected GameRepository gameRepository;
	  
	  @Test
	    void findGamesToJoin() throws Exception {
	       List<Game> games = gameRepository.searchGamesToJoin();
	       assertThat(games.size()).isEqualTo(1);
	    }
	  
	  
	  


}

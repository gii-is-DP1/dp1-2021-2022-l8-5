package org.springframework.dwarf.board;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.dwarf.configuration.SecurityConfiguration;
import org.springframework.dwarf.game.GameService;
import org.springframework.dwarf.player.PlayerService;
import org.springframework.dwarf.resources.ResourcesService;
import org.springframework.dwarf.special_card.SpecialDeckService;
import org.springframework.dwarf.worker.WorkerService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = BoardController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
class BoardControllerTests {
	
	@MockBean
	private BoardService boardService;
	@MockBean
	private GameService gameService;
	@MockBean
    private ResourcesService resourcesService;
	@MockBean
    private WorkerService workerService;
	@MockBean
    private PlayerService playerService;
	@MockBean
    private BoardCellService boardCellService;
	@MockBean
    private SpecialDeckService specialDeckService;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	void testGetBoard() throws Exception {
		mockMvc.perform(get("/boards")).andExpect(status().is4xxClientError());
	}
}

package org.springframework.dwarf.board;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.dwarf.configuration.SecurityConfiguration;
import org.springframework.dwarf.game.GameService;
import org.springframework.dwarf.player.Player;
import org.springframework.dwarf.player.PlayerService;
import org.springframework.dwarf.resources.ResourcesService;
import org.springframework.dwarf.specialCard.SpecialDeckService;
import org.springframework.dwarf.user.User;
import org.springframework.dwarf.web.LoggedUserController;
import org.springframework.dwarf.worker.WorkerService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = { BoardController.class,
		LoggedUserController.class }, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)

class BoardControllerTests {

	@Autowired
	private BoardController boardController;

	@Autowired
	private ApplicationContext ac;

	@MockBean
	private GameService gameService;

	@MockBean
	private BoardService boardService;
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

	private Player george;

	@BeforeEach
	void setup() {

		george = new Player();
		george.setId(1);
		george.setFirstName("Paco");
		george.setLastName("Fiestas");
		User user = new User();
		user.setUsername("paco");
		user.setEmail("hacker@hack.com");
		user.setEnabled(true);
		user.setPassword("1");
		george.setAvatarUrl("https://www.w3schools.com/w3images/avatar1.png");

		george.setUser(user);

		List<Player> lista = new ArrayList<Player>(List.of(george));

		given(this.playerService.findPlayerById(1)).willReturn(george);
		given(this.playerService.findPlayerByUserName("paco")).willReturn(george);

		given(this.playerService.findPlayerByLastName("T")).willReturn(lista);

	}

	@Test
	void testGetBoard() throws Exception {
		mockMvc.perform(get("/boards")).andExpect(status().is4xxClientError());
	}

	@Test
	void testSetTurns() {
		List<Player> players = playerService.findPlayerByLastName("T").stream().collect(Collectors.toList());
		Player player = players.get(0);
		Integer turnInitial = player.getTurn();

		assertThat(turnInitial).isEqualTo(null);

		boardController.setTurns(players);

		Integer turnFinal = player.getTurn();

		assertThat(turnFinal).isEqualTo(1);

	}

}

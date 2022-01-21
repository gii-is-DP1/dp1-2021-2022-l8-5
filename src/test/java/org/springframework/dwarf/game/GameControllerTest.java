package org.springframework.dwarf.game;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.dwarf.configuration.SecurityConfiguration;
import org.springframework.dwarf.player.Player;
import org.springframework.dwarf.player.PlayerService;
import org.springframework.dwarf.resources.ResourcesService;
import org.springframework.dwarf.user.AuthoritiesService;
import org.springframework.dwarf.user.User;
import org.springframework.dwarf.user.UserService;
import org.springframework.dwarf.web.LoggedUserController;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Test class for {@link GameController}
 *
 * @author Pablo Marín
 * 
 */

@WebMvcTest(controllers = { GameController.class,
		LoggedUserController.class }, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
public class GameControllerTest {

	@MockBean
	private GameService gameService;

	@MockBean
	private ResourcesService resourcesService;

	@MockBean
	private AuthoritiesService authoritiesService;

	@MockBean
	private PlayerService playerService;

	@MockBean
	private UserService userService;
	@Autowired
	private MockMvc mockMvc;

	private Game g0;

	private Player p1;

	@BeforeEach
	void setup() {
		g0 = new Game();

		p1 = new Player();
		User u1 = new User();
		u1.setUsername("player1");
		p1.setUser(u1);
		p1.setId(1);

		Player p2 = new Player();
		User u2 = new User();
		u2.setUsername("player2");
		p2.setUser(u2);

		Player p3 = new Player();
		User u3 = new User();
		u3.setUsername("player3");
		p3.setUser(u3);
		p3.setUser(u3);

		g0.setFirstPlayer(p1);
		g0.setSecondPlayer(p2);
		g0.setThirdPlayer(p3);

		given(this.playerService.findPlayerById(1)).willReturn(p1);
		given(this.playerService.findPlayerByUserName("player1")).willReturn(p1);
	}

	@Test
	@WithMockUser(username = "pabmargom3")
	void testSearchGames_searchOrCreate() throws Exception {
		mockMvc.perform(get("/games/searchGames")).andExpect(status().isOk())
				.andExpect(view().name("games/searchOrCreateGames"))
				.andExpect(model().attributeExists("gamesToJoin"));
	}

	@Test
	@WithMockUser(username = "pabmargom3")
	void listFinsihedGames() throws Exception {
		mockMvc.perform(get("/games/listGames/finished")).andExpect(status().isOk())
				.andExpect(view().name("games/listGames"))
				.andExpect(model().attributeExists("games"));
	}

	@Test
	@WithMockUser(username = "pabmargom3")
	void listCurrentGames() throws Exception {
		mockMvc.perform(get("/games/listGames/current")).andExpect(status().isOk())
				.andExpect(view().name("games/listGames"))
				.andExpect(model().attributeExists("games"));
	}
}

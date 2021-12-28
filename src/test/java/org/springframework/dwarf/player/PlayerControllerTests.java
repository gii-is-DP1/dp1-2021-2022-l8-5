package org.springframework.dwarf.player;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.dwarf.configuration.SecurityConfiguration;
import org.springframework.dwarf.game.GameService;
import org.springframework.dwarf.user.AuthoritiesService;
import org.springframework.dwarf.user.User;
import org.springframework.dwarf.user.UserService;
import org.springframework.dwarf.web.LoggedUserController;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Test class for {@link PlayerController}
 *
 * @author Colin But
 * @author Pablo Marín
 */

@WebMvcTest(controllers = PlayerController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
class PlayerControllerTests {

	private static final int TEST_PLAYER_ID = 1;

	@MockBean
	private PlayerService playerService;

	@MockBean
	private UserService userService;

	@MockBean
	private AuthoritiesService authoritiesService;
	

	@MockBean
	private GameService gameService;

	@Autowired
	private MockMvc mockMvc;

	private Player george;

	@BeforeEach
	void setup() {

		george = new Player();
		george.setId(TEST_PLAYER_ID);
		george.setFirstName("Paco");
		george.setLastName("Fiestas");
		User user = new User();
		user.setUsername("paco");
		user.setEmail("hacker@hack.com");
		user.setEnabled(true);
		user.setPassword("1");
		george.setAvatarUrl("https://www.w3schools.com/w3images/avatar1.png");
		
		george.setUser(user);
		given(this.playerService.findPlayerById(TEST_PLAYER_ID)).willReturn(george);
	}
	@WithMockUser(username = "pabmargom3")
    @Test
    void loginSuccesful() throws Exception {
        String userLogged = LoggedUserController.returnLoggedUserName();
        assertEquals(userLogged, "pabmargom3");       
    }
	@Test
	@WithMockUser(username = "pabmargom3") 
    void loginUnSuccesful() throws Exception {
        String userLogged = LoggedUserController.returnLoggedUserName();
        assertNotEquals(userLogged, "nopabmargom3");       
    }
	
    @WithMockUser(username = "admin1")
	@Test
	void testInitFindForm() throws Exception {
		mockMvc.perform(get("/players/find")).andExpect(status().isOk()).andExpect(model().attributeExists("players"))
				.andExpect(view().name("players/findPlayers"));
	}
	
    
	@WithMockUser(username = "admin1")
	@Test
	void testInitUpdatePlayerForm() throws Exception {
		mockMvc.perform(get("/players/1/edit")).andExpect(status().isOk())
		.andExpect(view().name("players/createOrUpdatePlayerForm"))
		.andExpect(model().attributeExists("player"))
		.andExpect(model().attribute("player", hasProperty("firstName", is("Paco"))))
		.andExpect(model().attribute("player", hasProperty("lastName", is("Fiestas"))))
		.andExpect(model().attribute("player", hasProperty("password", is("1"))))
		.andExpect(model().attribute("player", hasProperty("email", is("hacker@hack.com"))))
		.andExpect(model().attribute("player", hasProperty("avatarUrl", is("https://www.w3schools.com/w3images/avatar1.png"))))
		.andExpect(view().name("players/createOrUpdatePlayerForm"));
	}
	/*
	@WithMockUser(value = "spring")
	@Test
	void testProcessUpdatePlayerFormSuccess() throws Exception {
		mockMvc.perform(post("/players/{playerId}/edit", TEST_PLAYER_ID).with(csrf()).param("firstName", "Paco")
				.param("lastName", "Fiestas").param("password", "1").param("email", "hacker@hack.com")
				.param("avatarUrl", "https://www.w3schools.com/w3images/avatar1.png")).andExpect(status().isOk())
				.andExpect(view().name("redirect:/players"));
	}*/
	
	@WithMockUser(value = "spring")
	@Test
	void testInitLogin() throws Exception {
		mockMvc.perform(get("/login")).andExpect(status().isOk());
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testInitCreationForm() throws Exception {
		mockMvc.perform(get("/players/new")).andExpect(status().isOk()).andExpect(model().attributeExists("player"))
				.andExpect(view().name("players/createOrUpdatePlayerForm"));
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testProcessCreationFormSuccess() throws Exception {
		mockMvc.perform(post("/players/new").param("firstName", "Joe").param("username", "Joe").param("lastName", "Bloggs").param("password", "1").param("email", "xd@xd.com").param("avatarUrl", "https://www.w3schools.com/w3images/avatar1.png").with(csrf())
				)
				.andExpect(status().isOk());
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testProcessCreationFormHasErrors() throws Exception {
		mockMvc.perform(post("/players/new").with(csrf()).param("firstName", "Joe").param("lastName", "Bloggs").param("username", "paco").param("password", "1").param("email", "xd@xd.com").param("avatarUrl", "https://www.w3schools.com/w3images/avatar1.png")).andExpect(status().isOk()).andExpect(model().attributeHasErrors("player"))
				.andExpect(model().attributeHasFieldErrors("player", "username"))
				.andExpect(view().name("players/createOrUpdatePlayerForm"));
	}

	
	@WithMockUser(value = "spring")
	@Test
	void testProcessFindFormSuccess() throws Exception {
		given(this.playerService.findPlayerByLastName("")).willReturn(Lists.newArrayList(george, new Player()));

		mockMvc.perform(get("/players")).andExpect(status().isOk()).andExpect(view().name("players/playersList"));
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testShowPlayer() throws Exception {
		mockMvc.perform(get("/players/{playerId}", TEST_PLAYER_ID)).andExpect(status().isOk())
				.andExpect(model().attribute("player", hasProperty("lastName", is("Fiestas"))))
				.andExpect(model().attribute("player", hasProperty("firstName", is("Paco"))))
				.andExpect(model().attribute("player", hasProperty("password", is("1"))))
				.andExpect(model().attribute("player", hasProperty("email", is("hacker@hack.com"))))
				.andExpect(model().attribute("player", hasProperty("avatarUrl", is("https://www.w3schools.com/w3images/avatar1.png"))))
				.andExpect(view().name("players/playerDetails"));
	}
	

	
	
	/*
	@WithMockUser(username = "paco")
	@Test
	void testInitUpdateMySelfForm() throws Exception {
		mockMvc.perform(get("/editProfile")).andExpect(status().isOk())
		.andExpect(view().name("players/createOrUpdatePlayerForm"))
		.andExpect(model().attributeExists("player"))
		.andExpect(model().attribute("player", hasProperty("firstName", is("Paco"))))
		.andExpect(model().attribute("player", hasProperty("lastName", is("Fiestas"))))
		.andExpect(model().attribute("player", hasProperty("password", is("1"))))
		.andExpect(model().attribute("player", hasProperty("email", is("hacker@hack.com"))))
		.andExpect(model().attribute("player", hasProperty("avatarUrl", is("https://www.w3schools.com/w3images/avatar1.png"))))
		.andExpect(view().name("players/createOrUpdatePlayerForm"));
	}
	
	*/
	
	
	/*
	
	@WithMockUser(value = "spring")
	@Test
	void testProcessLoginFormHasErrors() throws Exception {
		mockMvc.perform(post("/login").with(csrf()).param("username", "noexist").param("password", "nope")
				).andExpect(status().isOk())
				.andExpect(view().name("welcome"));
	}
*/
	/*
	
	@WithMockUser(value = "spring")
	@Test
	void testProcessCreationFormSuccess() throws Exception {
		mockMvc.perform(post("/users/new").param("firstName", "John").param("lastName", "Bloggs").param("user.username", "joemama").param("user.email", "joemama@test.com").param("user.password", "1").with(csrf())
				)
		.andExpect(view().name("/welcome"));
	}
	*/
	
	
	/*

	@WithMockUser(value = "spring")
	@Test
	void testProcessCreationFormSuccess() throws Exception {
		mockMvc.perform(post("/players/new").param("firstName", "Joe").param("lastName", "Bloggs").with(csrf())
				)
				.andExpect(status().is3xxRedirection());
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessCreationFormHasErrors() throws Exception {
		mockMvc.perform(post("/players/new").with(csrf()).param("firstName", "Joe").param("lastName", "Bloggs")
				).andExpect(status().isOk()).andExpect(model().attributeHasErrors("player"))
				.andExpect(view().name("players/createOrUpdatePlayerForm"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testInitFindForm() throws Exception {
		mockMvc.perform(get("/players/find")).andExpect(status().isOk()).andExpect(model().attributeExists("player"))
				.andExpect(view().name("players/findplayers"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessFindFormSuccess() throws Exception {
		given(this.playerService.findOwnerByLastName("")).willReturn(Lists.newArrayList(george, new Player()));

		mockMvc.perform(get("/players")).andExpect(status().isOk()).andExpect(view().name("players/playersList"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessFindFormByLastName() throws Exception {
		given(this.playerService.findOwnerByLastName(george.getLastName())).willReturn(Lists.newArrayList(george));

		mockMvc.perform(get("/players").param("lastName", "Franklin")).andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/players/" + TEST_OWNER_ID));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessFindFormNoplayersFound() throws Exception {
		mockMvc.perform(get("/players").param("lastName", "Unknown Surname")).andExpect(status().isOk())
				.andExpect(model().attributeHasFieldErrors("owner", "lastName"))
				.andExpect(model().attributeHasFieldErrorCode("owner", "lastName", "notFound"))
				.andExpect(view().name("players/findplayers"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testInitUpdateOwnerForm() throws Exception {
		mockMvc.perform(get("/players/{ownerId}/edit", TEST_OWNER_ID)).andExpect(status().isOk())
				.andExpect(model().attributeExists("owner"))
				.andExpect(model().attribute("owner", hasProperty("lastName", is("Franklin"))))
				.andExpect(model().attribute("owner", hasProperty("firstName", is("George"))))
				.andExpect(model().attribute("owner", hasProperty("address", is("110 W. Liberty St."))))
				.andExpect(model().attribute("owner", hasProperty("city", is("Madison"))))
				.andExpect(model().attribute("owner", hasProperty("telephone", is("608555103"))))
				.andExpect(view().name("players/createOrUpdateOwnerForm"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessUpdateOwnerFormSuccess() throws Exception {
		mockMvc.perform(post("/players/{ownerId}/edit", TEST_OWNER_ID).with(csrf()).param("firstName", "Joe")
				.param("lastName", "Bloggs").param("address", "13 Caramel Street").param("city", "London")
				.param("telephone", "0161691589")).andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/players/{ownerId}"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessUpdateOwnerFormHasErrors() throws Exception {
		mockMvc.perform(post("/players/{ownerId}/edit", TEST_OWNER_ID).with(csrf()).param("firstName", "Joe")
				.param("lastName", "Bloggs").param("city", "London")).andExpect(status().isOk())
				.andExpect(model().attributeHasErrors("owner"))
				.andExpect(model().attributeHasFieldErrors("owner", "address"))
				.andExpect(model().attributeHasFieldErrors("owner", "telephone"))
				.andExpect(view().name("players/createOrUpdateOwnerForm"));
	}


	*/
}

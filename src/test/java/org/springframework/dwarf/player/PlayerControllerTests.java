package org.springframework.dwarf.player;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
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
import org.springframework.dwarf.game.GameService;
import org.springframework.dwarf.user.AuthoritiesService;
import org.springframework.dwarf.user.UserService;
import org.springframework.dwarf.util.CorrentUserController;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Test class for {@link PlayerController}
 *
 * @author Colin But
 */

@WebMvcTest(controllers = PlayerController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
class PlayerControllerTests {

	private static final int TEST_PLAYER_ID = 1;

	@Autowired
	private PlayerController playerController;

	@MockBean
	private PlayerService playerService;

	@MockBean
	private UserService userService;
	

	@MockBean
	private GameService gameService;

	@MockBean
	private AuthoritiesService authoritiesService;

	@Autowired
	private MockMvc mockMvc;

	private Player george;

	@BeforeEach
	void setup() {

		george = new Player();
		george.setId(TEST_PLAYER_ID);
		george.setFirstName("George");
		george.setLastName("Franklin");
		given(this.playerService.findPlayerById(TEST_PLAYER_ID)).willReturn(george);

	}
	@WithMockUser(username = "pabmargom3")
    @Test
    void loginSuccesful() throws Exception {
        String userLogged = CorrentUserController.returnCurrentUserName();
        assertEquals(userLogged, "pabmargom3");       
    }
	@Test
	@WithMockUser(username = "pabmargom3") 
    void loginUnSuccesful() throws Exception {
        String userLogged = CorrentUserController.returnCurrentUserName();
        assertNotEquals(userLogged, "nopabmargom3");       
    }
	
    @WithMockUser(username = "admin1")
	@Test
	void testInitFindForm() throws Exception {
		mockMvc.perform(get("/players/find")).andExpect(status().isOk()).andExpect(model().attributeExists("players"))
				.andExpect(view().name("players/findPlayers"));
	}
	
	
	/*
	@WithMockUser(value = "spring")
	@Test
	void testInitCreationForm() throws Exception {
		mockMvc.perform(get("/players/new")).andExpect(status().isOk()).andExpect(model().attributeExists("player"))
				.andExpect(view().name("players/createOrUpdatePlayerForm"));
	}
	@WithMockUser(value = "spring")
	@Test
	void testInitLogin() throws Exception {
		mockMvc.perform(get("/login")).andExpect(status().isOk());
	}
	
	@WithMockUser()
	@Test
	void testProcessLoginFormSuccess() throws Exception {
		mockMvc.perform(post("/login").param("username", "test").param("password", "1").with(csrf())
				)
				.andExpect(status());
	}
	
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

	@WithMockUser(value = "spring")
	@Test
	void testShowOwner() throws Exception {
		mockMvc.perform(get("/players/{ownerId}", TEST_OWNER_ID)).andExpect(status().isOk())
				.andExpect(model().attribute("owner", hasProperty("lastName", is("Franklin"))))
				.andExpect(model().attribute("owner", hasProperty("firstName", is("George"))))
				.andExpect(model().attribute("owner", hasProperty("address", is("110 W. Liberty St."))))
				.andExpect(model().attribute("owner", hasProperty("city", is("Madison"))))
				.andExpect(model().attribute("owner", hasProperty("telephone", is("608555103"))))
				.andExpect(view().name("players/ownerDetails"));
	}
	*/
}

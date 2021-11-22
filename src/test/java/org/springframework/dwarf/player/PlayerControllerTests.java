package org.springframework.dwarf.player;

import static org.hamcrest.Matchers.hasProperty;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.hamcrest.Matchers.is;
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
import org.springframework.dwarf.player.Player;
import org.springframework.dwarf.player.PlayerController;
import org.springframework.dwarf.player.PlayerService;
import org.springframework.dwarf.user.AuthoritiesService;
import org.springframework.dwarf.user.UserService;
import org.springframework.dwarf.web.CorrentUserController;
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
	@WithMockUser(username = "fernandoAlonso", password = "worldChampion")
    @Test
    void loginSuccesful() throws Exception {
        String userLogged = CorrentUserController.returnCurrentUserName();
        assertEquals(userLogged, "fernandoAlonso");       
    }
	@Test
	@WithMockUser(username = "fernandoAlonso", password = "worldChampion") 
    void loginUnSuccesful() throws Exception {
        String userLogged = CorrentUserController.returnCurrentUserName();
        assertNotEquals(userLogged, "michaeljordan");       
    }
	
    @WithMockUser(username = "admin1")
	@Test
	void testInitFindForm() throws Exception {
		mockMvc.perform(get("/players2/find")).andExpect(status().isOk()).andExpect(model().attributeExists("players2"))
				.andExpect(view().name("players2/findPlayers"));
	}
	
	
	/*
	@WithMockUser(value = "spring")
	@Test
	void testInitCreationForm() throws Exception {
		mockMvc.perform(get("/players2/new")).andExpect(status().isOk()).andExpect(model().attributeExists("player"))
				.andExpect(view().name("players2/createOrUpdatePlayerForm"));
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
		mockMvc.perform(post("/players2/new").param("firstName", "Joe").param("lastName", "Bloggs").with(csrf())
				)
				.andExpect(status().is3xxRedirection());
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessCreationFormHasErrors() throws Exception {
		mockMvc.perform(post("/players2/new").with(csrf()).param("firstName", "Joe").param("lastName", "Bloggs")
				).andExpect(status().isOk()).andExpect(model().attributeHasErrors("player2"))
				.andExpect(view().name("players2/createOrUpdatePlayerForm"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testInitFindForm() throws Exception {
		mockMvc.perform(get("/players2/find")).andExpect(status().isOk()).andExpect(model().attributeExists("player2"))
				.andExpect(view().name("players2/findplayers2"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessFindFormSuccess() throws Exception {
		given(this.playerService.findOwnerByLastName("")).willReturn(Lists.newArrayList(george, new Player2()));

		mockMvc.perform(get("/players2")).andExpect(status().isOk()).andExpect(view().name("players2/players2List"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessFindFormByLastName() throws Exception {
		given(this.playerService.findOwnerByLastName(george.getLastName())).willReturn(Lists.newArrayList(george));

		mockMvc.perform(get("/players2").param("lastName", "Franklin")).andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/players2/" + TEST_OWNER_ID));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessFindFormNoplayers2Found() throws Exception {
		mockMvc.perform(get("/players2").param("lastName", "Unknown Surname")).andExpect(status().isOk())
				.andExpect(model().attributeHasFieldErrors("owner", "lastName"))
				.andExpect(model().attributeHasFieldErrorCode("owner", "lastName", "notFound"))
				.andExpect(view().name("players2/findplayers2"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testInitUpdateOwnerForm() throws Exception {
		mockMvc.perform(get("/players2/{ownerId}/edit", TEST_OWNER_ID)).andExpect(status().isOk())
				.andExpect(model().attributeExists("owner"))
				.andExpect(model().attribute("owner", hasProperty("lastName", is("Franklin"))))
				.andExpect(model().attribute("owner", hasProperty("firstName", is("George"))))
				.andExpect(model().attribute("owner", hasProperty("address", is("110 W. Liberty St."))))
				.andExpect(model().attribute("owner", hasProperty("city", is("Madison"))))
				.andExpect(model().attribute("owner", hasProperty("telephone", is("6085551023"))))
				.andExpect(view().name("players2/createOrUpdateOwnerForm"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessUpdateOwnerFormSuccess() throws Exception {
		mockMvc.perform(post("/players2/{ownerId}/edit", TEST_OWNER_ID).with(csrf()).param("firstName", "Joe")
				.param("lastName", "Bloggs").param("address", "123 Caramel Street").param("city", "London")
				.param("telephone", "01616291589")).andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/players2/{ownerId}"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessUpdateOwnerFormHasErrors() throws Exception {
		mockMvc.perform(post("/players2/{ownerId}/edit", TEST_OWNER_ID).with(csrf()).param("firstName", "Joe")
				.param("lastName", "Bloggs").param("city", "London")).andExpect(status().isOk())
				.andExpect(model().attributeHasErrors("owner"))
				.andExpect(model().attributeHasFieldErrors("owner", "address"))
				.andExpect(model().attributeHasFieldErrors("owner", "telephone"))
				.andExpect(view().name("players2/createOrUpdateOwnerForm"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testShowOwner() throws Exception {
		mockMvc.perform(get("/players2/{ownerId}", TEST_OWNER_ID)).andExpect(status().isOk())
				.andExpect(model().attribute("owner", hasProperty("lastName", is("Franklin"))))
				.andExpect(model().attribute("owner", hasProperty("firstName", is("George"))))
				.andExpect(model().attribute("owner", hasProperty("address", is("110 W. Liberty St."))))
				.andExpect(model().attribute("owner", hasProperty("city", is("Madison"))))
				.andExpect(model().attribute("owner", hasProperty("telephone", is("6085551023"))))
				.andExpect(view().name("players2/ownerDetails"));
	}
	*/
}

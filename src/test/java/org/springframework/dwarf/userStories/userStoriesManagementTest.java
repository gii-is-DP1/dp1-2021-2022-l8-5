package org.springframework.dwarf.userStories;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
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
import org.springframework.dwarf.player.Player;
import org.springframework.dwarf.player.PlayerController;
import org.springframework.dwarf.player.PlayerService;
import org.springframework.dwarf.user.AuthoritiesService;
import org.springframework.dwarf.user.User;
import org.springframework.dwarf.user.UserService;
import org.springframework.dwarf.web.LoggedUserController;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;



@WebMvcTest(controllers = PlayerController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
class userStoriesManagementTest {

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
    

    @WithMockUser(username = "alonsoPodio", password = "ElNano0")
    @Test
    void loginSuccesful() throws Exception {
        String userLogged = LoggedUserController.returnLoggedUserName();
        assertEquals(userLogged, "alonsoPodio");
    }

    @WithMockUser(username = "alonsoPodio", password = "ElNano0")
    @Test
    void loginUnsuccesful() throws Exception {
        String userLogged = LoggedUserController.returnLoggedUserName();
        assertNotEquals(userLogged, "betrayal>theneostorm");
    }
    /*
    @WithMockUser(username = "alonsoPodio", password = "ElNano0")
    @Test
    void logOutSuccesful() throws Exception {
        String userLogged = CorrentUserController.returnCurrentUserName();
        
		mockMvc.perform(post("/logout").with(csrf()));
		
	   assertNotEquals(userLogged, "alonsoPodio");
		
    }*/
    
    @WithMockUser(username = "admin1")
	@Test
	void testInitFindForm() throws Exception {
		mockMvc.perform(get("/players/find")).andExpect(status().isOk())
		.andExpect(model().attributeExists("players"))
		.andExpect(view().name("players/findPlayers"));
	}
    
    /*
	@Test
	void testProcessCreationFormSuccess() throws Exception {
		mockMvc.perform(post("/users/new").param("firstName", "John").param("lastName", "Bloggs").param("user.username", "joemama").param("user.email", "joemama@test.com").param("avatarUrl", "1").param("user.password", "1").with(csrf())
				)
		.andExpect(view().name("welcome"));
	}*/
    
    
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
	@WithMockUser(username = "paco")
	@Test
	void testInitUpdateMyselfForm() throws Exception {
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
}
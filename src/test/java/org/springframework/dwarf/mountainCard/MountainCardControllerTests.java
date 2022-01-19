package org.springframework.dwarf.mountainCard;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.dwarf.configuration.SecurityConfiguration;
import org.springframework.dwarf.mountain_card.MountainCard;
import org.springframework.dwarf.mountain_card.MountainCardController;
import org.springframework.dwarf.mountain_card.MountainCardService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Test class for {@link MountainCardController}
 *
 * @author Pablo Maromo
 * 
 */

@WebMvcTest(controllers = MountainCardController.class)
public class MountainCardControllerTests {
	
	@MockBean
	private MountainCardService mountainCardService;
	
	@Autowired
	private MockMvc mockMvc;

	private MountainCard card;
	
	@Test
	void testListMountainCards() throws Exception {
		mockMvc.perform(get("/cards/mountain")).andExpect(status().is4xxClientError());
		
	}

}

package org.springframework.dwarf.worker;

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
import org.springframework.dwarf.web.LoggedUserController;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Test class for {@link WorkerController}
 *
 * @author Pablo Marín
 * 
 */

@WebMvcTest(controllers = { WorkerController.class,
		LoggedUserController.class }, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
class WorkerControllerTest {

	private static final int TEST_WORKER_ID = 1;

	@MockBean
	private WorkerService workerService;

	@MockBean
	private LoggedUserController loggedUserController;

	@Autowired
	private MockMvc mockMvc;

	private Worker w0;

	@BeforeEach
	void setup() {
		w0 = new Worker();
		w0.setId(TEST_WORKER_ID);
		w0.setStatus(false);

	}

	@Test
	@WithMockUser(username = "pabmargom3")
	void listWorkers() throws Exception {
		mockMvc.perform(get("/workers")).andExpect(status().isOk())
				.andExpect(view().name("workers/listWorkers"))
				.andExpect(model().attributeExists("workers"));
	}

}

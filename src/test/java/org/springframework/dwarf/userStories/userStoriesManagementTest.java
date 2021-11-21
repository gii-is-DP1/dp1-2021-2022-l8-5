package org.springframework.dwarf.userStories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.dwarf.configuration.SecurityConfiguration;
import org.springframework.dwarf.web.CorrentUserController;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;



@WebMvcTest(controllers = userStoriesManagementTest.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
class userStoriesManagementTest {

    @Autowired
    private MockMvc mockMvc;

    @WithMockUser(username = "alonsoPodio", password = "ElNano2022")
    @Test
    void loginSuccesful() throws Exception {
        String userLogged = CorrentUserController.returnCurrentUserName();
        assertEquals(userLogged, "alonsoPodio");
    }

    @WithMockUser(username = "alonsoPodio", password = "ElNano2022")
    @Test
    void loginUnsuccesful() throws Exception {
        String userLogged = CorrentUserController.returnCurrentUserName();
        assertNotEquals(userLogged, "betrayal>theneostorm");
    }
}
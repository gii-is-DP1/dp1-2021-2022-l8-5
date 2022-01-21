package org.springframework.dwarf.user;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dwarf.web.LoggedUserController;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter({ Service.class, Component.class }))
public class UserServiceTest {

	@Autowired
	protected UserService userservice;

	@Autowired
	protected LoggedUserController loggedUserController;

	@Test
	@DisplayName("Delete a user")
	void testDeleteUser() {
		String username = "pabalvcar";
		Optional<User> user = userservice.findUser(username);
		if (user.isPresent()) {
			userservice.delete(user.get());
			assertThat(userservice.findUser(username)).isEmpty();
		} else {
			System.out.println("user not found");
		}
	}

	@Test
	@DisplayName("save user")
	void testSaveUser() {
		User user = new User();
		user.setEmail("miguelesdominguez@gmail.com");
		user.setEnabled(true);
		user.setUsername("xiscomigueles");
		user.setPassword("123");
		user.setAuthorities(null);
		userservice.saveUser(user);
		assertThat(userservice.findUser("xiscomigueles").isPresent());
	}

}

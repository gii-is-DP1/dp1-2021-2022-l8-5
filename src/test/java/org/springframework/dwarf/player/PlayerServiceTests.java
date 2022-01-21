/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.dwarf.player;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataAccessException;
import org.springframework.dwarf.game.Game;
import org.springframework.dwarf.user.DuplicatedEmailException;
import org.springframework.dwarf.user.DuplicatedUsernameException;
import org.springframework.dwarf.user.InvalidEmailException;
import org.springframework.dwarf.user.User;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter({ Service.class, Component.class }))
class PlayerServiceTests {
	@Autowired
	protected PlayerService playerService;

	@Test
	void shouldFindPlayersByLastName() {
		Collection<Player> players = this.playerService.findPlayerByLastName("Marin");
		assertThat(players.size()).isEqualTo(1);

		players = this.playerService.findPlayerByLastName("Daviss");
		assertThat(players.isEmpty()).isTrue();
	}

	@Test
	void shouldFindAll() {
		Iterable<Player> players = this.playerService.findAll();
		assertThat(players.spliterator().getExactSizeIfKnown()).isEqualTo(10);
	}

	@Test
	void shouldFindPlayerById() {
		Player player = this.playerService.findPlayerById(1);
		assertThat(player.getLastName()).startsWith("Marin");
	}

	@Test
	void shouldFindPlayerByUserName() {
		Player player = this.playerService.findPlayerByUserName("test");
		assertThat(player.getLastName()).startsWith("Test");
	}

	@Test
	@Transactional
	public void shouldInsertPlayer()
			throws DataAccessException, DuplicatedUsernameException, DuplicatedEmailException, InvalidEmailException {
		Collection<Player> players = this.playerService.findPlayerByLastName("Schultz");
		int found = players.size();

		Player player = new Player();
		player.setFirstName("Sam");
		player.setLastName("Schultz");
		player.setAvatarUrl("https://www.w3schools.com/w3images/avatar2.png");
		User user = new User();
		user.setUsername("Sam");
		user.setPassword("supersecretpassword");
		user.setEmail("superemail@email.com");
		user.setEnabled(true);
		player.setUser(user);

		this.playerService.savePlayer(player);
		assertThat(player.getId().longValue()).isNotEqualTo(0);

		players = this.playerService.findPlayerByLastName("Schultz");
		assertThat(players.size()).isEqualTo(found + 1);
	}

	@Test
	@Transactional
	public void shouldInsertPlayerUsernameDuplicated()
			throws DataAccessException, DuplicatedUsernameException, DuplicatedEmailException, InvalidEmailException {

		Player player = new Player();
		player.setFirstName("Sam");
		player.setLastName("Schultz");
		player.setAvatarUrl("https://www.w3schools.com/w3images/avatar2.png");
		User user = new User();
		user.setUsername("pabmargom3");
		user.setPassword("supersecretpassword");
		user.setEmail("superemail@email.com");
		user.setEnabled(true);
		player.setUser(user);

		assertThrows(DuplicatedUsernameException.class, () -> {
			this.playerService.savePlayer(player);
		});

	}

	@Test
	@Transactional
	public void shouldInsertPlayerEmailDuplicated()
			throws DataAccessException, DuplicatedUsernameException, DuplicatedEmailException, InvalidEmailException {

		Player player = new Player();
		player.setFirstName("Sam");
		player.setLastName("Schultz");
		player.setAvatarUrl("https://www.w3schools.com/w3images/avatar2.png");
		User user = new User();
		user.setUsername("sam");
		user.setPassword("supersecretpassword");
		user.setEmail("test10@test.com");
		user.setEnabled(true);
		player.setUser(user);

		assertThrows(DuplicatedEmailException.class, () -> {
			this.playerService.savePlayer(player);
		});

	}

	@Test
	@Transactional
	public void shouldInsertPlayerEmailInvalid()
			throws DataAccessException, DuplicatedUsernameException, DuplicatedEmailException, InvalidEmailException {

		Player player = new Player();
		player.setFirstName("Sam");
		player.setLastName("Schultz");
		player.setAvatarUrl("https://www.w3schools.com/w3images/avatar2.png");
		User user = new User();
		user.setUsername("sam");
		user.setPassword("supersecretpassword");
		user.setEmail("");
		user.setEnabled(true);
		player.setUser(user);

		assertThrows(InvalidEmailException.class, () -> {
			this.playerService.savePlayer(player);
		});

	}

	@Test
	@Transactional
	public void shouldInsertPlayerSameDifferentId()
			throws DataAccessException, DuplicatedUsernameException, DuplicatedEmailException, InvalidEmailException {

		Player player = new Player();
		player.setFirstName("Pablo");
		player.setLastName("Marin");
		player.setId(10);
		player.setAvatarUrl("https://www.w3schools.com/w3images/avatar2.png");
		User user = new User();
		user.setUsername("pabmargom3");
		user.setPassword("supersecretpassword");
		user.setEmail("test10@test.com");
		user.setEnabled(true);
		player.setUser(user);

		assertThrows(DuplicatedUsernameException.class, () -> {
			this.playerService.savePlayer(player);
		});

	}

	@Test
	@Transactional
	void shouldUpdatePlayer()
			throws DataAccessException, DuplicatedUsernameException, DuplicatedEmailException, InvalidEmailException {
		Player player = this.playerService.findPlayerById(1);
		String oldLastName = player.getLastName();
		String newLastName = oldLastName + "X";

		player.setLastName(newLastName);
		this.playerService.savePlayer(player);

		// retrieving new name from database
		player = this.playerService.findPlayerById(1);
		assertThat(player.getLastName()).isEqualTo(newLastName);
	}

	@Test
	@Transactional
	void shouldDeletePlayer() throws DeletePlayerInGameException {
		long numPlayers = playerService.findAll().spliterator().getExactSizeIfKnown();
		Player player = this.playerService.findPlayerById(1);
		playerService.delete(player);
		long numPlayersAfterDelete = playerService.findAll().spliterator().getExactSizeIfKnown();
		assertThat(numPlayers).isEqualTo(numPlayersAfterDelete + 1);
	}

	@Test
	void testIsExceptionalCase() {
		Game g = new Game();
		g.setFinishDate(null);
		assertThrows(DeletePlayerInGameException.class, () -> {
			playerService.isExceptionalCase(g);
		});
	}

}

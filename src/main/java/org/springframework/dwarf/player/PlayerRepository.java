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

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.dwarf.player.PlayerRepository;

/**
 * Spring Data JPA OwnerRepository interface
 *
 * @author Michael Isvy
 *  * @author Pablo Marin
 * @autor Pablo Alvarez
 * @since 15.1.2013
 */
public interface PlayerRepository extends CrudRepository<Player, Integer> {

	/**
	 * Retrieve <code>Player</code>s from the data store by last name, returning all owners
	 * whose last name <i>starts</i> with the given name.
	 * @param lastName Value to search for
	 * @return a <code>Collection</code> of matching <code>Player</code>s (or an empty
	 * <code>Collection</code> if none found)
	 */	
	@Query("SELECT DISTINCT player2 FROM Player player2 WHERE player2.lastName LIKE :lastName% AND player2.id>0")
	public Collection<Player> findByLastName(@Param("lastName") String lastName);


	/**
	 * Retrieve an <code>Player</code> from the data store by username.
	 * @param username the username to search for
	 * @return the <code>Player </code> if found
	 * @throws org.springframework.dao.DataRetrievalFailureException if not found
	 */	
	
	@Query("SELECT player FROM Player player WHERE player.user.username =:username")
	public Player findByUsername(@Param("username") String username);
	
	/**
	 * Retrieve an <code>Player</code> from the data store by email.
	 * @param username the username to search for
	 * @return the <code>Player</code> if found
	 * @throws org.springframework.dao.DataRetrievalFailureException if not found
	 */	
	
	@Query("SELECT player FROM Player player WHERE player.user.email =:email")
	public Player findByEmail(@Param("email") String email);

}

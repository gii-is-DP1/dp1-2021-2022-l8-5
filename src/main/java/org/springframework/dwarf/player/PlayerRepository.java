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

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.dwarf.model.BaseEntity;
import org.springframework.dwarf.player.PlayerRepository;

/**
 * Spring Data JPA OwnerRepository interface
 *
 * @author Michael Isvy
 *  * @author Pablo Marin
 * @autor Pablo Alvarez
 * @since 15.1.2013
 */
public interface PlayerRepository extends Repository<Player, Integer> {

	/**
	 * Save an <code>Player</code> to the data store, either inserting or updating it.
	 * @param owner the <code>Player</code> to save
	 * @see BaseEntity#isNew
	 */
	void save(Player owner) throws DataAccessException;

	/**
	 * Retrieve <code>Player</code>s from the data store by last name, returning all owners
	 * whose last name <i>starts</i> with the given name.
	 * @param lastName Value to search for
	 * @return a <code>Collection</code> of matching <code>Player</code>s (or an empty
	 * <code>Collection</code> if none found)
	 */	
	@Query("SELECT DISTINCT player2 FROM Player player2 WHERE player2.lastName LIKE :lastName%")
	public Collection<Player> findByLastName(@Param("lastName") String lastName);


	/**
	 * Retrieve an <code>Player</code> from the data store by id.
	 * @param id the id to search for
	 * @return the <code>Player</code> if found
	 * @throws org.springframework.dao.DataRetrievalFailureException if not found
	 */	
	@Query("SELECT player2 FROM Player player2 WHERE player2.id =:id")
	public Player findById(@Param("id") int id);
	
	/**
	 * Retrieve an <code>Player</code> from the data store by username.
	 * @param username the username to search for
	 * @return the <code>Player id</code> if found
	 * @throws org.springframework.dao.DataRetrievalFailureException if not found
	 */	
	
	@Query(value="SELECT player2.id FROM Player player2 WHERE player2.username =:username",nativeQuery = true)
	public Integer findByUsername(@Param("username") String username);

}

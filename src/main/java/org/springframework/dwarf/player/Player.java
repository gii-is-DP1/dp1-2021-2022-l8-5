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

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Range;
import org.springframework.core.style.ToStringCreator;
import org.springframework.dwarf.model.Person;
import org.springframework.dwarf.user.User;

/**
 * Simple JavaBean domain object representing an player.
 *
 *  @author Pablo Marin
 * @autor Pablo Alvarez
 */
@Entity
@Table(name = "player")
public class Player extends Person {
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "username", referencedColumnName = "username")
	private User user;
	//
	@Column(name = "avatar_url")
	@NotEmpty
	String avatarUrl;
	
	@Range(min=1,max=3)
	Integer turn;
	
	public Integer getTurn() {
		return turn;
	}
	
	public void setTurn(Integer turn) {
		this.turn = turn;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getAvatarUrl() {
		return avatarUrl;
	}

	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}
	
	public String getUsername() {
		return user.getUsername();
	}

	public void setUsername(String Username) {
		user.setUsername(Username);
	}
	
	public String getEmail() {
		return user.getEmail();
	}

	public void setEmail(String email) {
		user.setEmail(email);
	}
	
	public String getPassword() {
		return user.getPassword();
	}

	public void setPassword(String password) {
		user.setPassword(password);
	}
	
	@Override
	public String toString() {
		return new ToStringCreator(this)

				.append("id", this.getId()).append("new", this.isNew()).append("lastName", this.getLastName())
				.append("firstName", this.getFirstName())
				.toString();
	}

}

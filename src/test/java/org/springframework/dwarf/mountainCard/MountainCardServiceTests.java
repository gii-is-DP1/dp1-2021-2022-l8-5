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
package org.springframework.dwarf.mountainCard;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter({ Service.class, Component.class }))
class MountainCardServiceTests {
	@Autowired
	protected MountainCardService mountainCardService;

	@Test
	void shouldFindMountainCardById() {
		MountainCard mountainCard = this.mountainCardService.findByMountainCardId(1).get();
		assertThat(mountainCard.getXPosition()).isEqualTo(1);
		assertThat(mountainCard.getYPosition()).isEqualTo(0);
		assertThat(mountainCard.getName()).isEqualTo("Iron Seam");
	}

	@Test
	void ShouldFindAllMountainCards() {
		Iterable<MountainCard> mountainCards = this.mountainCardService.findAll();
		assertThat(mountainCards.spliterator().getExactSizeIfKnown()).isEqualTo(54);
	}

	@Test
	void shouldFindByGroupCard() {
		List<MountainCard> mountainCards = mountainCardService.findByGroupCard(1);
		assertThat(mountainCards.size()).isEqualTo(9);
	}

	@Test
	void shouldFindInitialCardByPosition() {
		// Initial card with position (1,0)
		MountainCard card = mountainCardService.findByMountainCardId(1).get();
		MountainCard cardSearch = mountainCardService.findInitialCardByPosition(1, 0);
		assertThat(cardSearch).isEqualTo(card);
	}

	@Test
	@DisplayName("Delete a card")
	void testDeleteMountainCard() {
		Optional<MountainCard> mc = mountainCardService.findByMountainCardId(1);
		if (mc.isPresent()) {
			mountainCardService.delete(mc.get());
			assertThat(mountainCardService.findByMountainCardId(1)).isEmpty();
		} else {
			System.out.println("MountainCard not found");
		}
	}
}

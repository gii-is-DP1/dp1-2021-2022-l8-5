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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dwarf.mountain_card.CardType;
import org.springframework.dwarf.mountain_card.MountainCard;
import org.springframework.dwarf.mountain_card.MountainCardService;
import org.springframework.dwarf.mountain_card.MountainDeck;
import org.springframework.dwarf.mountain_card.MountainDeckService;
import org.springframework.dwarf.player.Player;
import org.springframework.dwarf.player.PlayerService;
import org.springframework.dwarf.user.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration test of the Service and the Repository layer.
 * <p>
 * ClinicServiceSpringDataJpaTests subclasses benefit from the following services provided
 * by the Spring TestContext Framework:
 * </p>
 * <ul>
 * <li><strong>Spring IoC container caching</strong> which spares us unnecessary set up
 * time between test execution.</li>
 * <li><strong>Dependency Injection</strong> of test fixture instances, meaning that we
 * don't need to perform application context lookups. See the use of
 * {@link Autowired @Autowired} on the <code>{@link
 * MountainDeckServiceTests#clinicService clinicService}</code> instance variable, which uses
 * autowiring <em>by type</em>.
 * <li><strong>Transaction management</strong>, meaning each test method is executed in
 * its own transaction, which is automatically rolled back by default. Thus, even if tests
 * insert or otherwise change database state, there is no need for a teardown or cleanup
 * script.
 * <li>An {@link org.springframework.context.ApplicationContext ApplicationContext} is
 * also inherited and can be used for explicit bean lookup if necessary.</li>
 * </ul>
 *
 * @author Ken Krebs
 * @author Rod Johnson
 * @author Juergen Hoeller
 * @author Sam Brannen
 * @author Michael Isvy
 * @author Dave Syer
 */

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
class MountainDeckServiceTests {                
        @Autowired
	protected MountainDeckService mountainDeckService;

	@Test
	void shouldFindMountainDeckById() {
		MountainDeck mountainDeck = this.mountainDeckService.findByMountainDeckId(1).get();
		assertThat(mountainDeck.getMountainCards().size()).isEqualTo(9);
	}
	
	@Test
	void ShouldFindAllMountainDecks() {
		Iterable<MountainDeck> mountainDecks = this.mountainDeckService.findAll();
		assertThat(mountainDecks.spliterator().getExactSizeIfKnown()).isEqualTo(2);
	}
	
	@Test
	@BeforeEach
	@Transactional
	public void shouldInsertMountainDeck() {
		
		Iterable<MountainDeck> mountainDecks = this.mountainDeckService.findAll();
		Long found = mountainDecks.spliterator().getExactSizeIfKnown();
		
		MountainDeck mountainDeck = new MountainDeck();
		mountainDeck.setXPosition(0);
		mountainDeck.setYPosition(0);
			
		MountainCard mountainCard = new MountainCard();
		mountainCard.setCardType(CardType.MINE);
		mountainCard.setDescription("Instantly kills the player if its name is TheNeoStormZ");
		mountainCard.setGroup(2);
		mountainCard.setName("Wall of Flesh");
		mountainCard.setXPosition(1);
		mountainCard.setYPosition(0);
		
		List<MountainCard> mountainCardList = new ArrayList<MountainCard>();
		mountainCardList.add(mountainCard);
		
		mountainDeck.setMountainCards(mountainCardList);
		
		this.mountainDeckService.saveMountainDeck(mountainDeck);
		Iterable<MountainDeck> mountainDecksUpdated = this.mountainDeckService.findAll();
		assertThat(mountainDecksUpdated.spliterator().getExactSizeIfKnown()).isEqualTo(found+1);
		
	}

	/* el delete peta
	@Test
	@Transactional
	void shouldDeleteMountainDeck() {
		Iterable<MountainDeck> mountainDecks = this.mountainDeckService.findAll();
		Long found = mountainDecks.spliterator().getExactSizeIfKnown();
		
		MountainDeck mountainDeck = this.mountainDeckService.findByMountainDeckId(1).get();
		this.mountainDeckService.delete(mountainDeck);
		
		Iterable<MountainDeck> mountainDecksUpdated = this.mountainDeckService.findAll();
		assertThat(mountainDecksUpdated.spliterator().getExactSizeIfKnown()).isEqualTo(found-1);
	}
*/

}

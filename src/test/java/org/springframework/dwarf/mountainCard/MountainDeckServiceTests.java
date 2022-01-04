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
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dwarf.mountain_card.CardType;
import org.springframework.dwarf.mountain_card.MountainCard;
import org.springframework.dwarf.mountain_card.MountainDeck;
import org.springframework.dwarf.mountain_card.MountainDeckService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


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
		System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAA " + mountainDeck.getId()); //Por alguna razon el ID es 6

		Iterable<MountainDeck> mountainDecksUpdated = this.mountainDeckService.findAll();
		assertThat(mountainDecksUpdated.spliterator().getExactSizeIfKnown()).isEqualTo(found+1);
		
	}
	
	@Test
	void shouldCreateMountainDeck() {
		Iterable<MountainDeck> mountainDecks = this.mountainDeckService.findAll();
		Long founds = mountainDecks.spliterator().getExactSizeIfKnown();
		
		this.mountainDeckService.createMountainDeck();
		
		Iterable<MountainDeck> mountainDecksAfterCreate = this.mountainDeckService.findAll();
		Long foundsAfterCreate = mountainDecksAfterCreate.spliterator().getExactSizeIfKnown();
		
		assertThat(foundsAfterCreate).isEqualTo(founds+1);
	}

	@Test
	@Transactional
	void shouldDeleteMountainDeck() {
		
		MountainDeck mountainDeck = this.mountainDeckService.findByMountainDeckId(2).get();
		this.mountainDeckService.delete(mountainDeck);
		
		Optional<MountainDeck> spdeleted = mountainDeckService.findByMountainDeckId(2);
		assertThat(spdeleted.isPresent()).isFalse();
	}


}

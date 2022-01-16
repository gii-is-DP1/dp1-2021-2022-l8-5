package org.springframework.dwarf.special_card;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class SpecialDeckServiceTest {
	
	@Autowired
	private SpecialDeckService specialDeckService;
	@Autowired
	private SpecialCardService specialCardService;
	
	@Test
	@DisplayName("")
	void testSpecialDeckServiceCount() {
		int count = specialDeckService.specialDeckCount();
		assertThat(count).isEqualTo(2);
	}
	@Test
	@DisplayName("Returns all special_decks")
	void testFindAllSpecialDecks() {
		Iterable<SpecialDeck> iterator = specialDeckService.findAll();
		assertThat(iterator.spliterator().getExactSizeIfKnown()).isEqualTo(2); 
	}
	
	@Test 
	@DisplayName("Returns a special_deck by its Id correctly - Positive")
	void testFinBySpecialDeckIdPositive() {
		int specialdeckid=1;
		Optional<SpecialDeck> sp= specialDeckService.findBySpecialDeckId(specialdeckid);
		assertThat(sp.get().getXPosition()).isEqualTo(0);
		assertThat(sp.get().getYPosition()).isEqualTo(0);
		System.out.println("Special deck not found");
	}
	
	@Test 
	@DisplayName("Returns a special_deck by its Id correctly - Negative")
	void testFinBySpecialDeckIdNegative() {
		int specialdeckid=17;
		Optional<SpecialDeck> sp= specialDeckService.findBySpecialDeckId(specialdeckid);
		assertThat(sp.isEmpty());
		System.out.println("Special deck not found");
	
	}
	@Test
	@DisplayName("Delete a special deck - Positive")
	void testDeleteSpecialDeckPositive() {
		int spid=2;
		Optional<SpecialDeck> sp= specialDeckService.findBySpecialDeckId(spid);
		specialDeckService.delete(sp.get());
		Optional<SpecialDeck> spdeleted =specialDeckService.findBySpecialDeckId(spid);
		assertThat(spdeleted.isPresent()).isFalse();
		
	}
	
	@Test
	@DisplayName("Delete a special deck - Negative")
	void testDeleteSpecialDeckNegative() {
		int spid=17;
		Optional<SpecialDeck> sp= specialDeckService.findBySpecialDeckId(spid);
		assertThat(sp.isEmpty());
		System.out.println("Special deck not found");	
	}
	
	@Test
	@DisplayName("Create SpecialDeck")
	void testCreateSpecialDeck() {
		List<Integer> sc= new ArrayList<Integer>();
		sc.add(specialCardService.findBySpecialCardId(1).get().getId());
		sc.add(specialCardService.findBySpecialCardId(2).get().getId());
		sc.add(specialCardService.findBySpecialCardId(3).get().getId());
		SpecialDeck sp = specialDeckService.createSpecialDeck(0, 0, sc);
		assertThat(sp).isNotNull();
	}
	
	
	
	@Test
	@DisplayName("Save a special deck")
	void testSaveSpecialDeck() {
		SpecialDeck sp = new SpecialDeck();
		sp.setXPosition(0);
		sp.setYPosition(2);
		List<SpecialCard> cards = new ArrayList<SpecialCard>();
		specialCardService.findAll().forEach(cards::add);
		sp.setSpecialCard(cards);
		
		specialDeckService.saveSpecialDeck(sp);
		int spid=sp.getId();
		Optional<SpecialDeck> sptest =specialDeckService.findBySpecialDeckId(spid);
		assertThat(sptest.isPresent()).isTrue();
			
	}	

}

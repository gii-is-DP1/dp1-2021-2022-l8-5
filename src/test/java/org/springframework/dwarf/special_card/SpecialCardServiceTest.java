package org.springframework.dwarf.special_card;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class SpecialCardServiceTest {
	
	@Autowired
	private SpecialCardService specialCardService;
	
	@Test
	@DisplayName("Returns the number of special cards created")
	void testSpecialCardCount() {
		int totalCards = specialCardService.cardCount();
		assertThat(totalCards).isEqualTo(9);
	}
	
	@Test
	@DisplayName("Returns a special card by its Id correctly")
	void testFindBySpecialCardId() {
		Optional<SpecialCard> specialCard = specialCardService.findBySpecialCardId(1);
		if(specialCard.isPresent()) {
			assertThat(specialCard.get().getName()).isEqualTo("Muster an Army");
		}else {
			System.out.println("Special card not found");
		}
	}
	
	@Test
	@DisplayName("Returns all special cards")
	void testFindAll() {
		Iterable<SpecialCard> specialCards = specialCardService.findAll();
		assertThat(specialCards.spliterator().getExactSizeIfKnown()).isEqualTo(9);
	}
	
}

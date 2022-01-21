package org.springframework.dwarf.specialCard;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
/**
 * @author FJ Migueles
 */
@Service
public class SpecialDeckService {
	
	private SpecialDeckRepository specialDeckRepo;
	private SpecialCardService specialCardSer;
	
	@Autowired
	public 	SpecialDeckService(SpecialDeckRepository specialDeckRepo, SpecialCardService specialCardSer) {
		this.specialDeckRepo = specialDeckRepo;
		this.specialCardSer=specialCardSer;
	}		
	
	@Transactional
	public int specialDeckCount() {
		return (int) specialDeckRepo.count();
	}
 
	public Iterable<SpecialDeck> findAll() {
		return specialDeckRepo.findAll();
	}
	@Transactional(readOnly = true)
	public Optional<SpecialDeck> findBySpecialDeckId(int id){
		return specialDeckRepo.findById(id);
	}
	
	public void delete(SpecialDeck specialDeck) {
		specialDeckRepo.delete(specialDeck);
	}
	
	@Transactional
	public void saveSpecialDeck(SpecialDeck specialDeck) throws DataAccessException {
		//creating specialDeck
		specialDeckRepo.save(specialDeck);		
	}
	
	@Transactional
	public SpecialDeck createSpecialDeck(Integer xposition, Integer yposition,List<Integer> specialCardsId) throws DataAccessException {
		SpecialDeck deck = new SpecialDeck(xposition,yposition);
		List<SpecialCard> spcards= new ArrayList<SpecialCard>();
		for(Integer cardId:specialCardsId) {
			spcards.add(specialCardSer.findBySpecialCardId(cardId).get());
		}
		deck.setSpecialCard(spcards);
		specialDeckRepo.save(deck);
		
		
		return deck;
	}
}

package org.springframework.dwarf.mountainCard;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Diego Ruiz Gil
 */
@Service
public class MountainDeckService {
	
	private MountainDeckRepository mountainDeckRepo;
	private MountainCardService mountainCardSer;
	
	@Autowired
	public MountainDeckService(MountainDeckRepository mountainDeckRepo, MountainCardService mountainCardSer) {
		this.mountainDeckRepo = mountainDeckRepo;
		this.mountainCardSer = mountainCardSer;
	}		

	@Transactional(readOnly = true)
	public Iterable<MountainDeck> findAll() {
		return mountainDeckRepo.findAll();
	}
	
	@Transactional(readOnly = true)
	public Optional<MountainDeck> findByMountainDeckId(int id){
		return mountainDeckRepo.findById(id);
	}
	
	@Transactional
	public void delete(MountainDeck mountainDeck) {
		mountainDeckRepo.delete(mountainDeck);
	}
	
	@Transactional
	public void saveMountainDeck(MountainDeck mountainDeck) throws DataAccessException {
		//creating mountainDeck
		mountainDeckRepo.save(mountainDeck);		
	}
	
	
	@Transactional
	public MountainDeck createMountainDeck() throws DataAccessException{
		MountainDeck deck = new MountainDeck();
		
		List<MountainCard> cardsGroupTwo = mountainCardSer.findByGroupCard(2);
		List<MountainCard> cardsGroupThree = mountainCardSer.findByGroupCard(3);
		
		List<MountainCard> mountainCards = new ArrayList<MountainCard>();
		mountainCards.addAll(cardsGroupTwo);
		mountainCards.addAll(cardsGroupThree);
		
		deck.setMountainCards(mountainCards);
		
		mountainDeckRepo.save(deck);
		
		return deck;
	}
}

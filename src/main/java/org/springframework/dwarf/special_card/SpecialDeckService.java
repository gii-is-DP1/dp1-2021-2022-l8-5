package org.springframework.dwarf.special_card;

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
	
	@Autowired
	public 	SpecialDeckService(SpecialDeckRepository specialDeckRepo) {
		this.specialDeckRepo = specialDeckRepo;
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

}

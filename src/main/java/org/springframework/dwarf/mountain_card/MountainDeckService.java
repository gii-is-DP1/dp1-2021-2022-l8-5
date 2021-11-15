package org.springframework.dwarf.mountain_card;

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
	
	@Autowired
	public MountainDeckService(MountainDeckRepository mountainDeckRepo) {
		this.mountainDeckRepo = mountainDeckRepo;
	}		
	
	@Transactional
	public int mountainDeckCount() {
		return (int) mountainDeckRepo.count();
	}

	public Iterable<MountainDeck> findAll() {
		return mountainDeckRepo.findAll();
	}
	@Transactional(readOnly = true)
	public Optional<MountainDeck> findByMountainDeckId(int id){
		return mountainDeckRepo.findById(id);
	}
	
	public void delete(MountainDeck mountainDeck) {
		mountainDeckRepo.delete(mountainDeck);
	}
	
	@Transactional
	public void saveMountainDeck(MountainDeck mountainDeck) throws DataAccessException {
		//creating mountainDeck
		mountainDeckRepo.save(mountainDeck);		

	}
}

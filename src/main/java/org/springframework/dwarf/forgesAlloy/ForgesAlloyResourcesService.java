package org.springframework.dwarf.forgesAlloy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ForgesAlloyResourcesService {
	
	@Autowired
	private ForgesAlloyResourcesRepository farRepo;
	
	@Transactional(readOnly = true)
	public ForgesAlloyResources findByCardName(String cardName) {
		return farRepo.findByCardName(cardName);
	}
}

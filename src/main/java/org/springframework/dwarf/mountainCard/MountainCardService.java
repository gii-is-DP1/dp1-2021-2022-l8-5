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


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Mostly used as a facade for all Petclinic controllers Also a placeholder
 * for @Transactional and @Cacheable annotations
 *
 * @author Pablo Marin
 * @author Pablo Álvarez
 *
 */
@Service
public class MountainCardService {

	
	private MountainCardRepository mountainCardRepo;
	
	@Autowired
	public MountainCardService(MountainCardRepository mountainCardRepository) {
		this.mountainCardRepo = mountainCardRepository;
	}

	public Iterable<MountainCard> findAll() {
		return mountainCardRepo.findAll();
	}
	
	@Transactional(readOnly = true)
	public List<MountainCard> findByGroupCard(Integer cardGroup){
		return mountainCardRepo.findByGroupCards(cardGroup);
	}
	
	@Transactional(readOnly = true)
	public MountainCard findInitialCardByPosition(Integer xposition, Integer yposition) {
		return mountainCardRepo.findInitialCardByPosition(xposition, yposition);
	}
	
	@Transactional(readOnly = true)
	public Optional<MountainCard> findByMountainCardId(int id){
		return mountainCardRepo.findById(id);
	}
	
	public void delete(MountainCard card) {
		mountainCardRepo.delete(card);
	}
	
}

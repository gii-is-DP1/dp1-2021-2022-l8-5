package org.springframework.samples.petclinic.resources;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

/**
 * @author David Zamora
 */

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class ResourcesServiceTest {
    

	@Autowired
	private ResourcesService resourcesService;
	
	
	@Test
	public void testCountWithInitialData() {
		int count = resourcesService.ResourcesCount();
		assertEquals(count,5);
	}
	
	
	@Test
	public void testFindByResourcesId() {
		int id = 1;
		
		Optional<Resources> Resources = resourcesService.findByResourcesId(id);
		System.out.println("------------TEST FIND BY Resources ID------------");
		Resources p = Resources.orElse(null);
		//assertEquals(p, null);
		
		if(p != null) {
			System.out.println("Resources with id: " + id + ", : " + p.getIron() + "iron" 
            + p.getSteel() + "steel" + p.getGold() + "gold" + p.getItems() + "items" + p.getBadges() + "badges");
		}else {
			System.out.println("Resources not found");
		}
		System.out.println("------------------------");
	}
	
	@Test
	public void testSaveResources() {
		Resources ResourcesTest = new Resources();
		//ResourcesTest.setEnabled(true);
		ResourcesTest.setIron(3);
        ResourcesTest.setSteel(3);
        ResourcesTest.setGold(3);
        ResourcesTest.setItems(3);
        ResourcesTest.setBadges(3);
		
		resourcesService.saveResources(ResourcesTest);
		int id = ResourcesTest.getId();
		
		Optional<Resources> Resources = resourcesService.findByResourcesId(id);
		System.out.println("------------TEST SAVE Resources------------");
		Resources p = Resources.orElse(null);
		if(p != null) {
			System.out.println("Resources with id: " + id + ", : " + p.getIron() + "iron" 
            + p.getSteel() + "steel" + p.getGold() + "gold" + p.getItems() + "items" + p.getBadges() + "badges");
		}else {
			System.out.println("Resources not found");
		}
		System.out.println("------------------------");
	}
	
	@Test
	public void testDeleteResources() {
		int id = 2;
		
		Optional<Resources> Resources = resourcesService.findByResourcesId(id);
		System.out.println("------------TEST DELETE Resources------------");
		Resources p = Resources.orElse(null);
		if(p != null) {
			resourcesService.delete(p);
			Resources deletedResources = resourcesService.findByResourcesId(id).orElse(null);
			assertEquals(deletedResources, null);
		}else {
			System.out.println("Resources not found");
		}
		System.out.println("------------------------");
	}
}


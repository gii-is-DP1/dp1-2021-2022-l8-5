package org.springframework.dwarf.resources;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

/**
 * @author David Zamora
 * @author Jose Ignacio Garcia
 */

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class ResourcesServiceTest {
    

	@Autowired
	private ResourcesService resourcesService;
	
	
	@Test
	public void testCountWithInitialData() {
		int count = resourcesService.ResourcesCount();
		assertEquals(count,1);
	}

	@Test
	public void testFindAll() {
		Iterable<Resources> resources = this.resourcesService.findAll();
		assertEquals(resources.spliterator().getExactSizeIfKnown(), 1);
	}
	
	
	@Test
	public void testFindByResourcesId() {
		int id = 1;
		
		Optional<Resources> Resources = resourcesService.findByResourcesId(id);
		System.out.println("------------TEST FIND BY Resources ID------------");
		Resources p = Resources.orElse(null);	//Si no lo encuentra, devuelve null
		assertEquals(p.getGold(), 3);
	}
	
	@Test
	public void testSaveResources() {
		Resources ResourcesTest = new Resources();
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
		assertEquals(p.getGold(), 3);

	}
	
	@Test
	public void testDeleteResources() {
		int id = 1;
		
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


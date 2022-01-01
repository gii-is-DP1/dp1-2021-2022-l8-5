package org.springframework.dwarf.forgesAlloyResources;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dwarf.forgesAlloy.ForgesAlloyResources;
import org.springframework.dwarf.forgesAlloy.ForgesAlloyResourcesService;
import org.springframework.dwarf.resources.ResourceType;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class ForgesAlloyResourcesServiceTest {
	
	@Autowired
	private ForgesAlloyResourcesService forgeAlloyResourceService;
	
	@Test
	@DisplayName("Find ForgesAlloyResources by card name")
	void testFindByCardName() {
		String cardName = "Alloy Steel";
		
		ForgesAlloyResources forgesAlloyResources = this.forgeAlloyResourceService.findByCardName(cardName);
		
		assertThat(forgesAlloyResources.getResourcesReceived().getResource()).isEqualTo(ResourceType.STEEL);
	}
}

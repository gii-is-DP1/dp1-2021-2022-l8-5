package org.springframework.dwarf.resources;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Repository;


@DataJpaTest(includeFilters = @ComponentScan.Filter(Repository.class))
public class ResourcesRepositoryTest {
    @Autowired
    protected ResourcesRepository resourcesRepository;

        @Test
        void findByGameId() throws Exception {
            Collection<Resources> resources = resourcesRepository.findByGameId(1);
            assertEquals(resources.spliterator().getExactSizeIfKnown(), 1);
        }

}
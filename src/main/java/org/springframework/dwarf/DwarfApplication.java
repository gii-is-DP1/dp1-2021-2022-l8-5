package org.springframework.dwarf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication()
public class DwarfApplication {

	public static void main(String[] args) {
		SpringApplication.run(DwarfApplication.class, args);
	}

}

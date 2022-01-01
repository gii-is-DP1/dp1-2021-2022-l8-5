package org.springframework.dwarf.forgesAlloy;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.springframework.dwarf.resources.ResourceType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class ResourceAmount {
	@Enumerated(value = EnumType.STRING)
	private ResourceType resource;
	private Integer amount;
	
	public ResourceAmount() {
		super();
	}
}

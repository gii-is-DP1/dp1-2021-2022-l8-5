package org.springframework.dwarf.forgesAlloy;

import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import org.springframework.dwarf.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="forgesAlloyResources")
public class ForgesAlloyResources extends BaseEntity{
	
	@Column(name="cardname")
	String cardName;
	
	@ElementCollection
	@CollectionTable(name="resources_given", joinColumns=@JoinColumn(name="forgesAlloyResourcesId"))
	List<ResourceAmount> resourcesGiven;
	
	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name="resource",
				column=@Column(name="resourceTypeReceived")),
		@AttributeOverride(name="amount",
			column=@Column(name="amountReceived"))
	})
	ResourceAmount resourcesReceived;
	
	
	
}

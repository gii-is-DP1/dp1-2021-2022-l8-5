package org.springframework.dwarf.resources;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.dwarf.model.BaseEntity;
/**
 * Simple JavaBean domain object representing the resources of the game.
 *
 * @author David Zamora
 * @author Jose Ignacio Garcia
 */

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "resources")
public class Resources extends BaseEntity{
	
    Integer iron;
    Integer gold;
    Integer steel;
    Integer items;
    Integer badges;
	

}

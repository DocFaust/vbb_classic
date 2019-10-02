package de.docfaust.vbb.service;

import java.io.Serializable;
import java.util.List;

import de.docfaust.vbb.data.entity.Group;

public interface GroupService extends Serializable {

	/**
	 * Gibt alle Gruppen zurück.
	 * 
	 * @return liste der Gruppen
	 */
	List<Group> getGroups();

	Group findByName(String string);

}
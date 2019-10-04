package de.docfaust.vbb.service;

import java.io.Serializable;
import java.util.List;

import de.docfaust.vbb.data.entity.Group;

/**
 * Services for the Group Entity.
 * @author wfa339
 *
 */
public interface GroupService extends Serializable {

	/**
	 * Gibt alle Gruppen zurück.
	 * 
	 * @return liste der Gruppen
	 */
	List<Group> getGroups();

	/**
	 * finds a Group by the Name.
	 * @param name name
	 * @return Group
	 */
	Group findByName(String name);

}
package de.docfaust.vbb.data.facade;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import de.docfaust.vbb.data.entity.Group;
import de.docfaust.vbb.data.facades.GroupFacade;
import de.docfaust.vbb.data.util.JpaBaseRolledBackTestCase;

public class TestGroupFacade extends JpaBaseRolledBackTestCase {

	@Test
	public void test() {
		assertThat(new GroupFacade(), not(nullValue()));
	}

	@Test
	public void testFindAll() {
		assertFalse(facadenFactory.getGroupFacade().findAll().isEmpty());
		assertEquals(3, facadenFactory.getGroupFacade().count());
	}

	@Test
	public void testFindByName() {
		Group group = facadenFactory.getGroupFacade().findByName("ADMIN");
		logger.info(group.toString());
		assertEquals("Administrator", group.getDescription());
		assertEquals("ADMIN", group.getName());
		assertEquals(1, group.getId());
	}

	@Test
	public void testSaveGroup() {
		Group group = new Group();
		group.setName("READERS");
		group.setDescription("Benutzer mit Leseberechtigung");

		facadenFactory.getGroupFacade().create(group);

		assertEquals(4, facadenFactory.getGroupFacade().count());
	}

	@Test
	public void testRemoveGroup() {
		printGroups();
		int count = facadenFactory.getGroupFacade().count();
		logger.info("Anzahl Gruppen: " + count);

		Group group = new Group();
		group.setDescription("Lesender Zugriff");
		group.setName("READERS");
		facadenFactory.getGroupFacade().create(group);
		printGroups();
		logger.info(group.toString());
		assertThat(facadenFactory.getGroupFacade().count(), equalTo(count + 1));
		facadenFactory.getGroupFacade().remove(group);
		printGroups();
		assertThat(facadenFactory.getGroupFacade().count(), equalTo(count));
	}

	@Test
	public void testRemoveGroupNotFound() {
		printGroups();
		int count = facadenFactory.getGroupFacade().count();
		Group group = new Group();
		group.setName("");
		group.setDescription("");
		group.setId(404);
		facadenFactory.getGroupFacade().remove(group);
		assertThat(facadenFactory.getGroupFacade().count(), equalTo(count));
	}

	public void printGroups() {
		for (Group group : facadenFactory.getGroupFacade().findAll()) {
			logger.info(group.toString());
		}
	}
}

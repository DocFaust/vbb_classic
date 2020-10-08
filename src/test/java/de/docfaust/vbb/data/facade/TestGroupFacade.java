package de.docfaust.vbb.data.facade;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import de.docfaust.vbb.data.entity.Group;
import de.docfaust.vbb.data.facades.GroupFacade;
import de.docfaust.vbb.data.util.JpaBaseRolledBackTestCase;

public class TestGroupFacade extends JpaBaseRolledBackTestCase {

	@Test
	@DisplayName("Test Not Null")
	public void test() {
		assertThat(new GroupFacade()).isNotNull();
	}

	@Test
	@DisplayName("Test Find All")
	public void testFindAll() {
		assertThat(facadenFactory.getGroupFacade().findAll()).isNotEmpty();
		assertThat(facadenFactory.getGroupFacade().count()).isEqualTo(3);
	}

	@Test
	@DisplayName("Test Find By Name")
	public void testFindByName() {
		Group group = facadenFactory.getGroupFacade().findByName("ADMIN");
		assertThat(group).isNotNull();
		logger.info(group.toString());
		assertThat(group.getDescription()).isEqualTo("Administrator");
		assertThat(group.getName()).isEqualTo("ADMIN");
		assertThat(group.getId()).isEqualTo(1);
	}

	@Test
	@DisplayName("Test SaveGroup")
	public void testSaveGroup() {
		Group group = new Group();
		group.setName("READERS");
		group.setDescription("Benutzer mit Leseberechtigung");

		facadenFactory.getGroupFacade().create(group);

		assertThat(facadenFactory.getGroupFacade().count()).isEqualTo(4);
	}

	@Test
	@DisplayName("Test Remove Group")
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
		assertThat(facadenFactory.getGroupFacade().count()).isEqualTo(count + 1);
		facadenFactory.getGroupFacade().remove(group);
		printGroups();
		assertThat(facadenFactory.getGroupFacade().count()).isEqualTo(count);
	}

	@Test
	@DisplayName("Test Remove Group Not Found")
	public void testRemoveGroupNotFound() {
		printGroups();
		int count = facadenFactory.getGroupFacade().count();
		Group group = new Group();
		group.setName("");
		group.setDescription("");
		group.setId(404);
		facadenFactory.getGroupFacade().remove(group);
		assertThat(facadenFactory.getGroupFacade().count()).isEqualTo(count);
	}

	public void printGroups() {
		for (Group group : facadenFactory.getGroupFacade().findAll()) {
			logger.info(group.toString());
		}
	}
}

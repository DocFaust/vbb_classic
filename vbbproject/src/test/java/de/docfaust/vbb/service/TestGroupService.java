package de.docfaust.vbb.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.docfaust.vbb.ServiceCreator;
import de.docfaust.vbb.data.entity.Group;
import de.docfaust.vbb.data.util.JpaBaseRolledBackTestCase;

public class TestGroupService extends JpaBaseRolledBackTestCase {
	private GroupService groupService;

	@BeforeEach
	public void setUp() {
		groupService = new ServiceCreator(em).getGroupService();
	}

	@Test
	public void testGetGroups() {
		List<Group> groups = groupService.getGroups();

		assertThat(groups)
			.isNotNull()
			.hasSize(facadenFactory.getGroupFacade().count())
			.extracting("name", "description")
			.contains(
					tuple("ADMIN","Administrator"),
					tuple("READER","Leser"),
					tuple("USER","Benutzer")
					);
	}

}

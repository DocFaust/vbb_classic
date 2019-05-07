package de.docfaust.vbb.data.facade;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import de.docfaust.vbb.data.entity.Buchung;
import de.docfaust.vbb.data.facades.BuchungFacade;
import de.docfaust.vbb.data.util.JpaBaseRolledBackTestCase;

public class TestBuchungFacade extends JpaBaseRolledBackTestCase {
	@Test
	@DisplayName("Test Facade Not Null")
	public void test() {
		assertThat(new BuchungFacade()).isNotNull();
	}

	@Test
	@DisplayName("Test FindAll")
	public void testFindAll() {
		logTest(">> testFindAll");
		assertThat(facadenFactory).isNotNull();
		assertThat(facadenFactory.getBuchungFacade()).isNotNull();
		assertThat(facadenFactory.getBuchungFacade().findAll()).isNotEmpty();
		assertThat(facadenFactory.getBuchungFacade().count()).isEqualTo(31);
		logTest("<< testFindAll");
	}

	@Test
	@DisplayName("Test Entities Not Equal ")
	public void testNotEqual() {
		logTest(">> testNotEqual");
		Buchung buchung1 = facadenFactory.getBuchungFacade().find(1);
		Buchung buchung2 = facadenFactory.getBuchungFacade().find(2);
		assertThat(buchung1).isNotEqualTo(buchung2);
		logTest("<< testNotEqual");
	}

	@Test
	@DisplayName("Test Entities Equal ")
	public void testEqual() {
		logTest(">> testEqual");
		Buchung buchung1 = facadenFactory.getBuchungFacade().find(1);
		Buchung buchung2 = facadenFactory.getBuchungFacade().find(1);
		assertThat(buchung1).isEqualTo(buchung2);
		logTest("<< testEqual");
	}
}

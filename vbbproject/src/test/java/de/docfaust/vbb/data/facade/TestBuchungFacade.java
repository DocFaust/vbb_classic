package de.docfaust.vbb.data.facade;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import de.docfaust.vbb.data.entity.Buchung;
import de.docfaust.vbb.data.facades.BuchungFacade;
import de.docfaust.vbb.data.util.JpaBaseRolledBackTestCase;

public class TestBuchungFacade extends JpaBaseRolledBackTestCase {
	@Test
	public void test() {
		assertThat(new BuchungFacade(), not(nullValue()));
	}
	

	@Test
	public void testFindAll() {
		logTest(">> testFindAll");
		assertFalse(facadenFactory.getBuchungFacade().findAll().isEmpty());
		assertEquals(31, facadenFactory.getBuchungFacade().count());
		logTest("<< testFindAll");
	}

	@Test
	public void testNotEqual() {
		logTest(">> testNotEqual");
		Buchung buchung1 = facadenFactory.getBuchungFacade().find(1);
		Buchung buchung2 = facadenFactory.getBuchungFacade().find(2);
		assertThat(buchung1, not(equalTo(buchung2)));
		logTest("<< testNotEqual");
	}

	@Test
	public void testEqual() {
		logTest(">> testEqual");
		Buchung buchung1 = facadenFactory.getBuchungFacade().find(1);
		Buchung buchung2 = facadenFactory.getBuchungFacade().find(1);
		assertThat(buchung1, equalTo(buchung2));
		logTest("<< testEqual");
	}
}

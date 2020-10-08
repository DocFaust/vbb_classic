package de.docfaust.vbb.data.facade;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.jupiter.api.Test;

import de.docfaust.vbb.data.entity.Buchung;
import de.docfaust.vbb.data.entity.Spieler;
import de.docfaust.vbb.data.facades.BuchungFacade;
import de.docfaust.vbb.data.facades.SpielerFacade;
import de.docfaust.vbb.data.factories.SpielerFactory;
import de.docfaust.vbb.data.util.JpaBaseRolledBackTestCase;
import de.docfaust.vbb.util.EntityFactory;

public class TestSpielerFacade extends JpaBaseRolledBackTestCase {

	@Test
	public void test() {
		assertThat(new SpielerFacade(), not(nullValue()));
	}
	
	@Test
	public void testFindAll() {
		assertFalse(facadenFactory.getSpielerFacade().findAll().isEmpty());
	}

	@Test
	public void testFindByNameSuccessful() throws Exception {
		Spieler spieler = facadenFactory.getSpielerFacade().findByName("Alfred Altmann");
		assertNotNull(spieler);
	}

	@Test
	public void testFindByNameUnsuccessful() throws Exception {
		Spieler spieler = facadenFactory.getSpielerFacade().findByName("Willi wichtig");
		assertNull(spieler);
	}

	@Test
	public void testSaveSpieler() {
		int count = facadenFactory.getSpielerFacade().count();
		Spieler spieler = SpielerFactory.createSpieler("Xaver Xylophon", "xxylophon@gmiks.de");
		
		facadenFactory.getSpielerFacade().create(spieler);
		

		assertEquals(++count, facadenFactory.getSpielerFacade().count());
	}

	@Test
	public void testAddBuchung() {
		BuchungFacade buchungFacade = facadenFactory.getBuchungFacade();
		assertThat(buchungFacade.count(), equalTo(31));
		Spieler spieler = facadenFactory.getSpielerFacade().findByName("Alfred Altmann");
		Buchung buchung = EntityFactory.createBuchung(new Date(), BigDecimal.TEN);
		
		spieler.addBuchung(buchung);
		facadenFactory.getBuchungFacade().create(buchung);

		facadenFactory.getSpielerFacade().edit(spieler);

		assertThat(buchungFacade.count(), equalTo(32));
	}

	@Test
	public void testRemoveBuchung() {
		Spieler spieler = facadenFactory.getSpielerFacade().findByName("Alfred Altmann");
		int count = spieler.getBuchungen().size();
		Buchung buchung = facadenFactory.getBuchungFacade().find(1);
		spieler.removeBuchung(buchung);
		
		facadenFactory.getSpielerFacade().edit(spieler);
		

		assertEquals(--count, facadenFactory.getSpielerFacade().findByName("Alfred Altmann").getBuchungen().size());
	}
}

package de.docfaust.vbb.data.facade;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.junit.Test;

import de.docfaust.vbb.data.entity.Buchung;
import de.docfaust.vbb.data.entity.Season;
import de.docfaust.vbb.data.entity.Spiel;
import de.docfaust.vbb.data.facades.SpielFacade;
import de.docfaust.vbb.data.factories.SpielFactory;
import de.docfaust.vbb.data.util.JpaBaseRolledBackTestCase;
import de.docfaust.vbb.util.messages.MessageConstants;
import de.docfaust.vbb.util.statusliste.Statusliste;

public class TestSpielFacade extends JpaBaseRolledBackTestCase {

	@Test
	public void test() {
		assertThat(new SpielFacade(), not(nullValue()));
	}
	
	@Test
	public void testFindAll() {
		assertFalse(facadenFactory.getSpielFacade().findAll().isEmpty());
	}

	@Test
	public void testSaveSpiel() throws ParseException {
		Date datum = new SimpleDateFormat("dd.MM.yyyy").parse("01.01.2014");
		Season season = facadenFactory.getSeasonFacade().getSeasonFromDate(datum);
		Spiel spiel = SpielFactory.createSpiel(datum, season);
		
		facadenFactory.getSpielFacade().create(spiel);
		
		logger.debug("spiel " + spiel.getId());

		assertNotNull(facadenFactory.getSpielFacade().find(spiel.getId()));
	}

	@Test
	public void testDeleteSpiel() throws ParseException {
		Date datum = new SimpleDateFormat("dd.MM.yyyy").parse("01.01.2014");
		Season season = facadenFactory.getSeasonFacade().getSeasonFromDate(datum);
		Spiel spiel = SpielFactory.createSpiel(datum, season);
		
		facadenFactory.getSpielFacade().create(spiel);
		

		
		Statusliste statusliste = facadenFactory.getSpielFacade().deleteSpiel(spiel);
		

		assertTrue(statusliste.booleanValue());
		assertNull(facadenFactory.getSpielFacade().find(spiel.getId()));
	}

	@Test
	public void testDeleteSpielNotFound() throws ParseException {
		Date datum = new SimpleDateFormat("dd.MM.yyyy").parse("01.01.2028");
		Season season = facadenFactory.getSeasonFacade().getSeasonFromDate(datum);
		Spiel spiel = SpielFactory.createSpiel(datum, season);
		
		Statusliste statusliste = facadenFactory.getSpielFacade().deleteSpiel(spiel);
		
		assertFalse(statusliste.booleanValue());

		assertTrue(statusliste.hasStatus(MessageConstants.SPIEL_NOT_FOUND));
	}

	@Test
	public void testDeleteSpielMitBuchungen() throws ParseException {
		Date datum = new SimpleDateFormat("dd.MM.yyyy").parse("01.01.2014");
		Season season = facadenFactory.getSeasonFacade().getSeasonFromDate(datum);
		Spiel spiel = SpielFactory.createSpiel(datum, season);
		spiel.setBuchungen(new ArrayList<Buchung>());
		Buchung buchung = new Buchung();
		buchung.setDatum(datum);
		buchung.setDescription("Spieleinsatz");
		buchung.setSpieler(facadenFactory.getSpielerFacade().findByName("Claus Caspar"));
		buchung.setPrice(season.getPrice());

		facadenFactory.getBuchungFacade().create(buchung);
		spiel.addBuchung(buchung);
		facadenFactory.getSpielFacade().create(spiel);

		assertNotNull(facadenFactory.getBuchungFacade().find(buchung.getId()));
		
		Statusliste statusliste = facadenFactory.getSpielFacade().deleteSpiel(spiel);
		

		assertNull(facadenFactory.getSpielFacade().find(spiel.getId()));
		assertTrue(statusliste.booleanValue());

		assertNull(facadenFactory.getBuchungFacade().find(buchung.getId()));
	}
}

package de.docfaust.vbb.data.facade;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;

import org.junit.Test;

import de.docfaust.vbb.data.entity.Buchung;
import de.docfaust.vbb.data.entity.Season;
import de.docfaust.vbb.data.entity.Spiel;
import de.docfaust.vbb.data.facades.SeasonFacade;
import de.docfaust.vbb.data.util.JpaBaseRolledBackTestCase;
import de.docfaust.vbb.util.messages.MessageConstants;
import de.docfaust.vbb.util.statusliste.Statusliste;

public class TestSeasonFacade extends JpaBaseRolledBackTestCase {

	@Test
	public void test() {
		assertThat(new SeasonFacade(), not(nullValue()));
	}
	
	@Test
	public void testFindAll() {
		assertFalse(facadenFactory.getSeasonFacade().findAll().isEmpty());
	}

	@Test
	public void testhasCollisionsAnfangDazwischen() throws ParseException {
		Season season = new Season();
		season.setStartdate(new SimpleDateFormat("dd.MM.yyyy").parse("02.01.2014"));
		season.setEnddate(new SimpleDateFormat("dd.MM.yyyy").parse("04.01.2014"));
		assertTrue(facadenFactory.getSeasonFacade().hasCollisions(season));
	}

	@Test
	public void testhasCollisionsEndeDazwischen() throws ParseException {
		Season season = new Season();
		season.setStartdate(new SimpleDateFormat("dd.MM.yyyy").parse("03.01.2014"));
		season.setEnddate(new SimpleDateFormat("dd.MM.yyyy").parse("05.01.2014"));
		assertTrue(facadenFactory.getSeasonFacade().hasCollisions(season));
	}

	@Test
	public void testhasCollisionsAnfangUndEndeDazwischen() throws ParseException {
		Season season = new Season();
		season.setStartdate(new SimpleDateFormat("dd.MM.yyyy").parse("02.01.2014"));
		season.setEnddate(new SimpleDateFormat("dd.MM.yyyy").parse("05.01.2014"));
		assertTrue(facadenFactory.getSeasonFacade().hasCollisions(season));
	}

	@Test
	public void testhasCollisionsKeineKollision() throws ParseException {
		Season season = new Season();
		season.setStartdate(new SimpleDateFormat("dd.MM.yyyy").parse("03.01.2014"));
		season.setEnddate(new SimpleDateFormat("dd.MM.yyyy").parse("04.01.2014"));
		assertFalse(facadenFactory.getSeasonFacade().hasCollisions(season));
	}

	@Test
	public void testhasCollisionsTotalDrauf() throws ParseException {
		Season season = new Season();
		season.setStartdate(new SimpleDateFormat("dd.MM.yyyy").parse("01.01.2014"));
		season.setEnddate(new SimpleDateFormat("dd.MM.yyyy").parse("02.01.2014"));
		assertTrue(facadenFactory.getSeasonFacade().hasCollisions(season));
	}

	@Test
	public void removeSeasonwithReferences() {
		traceDatabaseContent();
		Season season = new Season();
		season.setId(1);
		Season tmpBefore = facadenFactory.getSeasonFacade().find(season.getId());
		assertNotNull(tmpBefore);
		
		Statusliste statusliste = facadenFactory.getSeasonFacade().deleteSeason(season);
		
		assertFalse(statusliste.booleanValue());
		assertTrue(statusliste.hasStatus(MessageConstants.SEASON_HAS_REFERENCES));
		traceDatabaseContent();
		Season tmpAfter = facadenFactory.getSeasonFacade().find(season.getId());
		assertNotNull(tmpAfter);
	}

	@Test
	public void removeSeasonwithoutReferences() throws ParseException {
		traceDatabaseContent();
		Season season = new Season();
		season.setDescription("Zum Löschen geboren");
		season.setStartdate(new SimpleDateFormat("dd.MM.yyyy").parse("01.02.2014"));
		season.setEnddate(new SimpleDateFormat("dd.MM.yyyy").parse("02.02.2014"));
		Set<ConstraintViolation<Season>> validate = Validation.buildDefaultValidatorFactory().getValidator().validate(season);
		assertTrue(validate.isEmpty());
		
		facadenFactory.getSeasonFacade().create(season);
		
		logger.info("Saison hat folgende ID: " + season.getId());

		Season tmpBefore = facadenFactory.getSeasonFacade().find(season.getId());
		assertNotNull(tmpBefore);
		
		Statusliste statusliste = facadenFactory.getSeasonFacade().deleteSeason(season);
		
		assertTrue(statusliste.booleanValue());
		traceDatabaseContent();
		Season tmpAfter = facadenFactory.getSeasonFacade().find(season.getId());
		assertNull(tmpAfter);
	}

	@Test
	public void removeUnknownSeason() throws ParseException {
		traceDatabaseContent();
		Season season = new Season();
		season.setDescription("Zum Löschen geboren");
		season.setId(5);
		Season tmpBefore = facadenFactory.getSeasonFacade().find(season.getId());
		assertNull(tmpBefore);
		
		Statusliste statusliste = facadenFactory.getSeasonFacade().deleteSeason(season);
		
		assertFalse(statusliste.booleanValue());
		assertTrue(statusliste.hasStatus(MessageConstants.SEASON_NOT_FOUND));
		traceDatabaseContent();
		Season tmpAfter = facadenFactory.getSeasonFacade().find(season.getId());
		assertNull(tmpAfter);
	}

	@Test
	public void testGetSeasonFromDateSuccessful() throws ParseException {
		Date datum = new SimpleDateFormat("dd.MM.yyyy").parse("01.01.2014");
		Season season = facadenFactory.getSeasonFacade().getSeasonFromDate(datum);
		assertNotNull(season);

		Date startDate = season.getStartdate();
		Date endDate = season.getEnddate();

		assertTrue(isBetween(datum, startDate, endDate));
	}

	@Test
	public void testGetSeasonFromDateUnSuccessful() throws ParseException {
		Date datum = new SimpleDateFormat("dd.MM.yyyy").parse("03.01.2014");
		Season season = facadenFactory.getSeasonFacade().getSeasonFromDate(datum);
		assertNull(season);

	}

	@Test
	public void testRemoveSpiel() {
		logger.info("Spiele vorher:");
		for (Spiel spiel2 : facadenFactory.getSpielFacade().findAll()) {
			logger.info(spiel2.toString());
		}

		logger.info("Seasons vorher:");
		for (Season season2 : facadenFactory.getSeasonFacade().findAll()) {
			logger.info(season2.toString());
		}

		logger.info("Spiele zu Saison:");
		Season season = facadenFactory.getSeasonFacade().find(1);
		for (Spiel spiel3 : season.getSpiele()) {
			logger.info(spiel3.toString());
		}
		Spiel spiel = season.getSpiele().get(0);
		assertEquals(2, facadenFactory.getSeasonFacade().find(1).getSpiele().size());
		season.removeSpiel(spiel);
		
		for (Buchung buchung : spiel.getBuchungen()) {
			buchung.setSpiel(null);
			facadenFactory.getBuchungFacade().remove(buchung);
		}
		facadenFactory.getSpielFacade().remove(spiel);
		facadenFactory.getSeasonFacade().edit(season);
		
		assertEquals(1, facadenFactory.getSeasonFacade().find(1).getSpiele().size());
		logger.info("Spiele nachher:");
		for (Spiel spiel2 : facadenFactory.getSpielFacade().findAll()) {
			logger.info(spiel2.toString());
		}

		logger.info("Seasons nachher:");
		for (Season season2 : facadenFactory.getSeasonFacade().findAll()) {
			logger.info(season2.toString());
		}

	}

	private boolean isBetween(final Date datum, final Date start, final Date end) {
		boolean isBetween = false;

		long lDatum = datum.getTime();
		long lStartDate = start.getTime();
		Calendar cal = Calendar.getInstance();
		cal.setTime(end);

		cal.add(Calendar.HOUR_OF_DAY, 23);
		cal.add(Calendar.MINUTE, 59);
		cal.add(Calendar.SECOND, 59);
		cal.add(Calendar.MILLISECOND, 999);

		long lEndDate = cal.getTimeInMillis();
		isBetween = lStartDate <= lDatum && lEndDate >= lDatum;

		logger.debug(String.format("Datum %1$td.%1$tm.%1$tY ist %2$szwischen %3$td.%3$tm.%3$tY und %4$td.%4$tm.%4$tY.", datum, (isBetween ? "" : "nicht "),
				start, end));
		return isBetween;
	}

	public void traceDatabaseContent() {
		logger.info("Tracing seasons");
		List<Season> seasonList = facadenFactory.getSeasonFacade().findAll();
		logger.info("Size: " + seasonList.size());
		for (Season season : seasonList) {
			logger.info(season.toString());
		}
	}
}

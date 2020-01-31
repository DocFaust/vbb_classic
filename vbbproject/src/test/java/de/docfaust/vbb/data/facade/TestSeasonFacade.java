package de.docfaust.vbb.data.facade;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;

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
		assertThat(new SeasonFacade()).isNotNull();
	}
	
	@Test
	public void testFindAll() {
		assertThat(facadenFactory.getSeasonFacade().findAll()).isNotEmpty();
	}

	@Test
	public void testhasCollisionsAnfangDazwischen() throws ParseException {
		Season season = new Season();
		season.setStartdate(new SimpleDateFormat("dd.MM.yyyy").parse("02.01.2014"));
		season.setEnddate(new SimpleDateFormat("dd.MM.yyyy").parse("04.01.2014"));
		assertThat(facadenFactory.getSeasonFacade().hasCollisions(season)).isTrue();
	}

	@Test
	public void testhasCollisionsEndeDazwischen() throws ParseException {
		Season season = new Season();
		season.setStartdate(new SimpleDateFormat("dd.MM.yyyy").parse("03.01.2014"));
		season.setEnddate(new SimpleDateFormat("dd.MM.yyyy").parse("05.01.2014"));
		assertThat(facadenFactory.getSeasonFacade().hasCollisions(season)).isTrue();
	}

	@Test
	public void testhasCollisionsAnfangUndEndeDazwischen() throws ParseException {
		Season season = new Season();
		season.setStartdate(new SimpleDateFormat("dd.MM.yyyy").parse("02.01.2014"));
		season.setEnddate(new SimpleDateFormat("dd.MM.yyyy").parse("05.01.2014"));
		assertThat(facadenFactory.getSeasonFacade().hasCollisions(season)).isTrue();
	}

	@Test
	public void testhasCollisionsKeineKollision() throws ParseException {
		Season season = new Season();
		season.setStartdate(new SimpleDateFormat("dd.MM.yyyy").parse("03.01.2014"));
		season.setEnddate(new SimpleDateFormat("dd.MM.yyyy").parse("04.01.2014"));
		assertThat(facadenFactory.getSeasonFacade().hasCollisions(season)).isFalse();
	}

	@Test
	public void testhasCollisionsTotalDrauf() throws ParseException {
		Season season = new Season();
		season.setStartdate(new SimpleDateFormat("dd.MM.yyyy").parse("01.01.2014"));
		season.setEnddate(new SimpleDateFormat("dd.MM.yyyy").parse("02.01.2014"));
		assertThat(facadenFactory.getSeasonFacade().hasCollisions(season)).isTrue();
	}

	@Test
	public void removeSeasonwithReferences() {
		traceDatabaseContent();
		Season season = new Season();
		season.setId(1);
		Season tmpBefore = facadenFactory.getSeasonFacade().find(season.getId());
		assertThat(tmpBefore).isNotNull();
		
		Statusliste statusliste = facadenFactory.getSeasonFacade().deleteSeason(season);
		
		assertThat(statusliste.booleanValue()).isFalse();
		assertThat(statusliste.hasStatus(MessageConstants.SEASON_HAS_REFERENCES)).isTrue();
		traceDatabaseContent();
		Season tmpAfter = facadenFactory.getSeasonFacade().find(season.getId());
		assertThat(tmpAfter).isNotNull();
	}

	@Test
	public void removeSeasonwithoutReferences() throws ParseException {
		traceDatabaseContent();
		Season season = new Season();
		season.setDescription("Zum Löschen geboren");
		season.setStartdate(new SimpleDateFormat("dd.MM.yyyy").parse("01.02.2014"));
		season.setEnddate(new SimpleDateFormat("dd.MM.yyyy").parse("02.02.2014"));
		Set<ConstraintViolation<Season>> validate = Validation.buildDefaultValidatorFactory().getValidator().validate(season);
		assertThat(validate).isEmpty();
		
		facadenFactory.getSeasonFacade().create(season);
		
		logger.info("Saison hat folgende ID: " + season.getId());

		Season tmpBefore = facadenFactory.getSeasonFacade().find(season.getId());
		assertThat(tmpBefore).isNotNull();
		
		Statusliste statusliste = facadenFactory.getSeasonFacade().deleteSeason(season);
		
		assertThat(statusliste.booleanValue()).isTrue();
		traceDatabaseContent();
		Season tmpAfter = facadenFactory.getSeasonFacade().find(season.getId());
		assertThat(tmpAfter).isNull();
	}

	@Test
	public void removeUnknownSeason() throws ParseException {
		traceDatabaseContent();
		Season season = new Season();
		season.setDescription("Zum Löschen geboren");
		season.setId(5);
		Season tmpBefore = facadenFactory.getSeasonFacade().find(season.getId());
		assertThat(tmpBefore).isNull();
		
		Statusliste statusliste = facadenFactory.getSeasonFacade().deleteSeason(season);
		
		assertThat(statusliste.booleanValue()).isFalse();
		assertThat(statusliste.hasStatus(MessageConstants.SEASON_NOT_FOUND)).isTrue();
		traceDatabaseContent();
		Season tmpAfter = facadenFactory.getSeasonFacade().find(season.getId());
		assertThat(tmpAfter).isNull();;
	}

	@Test
	public void testGetSeasonFromDateSuccessful() throws ParseException {
		Date datum = new SimpleDateFormat("dd.MM.yyyy").parse("01.01.2014");
		Season season = facadenFactory.getSeasonFacade().getSeasonFromDate(datum);
		assertThat(season).isNotNull();

		Date startDate = season.getStartdate();
		Date endDate = season.getEnddate();

		assertThat(isBetween(datum, startDate, endDate)).isTrue();
	}

	@Test
	public void testGetSeasonFromDateUnSuccessful() throws ParseException {
		Date datum = new SimpleDateFormat("dd.MM.yyyy").parse("03.01.2014");
		Season season = facadenFactory.getSeasonFacade().getSeasonFromDate(datum);
		assertThat(season).isNull();

	}

	@Test
	public void testRemoveSpiel() {
		logger.info("Spiele vorher:");
		facadenFactory.getSpielFacade().findAll().stream().forEach(obj -> logger.info(obj.toString()));

		logger.info("Seasons vorher:");
		facadenFactory.getSeasonFacade().findAll().stream().forEach(obj -> logger.info(obj.toString()));
		
		assertThat(facadenFactory.getSpielFacade().count()).isEqualTo(4);
		assertThat(facadenFactory.getSeasonFacade().count()).isEqualTo(3);
		
		
		logger.info("Spiele zu Saison:");
		Season season = facadenFactory.getSeasonFacade().find(1);
		season.getSpiele().stream().forEach(obj -> logger.info(obj.toString()));
		for (Spiel spiel3 : season.getSpiele()) {
			logger.info(spiel3.toString());
		}
		Spiel spiel = season.getSpiele().get(0);
		
		
		assertThat(facadenFactory.getSeasonFacade().find(1).getSpiele()).hasSize(2);
		season.removeSpiel(spiel);
		
//		for (Buchung buchung : spiel.getBuchungen()) {
//			buchung.setSpiel(null);
//			facadenFactory.getBuchungFacade().remove(buchung);
//		}
		facadenFactory.getSpielFacade().remove(spiel);
		facadenFactory.getSeasonFacade().edit(season);
		
		assertThat(facadenFactory.getSeasonFacade().find(1).getSpiele()).hasSize(1);
		logger.info("Spiele nachher:");
		facadenFactory.getSpielFacade().findAll().stream().forEach(obj -> logger.info(obj.toString()));

		logger.info("Seasons nachher:");
		facadenFactory.getSeasonFacade().findAll().stream().forEach(obj -> logger.info(obj.toString()));
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

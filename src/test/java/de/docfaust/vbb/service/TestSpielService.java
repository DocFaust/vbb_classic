package de.docfaust.vbb.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.docfaust.vbb.ServiceCreator;
import de.docfaust.vbb.data.entity.Season;
import de.docfaust.vbb.data.entity.Spiel;
import de.docfaust.vbb.data.entity.Spieler;
import de.docfaust.vbb.data.util.JpaBaseRolledBackTestCase;
import de.docfaust.vbb.util.EntityFactory;
import de.docfaust.vbb.util.messages.MessageConstants;
import de.docfaust.vbb.util.statusliste.Statusliste;

class TestSpielService extends JpaBaseRolledBackTestCase {

	private SpielService spielservice;
	
	@Test
	void testSaveSpielListOfSpielerDate() {
		Date date = new Date();
		Season season = EntityFactory.createSeason(EntityFactory.getActualDateAddDays(0), EntityFactory.getActualDateAddDays(2), BigDecimal.TEN,
				"Aktuelle Saison");

		facadenFactory.getSeasonFacade().create(season);
		
		List<Spieler> spielerList = new ArrayList<Spieler>();
		Spieler spieler1 = facadenFactory.getSpielerFacade().findByName("Alfred Altmann");
		spieler1.setAnwesend(true);
		spieler1.setBezahlt(true);
		spielerList.add(spieler1);

		Spieler spieler2 = facadenFactory.getSpielerFacade().findByName("Claus Caspar");
		spieler2.setAnwesend(true);
		spieler2.setBezahlt(false);
		spielerList.add(spieler2);
		Spieler spieler3 = facadenFactory.getSpielerFacade().findByName("Bernd Brot");
		spieler3.setAnwesend(false);
		spieler3.setBezahlt(false);
		spielerList.add(spieler3);


		spielservice.saveSpiel(spielerList, date);

		assertThat(facadenFactory.getSpielFacade().count()).isEqualTo(5);
		printDatabaseContent();
		assertThat(facadenFactory.getBuchungFacade().count()).isEqualTo(34);
	}
	@Test
	void testSaveSpielSpielExists() {
		Date from = Date.from(LocalDate.of(2014, 1, 5).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
		Season season = EntityFactory.createSeason(EntityFactory.getActualDateAddDays(0), EntityFactory.getActualDateAddDays(2), BigDecimal.TEN,
				"Aktuelle Saison");

		facadenFactory.getSeasonFacade().create(season);
		
		List<Spieler> spielerList = new ArrayList<Spieler>();
		Spieler spieler1 = facadenFactory.getSpielerFacade().findByName("Alfred Altmann");
		spieler1.setAnwesend(true);
		spieler1.setBezahlt(true);
		spielerList.add(spieler1);

		Spieler spieler2 = facadenFactory.getSpielerFacade().findByName("Claus Caspar");
		spieler2.setAnwesend(true);
		spieler2.setBezahlt(false);
		spielerList.add(spieler2);

		Statusliste statusliste = spielservice.saveSpiel(spielerList, from);
		
		assertThat(statusliste.booleanValue()).isFalse();
		statusliste.hasStatus(MessageConstants.GAME_ALREADYEXISTING);
		
		
		assertThat(facadenFactory.getSpielFacade().count()).isEqualTo(4);
		printDatabaseContent();
	}

	@Test
	void testSaveSpielNoSeason() {
		Date from = Date.from(LocalDate.of(2000, 1, 5).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
		
		List<Spieler> spielerList = new ArrayList<Spieler>();
		Spieler spieler1 = facadenFactory.getSpielerFacade().findByName("Alfred Altmann");
		spieler1.setAnwesend(true);
		spieler1.setBezahlt(true);
		spielerList.add(spieler1);

		Spieler spieler2 = facadenFactory.getSpielerFacade().findByName("Claus Caspar");
		spieler2.setAnwesend(true);
		spieler2.setBezahlt(false);
		spielerList.add(spieler2);


		Statusliste statusliste = spielservice.saveSpiel(spielerList, from);
		statusliste.hasStatus(MessageConstants.SEASON_NOT_FOUND);
		assertThat(statusliste.booleanValue()).isFalse();

		
		
		assertThat(facadenFactory.getSpielFacade().count()).isEqualTo(4);
		printDatabaseContent();
	}
	@Test
	void testSaveSpielSpiel() {
		assertThat(facadenFactory.getSpielFacade().count()).isEqualTo(4);
		Spiel spiel = EntityFactory.createSpiel(new Date());
		spiel.setSeason(facadenFactory.getSeasonFacade().find(1));
		spielservice.saveSpiel(spiel);
		assertThat(facadenFactory.getSpielFacade().count()).isEqualTo(5);
	}

	@Test
	void testDeleteSpiel() {
		int count = facadenFactory.getSpielFacade().count();
		Spiel spiel = facadenFactory.getSpielFacade().find(1);
		spielservice.deleteSpiel(spiel);
		assertThat(facadenFactory.getSpielFacade().count()).isEqualTo(--count);
		
	}

	@Test
	void testGetSpiele() {
		assertThat(spielservice.getSpiele()).hasSize(facadenFactory.getSpielFacade().count());
	}

	@Test
	void testRemoveOldSpiele() {
		/*2014-01-05*/
		Date from = Date.from(LocalDate.of(2014, 1, 5).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
		spielservice.removeOldSpiele(from);
		assertThat(facadenFactory.getSpielFacade().count()).isEqualTo(1);
		
	}
	
	@BeforeEach
	public  void initService() {
		ServiceCreator sc = new ServiceCreator(em);
		this.spielservice = sc.getSpielService();
	}
}

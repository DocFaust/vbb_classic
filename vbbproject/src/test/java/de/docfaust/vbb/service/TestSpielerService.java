package de.docfaust.vbb.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;

import de.docfaust.vbb.ServiceCreator;
import de.docfaust.vbb.data.entity.Spieler;
import de.docfaust.vbb.data.util.JpaBaseRolledBackTestCase;
import de.docfaust.vbb.util.SpielerBuilder;
import de.docfaust.vbb.util.statusliste.Statusliste;

class TestSpielerService extends JpaBaseRolledBackTestCase {

	@Test 
	void testInit() {
		assertThat(new SpielerServiceImpl()).isNotNull();
	}
	
	@Test
	void testGetSpielerNames() {
		ServiceCreator sc = new ServiceCreator(em);
		SpielerService spielerService = sc.getSpielerService();
		List<String> spielerNames = spielerService.getSpielerNames();
		assertThat(spielerNames).hasSize(facadenFactory.getSpielerFacade().count()).contains("Alfred Altmann", "Bernd Brot", "Claus Caspar", "Doreen Durstig", "Erich Ehrlich", "Franz Fröhlich", "Gretchen Gröhl", "Hans Hohlbirne", "Johann Jochbein", "Ines Ignorant");
	}
	
	@Test
	void testGetSpieler() {
		ServiceCreator sc = new ServiceCreator(em);
		SpielerService spielerService = sc.getSpielerService();
		List<Spieler> spieler = spielerService.getSpieler();
		assertThat(spieler).hasSize(facadenFactory.getSpielerFacade().count());
	}
	
	@Test
	void testDeleteSpielerWithBuchungen() {
		ServiceCreator sc = new ServiceCreator(em);
		SpielerService spielerService = sc.getSpielerService();
		int initialcount = facadenFactory.getSpielerFacade().count();
		assertThat(initialcount).isNotEqualTo(0);
		Spieler spieler = facadenFactory.getSpielerFacade().find(1);
		assertThat(spieler).isNotNull();
		Statusliste statusliste = spielerService.deleteSpieler(spieler);
		assertThat(statusliste.booleanValue()).isFalse();
		assertThat(facadenFactory.getSpielerFacade().count()).isEqualTo(initialcount);
	}
	
	@Test
	void testDeleteSpielerWithoutBuchungen() {
		ServiceCreator sc = new ServiceCreator(em);
		SpielerService spielerService = sc.getSpielerService();
		int initialcount = facadenFactory.getSpielerFacade().count();
		assertThat(initialcount).isNotEqualTo(0);
		Spieler spieler = facadenFactory.getSpielerFacade().find(1);
		spieler.getBuchungen().stream().forEach(buchung-> facadenFactory.getBuchungFacade().remove(buchung));
		spieler.getBuchungen().clear();
		assertThat(spieler).isNotNull();
		Statusliste statusliste = spielerService.deleteSpieler(spieler);
		assertThat(statusliste.booleanValue()).isTrue();
		assertThat(facadenFactory.getSpielerFacade().count()).isEqualTo(--initialcount);
	}

	@Test
	void testSaveSpieler() {
		ServiceCreator sc = new ServiceCreator(em);
		SpielerService spielerService = sc.getSpielerService();
		int initialcount = facadenFactory.getSpielerFacade().count();
		assertThat(initialcount).isNotEqualTo(0);
		
		Spieler spieler = SpielerBuilder.create()
				.name("Karl Kloßbrühe")
				.email("k@k.k")
				.anwesend(false)
				.bezahlt(false)
				.build();
		spielerService.saveSpieler(spieler);
		assertThat(facadenFactory.getSpielerFacade().count()).isEqualTo(++initialcount);
	}
	
	@Test
	void testEditSpieler() {
		ServiceCreator sc = new ServiceCreator(em);
		SpielerService spielerService = sc.getSpielerService();
		int initialcount = facadenFactory.getSpielerFacade().count();
		assertThat(initialcount).isNotEqualTo(0);
		Spieler spieler = facadenFactory.getSpielerFacade().find(1);
		String name = "Alina Arschkrampe";
		String email = "a@a.a2";
		spieler.setName(name);
		spieler.setEmail(email);
		spielerService.saveSpieler(spieler);
		assertThat(facadenFactory.getSpielerFacade().count()).isEqualTo(initialcount);
		Spieler spieler2 = facadenFactory.getSpielerFacade().find(1);
		assertThat(spieler2).isNotNull().extracting("name", "email").contains(name, email);
	}
	
	@Test
	void testIncrementActivityLevel() {
		ServiceCreator sc = new ServiceCreator(em);
		SpielerService spielerService = sc.getSpielerService();
		int initialcount = facadenFactory.getSpielerFacade().count();
		assertThat(initialcount).isNotEqualTo(0);
		Spieler spieler = facadenFactory.getSpielerFacade().find(1);
		int inititialActivityLevel = spieler.getActivityLevel();
		
		spielerService.incrementActivityLevel(spieler);
		Spieler spieler2 = facadenFactory.getSpielerFacade().find(1);
		assertThat(spieler2).isNotNull();
		assertThat(spieler2.getActivityLevel()).isEqualTo(++inititialActivityLevel);
	}
	
}

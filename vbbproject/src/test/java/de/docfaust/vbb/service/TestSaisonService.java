package de.docfaust.vbb.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.docfaust.vbb.ServiceCreator;
import de.docfaust.vbb.data.entity.Season;
import de.docfaust.vbb.data.util.JpaBaseRolledBackTestCase;
import de.docfaust.vbb.util.EntityFactory;
import de.docfaust.vbb.util.statusliste.Statusliste;

public class TestSaisonService extends JpaBaseRolledBackTestCase {
	private SeasonService seasonService;

	@BeforeEach
	public void setUp() {
		seasonService = new ServiceCreator(em).getSeasonService();
	}

	@Test
	public void testInit() {
		assertThat(new SeasonServiceImpl()).isNotNull();

	}
	@Test
	public void testGetSeasons() {
		int count = facadenFactory.getSeasonFacade().count();
		assertThat(seasonService.getSeasons()).isNotNull().isNotEmpty().hasSize(count);
	}
	
	@Test
	public void testDeleteSaison() {
		int count = facadenFactory.getSeasonFacade().count();
		Season season = facadenFactory.getSeasonFacade().find(3);
		Statusliste statusliste = seasonService.deleteSaison(season);
		assertThat(statusliste.booleanValue()).isTrue();
		assertThat(facadenFactory.getSeasonFacade().count()).isEqualTo(count - 1);
	}
	@Test
	void testGetSeason() {
		Date from = Date.from(LocalDate.of(2015, 1, 8).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());

		Season season = seasonService.getSeason(from);
		
		assertThat(season).isNotNull().extracting("description").contains("Saison 3");
	}
	
//	Macht eigentlich nur in Kombination Sinn
//	@Test
//	void testRemoveOldSeasons() {
//		BuchungFacade bf = facadenFactory.getBuchungFacade();
//		SpielFacade sf = facadenFactory.getSpielFacade();
//		SeasonFacade sef = facadenFactory.getSeasonFacade();
//		bf.findAll().forEach(b -> bf.remove(b));
//		sf.findAll().forEach(s -> sf.remove(s));
//		
//		seasonService.removeOldSeasons(new Date());
//		assertThat(sef.count()).isEqualTo(0);
//	}
	
	@Test
	void testSaveSeason() {
		int count = facadenFactory.getSeasonFacade().count();
		
		Season season = EntityFactory.createSeason(new Date(), new Date(), BigDecimal.ONE, "Hurra");
		seasonService.saveSeason(season);
		assertThat(facadenFactory.getSeasonFacade().count()).isEqualTo(++count);
	}
}

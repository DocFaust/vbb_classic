package de.docfaust.vbb.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.docfaust.vbb.ServiceCreator;
import de.docfaust.vbb.data.entity.Season;
import de.docfaust.vbb.data.util.JpaBaseRolledBackTestCase;
import de.docfaust.vbb.util.statusliste.Statusliste;

public class TestSaisonService extends JpaBaseRolledBackTestCase {
	private SeasonService seasonService;

	@BeforeEach
	public void setUp() {
		ServiceCreator sc = new ServiceCreator(em);
		seasonService = sc.getSeasonService();
	}

	@Test
	public void testDeleteSaison() {
		int count = facadenFactory.getSeasonFacade().count();
		Season season = facadenFactory.getSeasonFacade().find(3);
		Statusliste statusliste = seasonService.deleteSaison(season);
		assertThat(statusliste.booleanValue()).isTrue();
		assertThat(facadenFactory.getSeasonFacade().count()).isEqualTo(count - 1);
	}

}

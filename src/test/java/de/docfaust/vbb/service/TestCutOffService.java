package de.docfaust.vbb.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.docfaust.vbb.ServiceCreator;
import de.docfaust.vbb.data.entity.Buchung;
import de.docfaust.vbb.data.util.JpaBaseRolledBackTestCase;

public class TestCutOffService extends JpaBaseRolledBackTestCase {
	private static final String HANS_HOHLBIRNE = "Hans Hohlbirne";
	private static final String GRETCHEN_GROEHL = "Gretchen Gröhl";
	private static final String FRANZ_FROEHLICH = "Franz Fröhlich";
	private static final String ERICH_EHRLICH = "Erich Ehrlich";
	private static final String DOREEN_DURSTIG = "Doreen Durstig";
	private static final String CLAUS_CASPAR = "Claus Caspar";
	private static final String BERND_BROT = "Bernd Brot";
	private static final String ALFRED_ALTMANN = "Alfred Altmann";
	private static final String INES_IGNORANT = "Ines Ignorant";
	private static final String JOHANN_JOCHBEIN = "Johann Jochbein";

	private CutOffService cutOffService;

	@BeforeEach
	void init() {
		cutOffService = new ServiceCreator(em).getCutOffService();
	}

	@Test
	public void testStartBuchungssschnitt() {

		cutOffService.starteBuchungsschnitt(new Date());

		printDatabaseContent();

		assertThat(facadenFactory.getBuchungFacade().count()).isEqualTo(10);
		List<Buchung> allBuchungen = facadenFactory.getBuchungFacade().findAll();
		assertThat(allBuchungen).hasSize(10);

		for (Buchung buchung : allBuchungen) {
			if (ALFRED_ALTMANN.equals(buchung.getSpieler().getName())) {
				assertThat(buchung.getPrice().intValue()).isEqualTo(-7);
			}
			if (BERND_BROT.equals(buchung.getSpieler().getName())) {
				assertThat(buchung.getPrice().intValue()).isEqualTo(-7);
			}
			if (CLAUS_CASPAR.equals(buchung.getSpieler().getName())) {
				assertThat(buchung.getPrice().floatValue()).isEqualTo(1.5F);
			}
			if (DOREEN_DURSTIG.equals(buchung.getSpieler().getName())) {
				assertThat(buchung.getPrice().intValue()).isEqualTo(-4);
			}
			if (ERICH_EHRLICH.equals(buchung.getSpieler().getName())) {
				assertThat(buchung.getPrice().intValue()).isEqualTo(8);
			}
			if (FRANZ_FROEHLICH.equals(buchung.getSpieler().getName())) {
				assertThat(buchung.getPrice().intValue()).isEqualTo(-2);
			}
			if (GRETCHEN_GROEHL.equals(buchung.getSpieler().getName())) {
				assertThat(buchung.getPrice().intValue()).isEqualTo(-2);
			}
			if (HANS_HOHLBIRNE.equals(buchung.getSpieler().getName())) {
				assertThat(buchung.getPrice().intValue()).isEqualTo(-2);
			}
			if (INES_IGNORANT.equals(buchung.getSpieler().getName())) {
				assertThat(buchung.getPrice().intValue()).isEqualTo(7);
			}
			if (JOHANN_JOCHBEIN.equals(buchung.getSpieler().getName())) {
				assertThat(buchung.getPrice().intValue()).isEqualTo(9);
			}
		}

		assertThat(facadenFactory.getSeasonFacade().count()).isEqualTo(1);

		assertThat(facadenFactory.getSpielFacade().count()).isEqualTo(1);
	}

}

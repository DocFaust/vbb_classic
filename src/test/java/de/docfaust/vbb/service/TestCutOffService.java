package de.docfaust.vbb.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.docfaust.vbb.ServiceCreator;
import de.docfaust.vbb.data.entity.Buchung;
import de.docfaust.vbb.data.util.JpaBaseRolledBackTestCase;

public class TestCutOffService extends JpaBaseRolledBackTestCase {
	private static final String HELENE_HERBST = "Helene Herbst";
	private static final String GUSTAV_GRUEN = "Gustav Gruen";
	private static final String FRIEDA_FALK = "Frieda Falk";
	private static final String EMIL_ENGEL = "Emil Engel";
	private static final String DANA_DORN = "Dana Dorn";
	private static final String CHRIS_CORDT = "Chris Cordt";
	private static final String BEA_BEISPIEL = "Bea Beispiel";
	private static final String ALEX_ANDER = "Alex Ander";
	private static final String IDA_IMMER = "Ida Immer";
	private static final String JONAS_JOREK = "Jonas Jorek";

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
			if (ALEX_ANDER.equals(buchung.getSpieler().getName())) {
				assertThat(buchung.getPrice().intValue()).isEqualTo(-7);
			}
			if (BEA_BEISPIEL.equals(buchung.getSpieler().getName())) {
				assertThat(buchung.getPrice().intValue()).isEqualTo(-7);
			}
			if (CHRIS_CORDT.equals(buchung.getSpieler().getName())) {
				assertThat(buchung.getPrice().floatValue()).isEqualTo(1.5F);
			}
			if (DANA_DORN.equals(buchung.getSpieler().getName())) {
				assertThat(buchung.getPrice().intValue()).isEqualTo(-4);
			}
			if (EMIL_ENGEL.equals(buchung.getSpieler().getName())) {
				assertThat(buchung.getPrice().intValue()).isEqualTo(8);
			}
			if (FRIEDA_FALK.equals(buchung.getSpieler().getName())) {
				assertThat(buchung.getPrice().intValue()).isEqualTo(-2);
			}
			if (GUSTAV_GRUEN.equals(buchung.getSpieler().getName())) {
				assertThat(buchung.getPrice().intValue()).isEqualTo(-2);
			}
			if (HELENE_HERBST.equals(buchung.getSpieler().getName())) {
				assertThat(buchung.getPrice().intValue()).isEqualTo(-2);
			}
			if (IDA_IMMER.equals(buchung.getSpieler().getName())) {
				assertThat(buchung.getPrice().intValue()).isEqualTo(7);
			}
			if (JONAS_JOREK.equals(buchung.getSpieler().getName())) {
				assertThat(buchung.getPrice().intValue()).isEqualTo(9);
			}
		}

		assertThat(facadenFactory.getSeasonFacade().count()).isEqualTo(1);

		assertThat(facadenFactory.getSpielFacade().count()).isEqualTo(1);
	}

	/** Spiele 1+2 liegen vor dem Schnitt, Spiele 3+4 und ihre Buchungen dahinter. */
	@Test
	public void testSaldoBleibtErhaltenWennBuchungenNachDemSchnittExistieren() {
		Map<Integer, BigDecimal> saldoVorher = saldoProSpieler();

		cutOffService.starteBuchungsschnitt(datum(2014, Calendar.JANUARY, 4, 0));

		assertThat(saldoProSpieler()).isEqualTo(saldoVorher);
	}

	/** Spiel 3 faellt auf den Schnitttag, seine Buchungen sind aber spaeter erfasst. */
	@Test
	public void testSaldoBleibtErhaltenWennSpielAufSchnitttagSpaeterErfasstWurde() {
		Map<Integer, BigDecimal> saldoVorher = saldoProSpieler();

		cutOffService.starteBuchungsschnitt(datum(2014, Calendar.JANUARY, 5, 0));

		assertThat(saldoProSpieler()).isEqualTo(saldoVorher);
	}

	@Test
	public void testSaldoBleibtErhaltenBeiWiederholtemSchnittZumGleichenDatum() {
		entferneSeasonIndexWieImProduktivschema();
		Map<Integer, BigDecimal> saldoVorher = saldoProSpieler();
		Date datum = datum(2016, Calendar.JANUARY, 1, 0);

		cutOffService.starteBuchungsschnitt(datum);
		em.clear();
		cutOffService.starteBuchungsschnitt(datum);

		assertThat(saldoProSpieler()).isEqualTo(saldoVorher);
	}

	@Test
	public void testSaldoBleibtErhaltenBeiWiederholtemSchnittAmGleichenTag() {
		entferneSeasonIndexWieImProduktivschema();
		Map<Integer, BigDecimal> saldoVorher = saldoProSpieler();

		cutOffService.starteBuchungsschnitt(datum(2016, Calendar.JANUARY, 1, 10));
		em.clear();
		cutOffService.starteBuchungsschnitt(datum(2016, Calendar.JANUARY, 1, 18));

		assertThat(saldoProSpieler()).isEqualTo(saldoVorher);
	}

	@Test
	public void testSaldoBleibtErhaltenBeiAufeinanderfolgendenSchnitten() {
		Map<Integer, BigDecimal> saldoVorher = saldoProSpieler();

		cutOffService.starteBuchungsschnitt(datum(2014, Calendar.JANUARY, 4, 0));
		em.clear();
		cutOffService.starteBuchungsschnitt(datum(2016, Calendar.JANUARY, 1, 0));

		assertThat(saldoProSpieler()).isEqualTo(saldoVorher);
		assertThat(facadenFactory.getBuchungFacade().count()).isEqualTo(10);
	}

	@Test
	public void testSaldoBleibtErhaltenBeiSpaetererBuchungOhneSpiel() {
		Buchung buchung = new Buchung();
		buchung.setDatum(datum(2014, Calendar.JANUARY, 10, 0));
		buchung.setPrice(BigDecimal.TEN);
		buchung.setSpieler(facadenFactory.getSpielerFacade().find(1));
		facadenFactory.getBuchungFacade().create(buchung);
		Map<Integer, BigDecimal> saldoVorher = saldoProSpieler();

		cutOffService.starteBuchungsschnitt(datum(2014, Calendar.JANUARY, 4, 0));

		assertThat(saldoProSpieler()).isEqualTo(saldoVorher);
	}

	/** Der Unique-Index stammt aus der Entity und fehlt in db/migrations. */
	private void entferneSeasonIndexWieImProduktivschema() {
		startTransaction();
		em.createNativeQuery("ALTER TABLE SEASON DROP CONSTRAINT start_end_idx").executeUpdate();
		commitTransaction();
	}

	private Map<Integer, BigDecimal> saldoProSpieler() {
		em.clear();
		Map<Integer, BigDecimal> saldi = new TreeMap<>();
		facadenFactory.getBuchungFacade().findAll().forEach(buchung -> saldi.merge(buchung.getSpieler().getId(),
				buchung.getPrice().setScale(2, RoundingMode.HALF_UP), BigDecimal::add));
		return saldi;
	}

	private static Date datum(final int jahr, final int monat, final int tag, final int stunde) {
		return new GregorianCalendar(jahr, monat, tag, stunde, 0).getTime();
	}

}

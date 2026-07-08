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

}

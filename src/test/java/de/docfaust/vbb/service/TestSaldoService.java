package de.docfaust.vbb.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.docfaust.vbb.ServiceCreator;
import de.docfaust.vbb.data.entity.Spiel;
import de.docfaust.vbb.data.util.JpaBaseRolledBackTestCase;
import de.docfaust.vbb.model.SaldoModel;
import de.docfaust.vbb.util.EntityFactory;

class TestSaldoService extends JpaBaseRolledBackTestCase {
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

	private SaldoService service;

	@BeforeEach
	public void init() {
		service = new ServiceCreator(em).getSaldoService();

	}

	@Test
	void testCreateSaldo() {
		assertThat(service).isNotNull();
	}

	@Test
	void testGetSaldo() {
		SaldoModel saldoModel = service.getSaldo();
		assertThat(saldoModel).isNotNull();
		logger.info("SaldoModel: ", saldoModel.toString());
		assertThat(saldoModel.getCompleteSaldo().doubleValue()).isEqualTo(BigDecimal.ZERO.doubleValue());
		assertThat(saldoModel.getSpielersaldi()).isNotNull().isNotEmpty().hasSize(10);

		assertThat(saldoModel.getSpielersaldi()).extracting("spielerName", "saldo").contains(
				tuple(ALEX_ANDER, BigDecimal.valueOf(-7.5F).setScale(2, RoundingMode.HALF_UP)),
				tuple(BEA_BEISPIEL, BigDecimal.valueOf(-7.5F).setScale(2, RoundingMode.HALF_UP)),
				tuple(CHRIS_CORDT, BigDecimal.valueOf(1.5F).setScale(2, RoundingMode.HALF_UP)),
				tuple(DANA_DORN, BigDecimal.valueOf(-4.5F).setScale(2, RoundingMode.HALF_UP)),
				tuple(EMIL_ENGEL, BigDecimal.valueOf(8F).setScale(2, RoundingMode.HALF_UP)),
				tuple(FRIEDA_FALK, BigDecimal.valueOf(-2F).setScale(2, RoundingMode.HALF_UP)),
				tuple(GUSTAV_GRUEN, BigDecimal.valueOf(-2F).setScale(2, RoundingMode.HALF_UP)),
				tuple(HELENE_HERBST, BigDecimal.valueOf(-2F).setScale(2, RoundingMode.HALF_UP)),
				tuple(IDA_IMMER, BigDecimal.valueOf(7F).setScale(2, RoundingMode.HALF_UP)),
				tuple(JONAS_JOREK, BigDecimal.valueOf(9F).setScale(2, RoundingMode.HALF_UP)));
	}

	@Test
	void testIsSpielSaldoZeroTrue() {
		Spiel spiel = facadenFactory.getSpielFacade().find(1);
		assertThat(service.isSpielSaldoZero(spiel)).isTrue();
	}

	@Test
	void testIsSpielSaldoZeroFalse() {
		Spiel spiel = facadenFactory.getSpielFacade().find(1);
		spiel.addBuchung(EntityFactory.createBuchung(new Date(), BigDecimal.ONE));
		assertThat(service.isSpielSaldoZero(spiel)).isFalse();
	}
}

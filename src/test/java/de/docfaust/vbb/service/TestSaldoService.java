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
				tuple(ALFRED_ALTMANN, BigDecimal.valueOf(-7.5F).setScale(2, RoundingMode.HALF_UP)),
				tuple(BERND_BROT, BigDecimal.valueOf(-7.5F).setScale(2, RoundingMode.HALF_UP)),
				tuple(CLAUS_CASPAR, BigDecimal.valueOf(1.5F).setScale(2, RoundingMode.HALF_UP)),
				tuple(DOREEN_DURSTIG, BigDecimal.valueOf(-4.5F).setScale(2, RoundingMode.HALF_UP)),
				tuple(ERICH_EHRLICH, BigDecimal.valueOf(8F).setScale(2, RoundingMode.HALF_UP)),
				tuple(FRANZ_FROEHLICH, BigDecimal.valueOf(-2F).setScale(2, RoundingMode.HALF_UP)),
				tuple(GRETCHEN_GROEHL, BigDecimal.valueOf(-2F).setScale(2, RoundingMode.HALF_UP)),
				tuple(HANS_HOHLBIRNE, BigDecimal.valueOf(-2F).setScale(2, RoundingMode.HALF_UP)),
				tuple(INES_IGNORANT, BigDecimal.valueOf(7F).setScale(2, RoundingMode.HALF_UP)),
				tuple(JOHANN_JOCHBEIN, BigDecimal.valueOf(9F).setScale(2, RoundingMode.HALF_UP)));
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

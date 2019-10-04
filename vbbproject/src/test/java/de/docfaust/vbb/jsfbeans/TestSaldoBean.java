package de.docfaust.vbb.jsfbeans;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.docfaust.vbb.ServiceCreator;
import de.docfaust.vbb.data.util.JpaBaseRolledBackTestCase;
import de.docfaust.vbb.model.SaldoModel;

public class TestSaldoBean extends JpaBaseRolledBackTestCase {
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

	private Logger logger = LoggerFactory.getLogger(getClass()); 
	
	@Test
	void testInit() {
		ServiceCreator sc = new ServiceCreator(em);
		SaldoBean b = new SaldoBean(sc.getSaldoService(), sc.getTokenService());
		SaldoModel saldoModel = b.getSaldoModel();
		
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
}

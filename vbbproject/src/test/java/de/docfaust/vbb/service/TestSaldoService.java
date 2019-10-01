package de.docfaust.vbb.service;


import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import de.docfaust.vbb.data.facades.BuchungFacade;
import de.docfaust.vbb.data.facades.SpielerFacade;
import de.docfaust.vbb.data.util.JpaBaseRolledBackTestCase;
import de.docfaust.vbb.model.SaldoModel;

class TestSaldoService extends JpaBaseRolledBackTestCase {

	@Test
	void testCreateSaldo() {
		SaldoService service = new SaldoServiceImpl();
		assertThat(service).isNotNull();
	}
	
	@Test
	void testGetSaldo() {
		SaldoService service = new SaldoServiceImpl(new BuchungFacade(em), new SpielerFacade(em));
		SaldoModel saldo = service.getSaldo();
		assertThat(saldo).isNotNull();
		logger.info("SaldoModel: ", saldo.toString());
		assertThat(saldo.getCompleteSaldo().doubleValue()).isEqualTo(BigDecimal.ZERO.doubleValue());
		assertThat(saldo.getSpielersaldi()).isNotNull();
		assertThat(saldo.getSpielersaldi()).isNotEmpty();
		assertThat(saldo.getSpielersaldi()).hasSize(10);
	}

}

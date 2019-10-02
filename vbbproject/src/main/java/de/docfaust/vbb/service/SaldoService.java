package de.docfaust.vbb.service;

import java.io.Serializable;
import java.math.BigDecimal;

import de.docfaust.vbb.data.entity.Spiel;
import de.docfaust.vbb.model.SaldoModel;

/**
 * Service for the Saldo Bean.
 * @author wfa339
 *
 */
public interface SaldoService extends Serializable {

	/**
	 * Calculates the Saldo.
	 * @return SaldoModel.
	 */
	SaldoModel getSaldo();

	BigDecimal getCompleteSaldo();

	BigDecimal getSpielSaldo(final Spiel spiel);

	boolean isSpielSaldoZero(final Spiel spiel);

}
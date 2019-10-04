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

	/**
	 * Calculates overall saldo.
	 * @return Saldo
	 */
	BigDecimal getCompleteSaldo();

	/**
	 * Calculates the Saldo for a Spiel.
	 * @param spiel Game
	 * @return Saldo
	 */
	BigDecimal getSpielSaldo(Spiel spiel);

	/**
	 * Returns if Saldo for Spiel is 0.
	 * @param spiel Spiel
	 * @return true if 0
	 */
	boolean isSpielSaldoZero(Spiel spiel);

}
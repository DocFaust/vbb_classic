package de.docfaust.vbb.service;

import java.io.Serializable;

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

}
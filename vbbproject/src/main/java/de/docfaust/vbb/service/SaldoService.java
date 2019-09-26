package de.docfaust.vbb.service;

import java.io.Serializable;

import de.docfaust.vbb.model.SaldoModel;

public interface SaldoService extends Serializable {

	SaldoModel getSaldo();

}
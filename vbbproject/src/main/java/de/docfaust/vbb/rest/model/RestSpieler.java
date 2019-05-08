package de.docfaust.vbb.rest.model;

import java.math.BigDecimal;

/**
 * DTO.
 * @author wfa339
 *
 */
public class RestSpieler {
	private String name;
	private boolean bezahlt;
	private boolean anwesend;
	private BigDecimal saldo;

	/**
	 * DTO Constructor.
	 * @param name Name of the Spieler
	 */
	public RestSpieler(final String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public boolean isBezahlt() {
		return bezahlt;
	}

	public void setBezahlt(final boolean bezahlt) {
		this.bezahlt = bezahlt;
	}

	public boolean isAnwesend() {
		return anwesend;
	}

	public void setAnwesend(final boolean anwesend) {
		this.anwesend = anwesend;
	}

	public BigDecimal getSaldo() {
		return saldo;
	}

	public void setSaldo(final BigDecimal saldo) {
		this.saldo = saldo;
	}

}

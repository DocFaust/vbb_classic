package de.docfaust.vbb.rest.model;

import java.math.BigDecimal;

public class RestSpieler {
	private String name;
	private boolean bezahlt;
	private boolean anwesend;
	private BigDecimal saldo;

	public RestSpieler(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isBezahlt() {
		return bezahlt;
	}

	public void setBezahlt(boolean bezahlt) {
		this.bezahlt = bezahlt;
	}

	public boolean isAnwesend() {
		return anwesend;
	}

	public void setAnwesend(boolean anwesend) {
		this.anwesend = anwesend;
	}

	public BigDecimal getSaldo() {
		return saldo;
	}

	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}

}

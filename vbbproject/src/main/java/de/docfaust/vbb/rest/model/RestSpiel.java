package de.docfaust.vbb.rest.model;

import java.util.List;

/**
 * DTO Spieler.
 * @author wfa339
 *
 */
public class RestSpiel {
	private String date;
	private List<RestSpieler> spieler;

	public String getDate() {
		return date;
	}

	public void setDate(final String date) {
		this.date = date;
	}

	public List<RestSpieler> getSpieler() {
		return spieler;
	}

	public void setSpieler(final List<RestSpieler> spieler) {
		this.spieler = spieler;
	}
}

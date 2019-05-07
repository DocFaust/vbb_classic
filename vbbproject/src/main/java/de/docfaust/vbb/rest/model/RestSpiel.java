package de.docfaust.vbb.rest.model;

import java.util.List;

public class RestSpiel {
	private String date;
	private List<RestSpieler> spieler;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public List<RestSpieler> getSpieler() {
		return spieler;
	}

	public void setSpieler(List<RestSpieler> spieler) {
		this.spieler = spieler;
	}
}

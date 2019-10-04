package de.docfaust.vbb.util;

import de.docfaust.vbb.data.entity.Spieler;

public class SpielerBuilder {
	private int activityLevel;
	private boolean anwesend;
	private boolean bezahlt;
	private String email;
	private int id;
	private String name;

	private SpielerBuilder() {
	}

	public static SpielerBuilder create() {
		return new SpielerBuilder();
	}

	public Spieler build() {
		Spieler spieler = new Spieler();
		spieler.setActivityLevel(activityLevel);
		spieler.setAnwesend(anwesend);
		spieler.setBezahlt(bezahlt);
		spieler.setEmail(email);
		spieler.setId(id);
		spieler.setName(name);
		return spieler;
	}

	public SpielerBuilder activityLevel(int activityLevel) {
		this.activityLevel = activityLevel;
		return this;
	}

	public SpielerBuilder id(int id) {
		this.id = id;
		return this;
	}

	public SpielerBuilder name(String name) {
		this.name = name;
		return this;
	}

	public SpielerBuilder email(String email) {
		this.email = email;
		return this;
	}

	public SpielerBuilder bezahlt(boolean bezahlt) {
		this.bezahlt = bezahlt;
		return this;
	}

	public SpielerBuilder anwesend(boolean anwesend) {
		this.anwesend = anwesend;
		return this;
	}
}

package de.docfaust.vbb.validation;

import de.docfaust.vbb.data.entity.Season;
import de.docfaust.vbb.util.statusliste.Statusliste;

/**
 * Factory zur Validierung von Entityklassen.
 * 
 * @author xhu1011
 *
 */
public final class ValidationFactory {

	/**
	 * Versteckter Konstruktor.
	 * @return ValidationFactory Objekt
	 */
	public static ValidationFactory create() {
		return new ValidationFactory();
	}

	/**
	 * Validiert eine Saison.
	 * 
	 * @param season
	 *            Season
	 * @return StatusListe
	 */
	public Statusliste validate(final Season season) {
		SeasonValidator validator = new SeasonValidator();
		return validator.validate(season);
	}
}

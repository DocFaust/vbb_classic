package de.docfaust.vbb.validation;

import de.docfaust.vbb.data.entity.Season;
import de.docfaust.vbb.util.messages.MessageConstants;
import de.docfaust.vbb.util.statusliste.Statusliste;

/**
 * Validiert eine Saison.
 * 
 * @author Werner
 *
 */
public class SeasonValidator implements Validator<Season> {

	/**
	 * Prüft die Saison auf Vorhandensein und Gültigkeit.
	 * 
	 * @param validatedClass
	 *            zu validierende Klasse
	 * @return Statusliste im Erfolgsfall leer
	 */
	@Override
	public Statusliste validate(final Season validatedClass) {
		Statusliste statusliste = new Statusliste();
		if (validatedClass == null) {
			statusliste.addStatus(MessageConstants.SEASON_NOT_FOUND);
		} else if (validatedClass.getPrice() == null && validatedClass.getDescription() != null && validatedClass.getDescription().equals("Buchungsschnitt")) {
			statusliste.addStatus(MessageConstants.SEASON_IS_BUCHUNGSSCHNITT);
		} else if (validatedClass.getPrice() == null) {
			statusliste.addStatus(MessageConstants.SEASON_NO_PRICE);
		}
		return statusliste;
	}

}

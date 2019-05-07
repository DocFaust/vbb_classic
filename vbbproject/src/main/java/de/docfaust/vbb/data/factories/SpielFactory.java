package de.docfaust.vbb.data.factories;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.docfaust.vbb.data.entity.Season;
import de.docfaust.vbb.data.entity.Spiel;

/**
 * Factoryklasse für ein Spiel.
 * 
 * @author xhu1011
 *
 */
public final class SpielFactory {
	private static Logger logger = LoggerFactory.getLogger(SpielFactory.class);

	private SpielFactory() {
	}
	
	/**
	 * Erstellt ein Spiel.
	 * @param datum Datum des Spiels
	 * @param season Saison
	 * @return befüllte Spielobjekt
	 */
	public static Spiel createSpiel(final Date datum, final Season season) {
		Spiel spiel = new Spiel();
		spiel.setDatum(datum);
		spiel.setSeason(season);
		logger.debug("Erstelle neues Spiel: " + spiel.toString());
		return spiel;
	}
}

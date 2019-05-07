package de.docfaust.vbb.data.factories;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.docfaust.vbb.data.entity.Spieler;

/**
 * 
 * @author xhu1011
 *
 */
public final class SpielerFactory {
	private static Logger logger = LoggerFactory.getLogger(SpielerFactory.class);
	
	private SpielerFactory() {
	}
	
	/**
	 * Erstellt einen Spieler.
	 * @param name Name
	 * @param email Mailadresse
	 * @return Spielerobjekt
	 */
	public static Spieler createSpieler(final String name, final String email) {
		Spieler spieler = new Spieler();
		spieler.setName(name);
		spieler.setEmail(email);
		logger.debug("Erstelle neuen Spieler: " + spieler.toString());
		return spieler;
	}
}

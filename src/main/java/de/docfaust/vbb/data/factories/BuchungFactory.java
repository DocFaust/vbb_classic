package de.docfaust.vbb.data.factories;

import java.math.BigDecimal;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.docfaust.vbb.data.entity.Buchung;
import de.docfaust.vbb.data.entity.Spiel;
import de.docfaust.vbb.data.entity.Spieler;

/**
 * Factory für Buchungen.
 * 
 * @author xhu1011
 *
 */
public final class BuchungFactory {
	/** BEZAHLUNG. */
	public static final String DESC_BEZAHLUNG = "Bezahlung";
	/** Spieleinsatz. */
	public static final String DESC_SPIELEINSATZ = "Spieleinsatz";
	/** Bezahlung. */
	public static final String DESC_BUCHUNGSSCHNITT = "Buchungsschitt";
	
	private static Logger logger = LoggerFactory.getLogger(BuchungFactory.class);
	
	private BuchungFactory() {
	}

	/**
	 * Erzeugt eine neue Buchung mit allen Parametern.
	 * 
	 * @param datum
	 *            Datum der Buchung
	 * @param description
	 *            Beschreibung
	 * @param price
	 *            Betrag
	 * @param spiel
	 *            Spiel
	 * @param spieler
	 *            Spieler
	 * @return neue Buchung
	 */
	public static Buchung createBuchung(final Date datum, final String description, final BigDecimal price,
			final Spiel spiel, final Spieler spieler) {
		Buchung buchung = new Buchung();
		buchung.setDatum(datum);
		buchung.setDescription(description);
		buchung.setPrice(price);
		buchung.setSpiel(spiel);
		buchung.setSpieler(spieler);
		logger.debug("Erstelle Buchung: " + buchung.toString());
		return buchung;
	}
}

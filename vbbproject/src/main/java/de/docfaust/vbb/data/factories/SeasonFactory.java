package de.docfaust.vbb.data.factories;

import java.math.BigDecimal;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.docfaust.vbb.data.entity.Season;

/**
 * 
 * @author xhu1011
 *
 */
public final class SeasonFactory {
	/** Konstante Buchungsschnitt. */
	public static final String DESC_BUCHUNGSSCHNITT = "Buchungsschnitt";
	private static Logger logger = LoggerFactory.getLogger(SeasonFactory.class);

	private SeasonFactory() {
	}

	/**
	 * Erstellt eine Season.
	 * 
	 * @param startdate
	 *            Startdatum
	 * @param enddate
	 *            Enddatum
	 * @param description
	 *            Beschreibung
	 * @param price
	 *            Preis
	 * @return Seasononjekt
	 */
	public static Season createSeason(final Date startdate, final Date enddate, final String description, final BigDecimal price) {
		Season season = new Season();
		season.setStartdate(startdate);
		season.setEnddate(enddate);
		season.setDescription(description);
		season.setPrice(price);
		logger.debug("Erstelle neue Saison: " + season.toString());
		return season;
	}
}

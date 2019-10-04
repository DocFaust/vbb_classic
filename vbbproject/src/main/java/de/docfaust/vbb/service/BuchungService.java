package de.docfaust.vbb.service;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import de.docfaust.vbb.data.entity.Buchung;

/**
 * Services for the Buchung Entity.
 * 
 * @author wfa339
 *
 */
public interface BuchungService extends Serializable {

	/**
	 * Gibt alle Buchungen zurück.
	 * 
	 * @return Liste der Buchungen.
	 */
	List<Buchung> getBuchungen();

	/**
	 * Löscht eine Buchung.
	 * 
	 * @param buchung zu Löschende Buchung.
	 */
	void deleteBuchung(Buchung buchung);

	/**
	 * Speichert eine Buchung.
	 * 
	 * @param buchung Zu speichernde Buchung
	 */
	void saveBuchung(Buchung buchung);

	/**
	 * Finds Buchungen before the given Date.
	 * 
	 * @param datum Date
	 * @return List of Buchungen
	 */
	List<Buchung> getBuchungenBeforeDate(Date datum);

	/**
	 * Removes all Buchungen before the given date.
	 * @param datum Date
	 */
	void removeOldBuchungen(Date datum);

}
package de.docfaust.vbb.service;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import de.docfaust.vbb.data.entity.Buchung;

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
	 * @param buchung
	 *            zu Löschende Buchung.
	 */
	void deleteBuchung(Buchung buchung);

	/**
	 * Speichert eine Buchung.
	 * 
	 * @param buchung
	 *            Zu speichernde Buchung
	 */
	void saveBuchung(Buchung buchung);

	List<Buchung> getBuchungenBeforeDate(Date datum);

	void removeOldBuchungen(final Date datum);

}
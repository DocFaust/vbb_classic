package de.docfaust.vbb.service;

import java.io.Serializable;
import java.util.List;

import de.docfaust.vbb.data.entity.Spieler;
import de.docfaust.vbb.util.statusliste.Statusliste;

/**
 * Service for Entity Spieler.
 * @author wfa339
 *
 */
public interface SpielerService extends Serializable {

	/**
	 * Returns List of all Spieler.
	 * @return List of all Spieler
	 */
	List<Spieler> getSpieler();

	/**
	 * Gibt die Namen der Spieler zurück.
	 * 
	 * @return Liste der Namen
	 */
	List<String> getSpielerNames();

	/**
	 * Speichert einen Spieler.
	 * 
	 * @param spieler
	 *            Zu speichernder Spieler.
	 */
	void saveSpieler(Spieler spieler);

	/**
	 * Löscht einen Spieler.
	 * 
	 * @param spieler
	 *            Zu löschender Spieler
	 * @return Statusliste
	 */
	Statusliste deleteSpieler(Spieler spieler);

	/**
	 * Increments the activitylevel of a given Spieler.
	 * @param spieler Spieler
	 */
	void incrementActivityLevel(Spieler spieler);

}
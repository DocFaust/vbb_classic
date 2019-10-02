package de.docfaust.vbb.service;

import java.io.Serializable;
import java.util.List;

import de.docfaust.vbb.data.entity.Spieler;
import de.docfaust.vbb.util.statusliste.Statusliste;

public interface SpielerService extends Serializable {

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

	void incrementActivityLevel(final Spieler spieler);

}
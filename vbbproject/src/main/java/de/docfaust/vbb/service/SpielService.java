package de.docfaust.vbb.service;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import de.docfaust.vbb.data.entity.Spiel;
import de.docfaust.vbb.data.entity.Spieler;
import de.docfaust.vbb.util.statusliste.Statusliste;

public interface SpielService extends Serializable {

	/**
	 * Speichert ein Spiel. Es wird ermittelt, wie hoch der Preis pro Person
	 * ist, wenn die Summe nicht in möglichen Beträgen Aufgeht, so wird
	 * aufgerundet und der Bezahlende bekommt die Differenz
	 * 
	 * 
	 * <table>
	 * <tr>
	 * Liste der ReturnCodes:
	 * </tr>
	 * <tr>
	 * <th align="left">Returncode</th>
	 * <th align="left">Beschreibung</th>
	 * </tr>
	 * <tr>
	 * <td>SUCESSFUL</td>
	 * <td>Spiel ordnungsgemäß gespeichert</td>
	 * </tr>
	 * <tr>
	 * <td>ALREADYEXISTING</td>
	 * <td>Spiel existiert bereits für dieses Datum</td>
	 * </tr>
	 * <tr>
	 * <td>SEASON_NOT_FOUND</td>
	 * <td>Keine Season für das Spiel gefunden</td>
	 * </tr>
	 * <tr>
	 * <td>SEASON_NO_PRICE</td>
	 * <td>Die Saison hat keinen Preis</td>
	 * </tr>
	 * 
	 * </table>
	 * 
	 * @param spielerList
	 *            Liste der Spieler
	 * @param datum
	 *            Datum des Spiels
	 * @return Statusliste {@link Statusliste}
	 */
	Statusliste saveSpiel(List<Spieler> spielerList, Date datum);

	/**
	 * Speichert ein Spiel.
	 * 
	 * @param spiel
	 *            zu speicherndes Spiel
	 * @return Statusliste
	 */
	Statusliste saveSpiel(Spiel spiel);

	Statusliste deleteSpiel(final Spiel spiel);

	List<Spiel> getSpiele();

	void removeOldSpiele(final Date date);

}
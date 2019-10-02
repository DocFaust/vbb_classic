package de.docfaust.vbb.service;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import de.docfaust.vbb.data.entity.Season;
import de.docfaust.vbb.util.statusliste.Statusliste;

public interface SeasonService extends Serializable {

	/**
	 * Liefert die Saison zu einem Datum.
	 * 
	 * @param datum Datum
	 * @return Saison
	 */
	Season getSeason(Date datum);

	/**
	 * Speichert eine Season.
	 * 
	 * @param season
	 *            Season
	 */
	void saveSeason(Season season);

	/**
	 * Löscht eine Season.
	 * 
	 * @param season
	 *            Season
	 * @return Returncode
	 */
	Statusliste deleteSaison(Season season);

	List<Season> getSeasons();

	/**
	 * Löscht alle Seasons vor einem Datum.
	 * 
	 * @param date
	 *            Datum
	 */
	void removeOldSeasons(Date date);

}
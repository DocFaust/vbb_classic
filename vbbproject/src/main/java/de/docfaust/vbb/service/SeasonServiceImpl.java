package de.docfaust.vbb.service;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.docfaust.vbb.data.entity.Season;
import de.docfaust.vbb.data.facades.SeasonFacade;
import de.docfaust.vbb.util.statusliste.Statusliste;

/**
 * Implementation of SeasonService.
 * @author wfa339
 *
 */
@Dependent
public class SeasonServiceImpl implements SeasonService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7091478010167639462L;

	@EJB
	private SeasonFacade seasonFacade;

	@Inject
	private Logger logger;

	/**
	 * @param seasonFacade from JUnit
	 */
	public SeasonServiceImpl(final SeasonFacade seasonFacade) {
		super();
		this.seasonFacade = seasonFacade;
		this.logger = LoggerFactory.getLogger(getClass());
	}
	/**
	 * 
	 */
	public SeasonServiceImpl() {
		super();
	}
	/**
	 * Liefert die Saison zu einem Datum.
	 * 
	 * @param datum Datum
	 * @return Saison
	 */
	@Override
	public Season getSeason(final Date datum) {
		logger.debug("Suche Saison für " + datum);
		Season season = seasonFacade.getSeasonFromDate(datum);
		return season;
	}
	/**
	 * Speichert eine Season.
	 * 
	 * @param season
	 *            Season
	 */
	@Override
	public final void saveSeason(final Season season) {
		seasonFacade.createOrUpdate(season);
	}

	/**
	 * Löscht eine Season.
	 * 
	 * @param season
	 *            Season
	 * @return Returncode
	 */
	@Override
	public final Statusliste deleteSaison(final Season season) {
		logger.info("Lösche Saison: " + season.getDescription());
		return seasonFacade.deleteSeason(season);
	}

	@Override
	public final List<Season> getSeasons() {
		return seasonFacade.findAll();
	}

	/**
	 * Löscht alle Seasons vor einem Datum.
	 * 
	 * @param date
	 *            Datum
	 */
	@Override
	public void removeOldSeasons(final Date date) {
		logger.debug("Lösche alte Seasons");
		seasonFacade.getSeasonsBeforeDate(date).stream().forEach(season -> seasonFacade.remove(season));
	}

}

package de.docfaust.vbb.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.docfaust.vbb.data.entity.Buchung;
import de.docfaust.vbb.data.entity.Season;
import de.docfaust.vbb.data.entity.Spiel;
import de.docfaust.vbb.data.entity.Spieler;
import de.docfaust.vbb.data.factories.BuchungFactory;
import de.docfaust.vbb.data.factories.SeasonFactory;
import de.docfaust.vbb.data.factories.SpielFactory;

/**
 * Implementation of the CutOffService.
 * @author wfa339
 *
 */
@Dependent
public class CutOffServiceImpl implements CutOffService {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1362759035417744837L;
	@Inject
	private Logger logger;
	@Inject
	private BuchungService buchungService;
	@Inject
	private SpielService spielService;
	@Inject
	private SpielerService spielerService;
	@Inject
	private SeasonService seasonService;

	/**
	 * @param buchungService for JUnit
	 * @param spielService for JUnit
	 * @param spielerService for JUnit
	 * @param seasonService for JUnit
	 */
	public CutOffServiceImpl(final BuchungService buchungService, final SpielService spielService,
			final SpielerService spielerService, final SeasonService seasonService) {
		super();
		this.buchungService = buchungService;
		this.spielService = spielService;
		this.spielerService = spielerService;
		this.seasonService = seasonService;
		this.logger = LoggerFactory.getLogger(getClass());
	}

	/**
	 * 
	 */
	public CutOffServiceImpl() {
		super();
	}

	/**
	 * Führt einen kompletten Buchungsschnitt durch.
	 * 
	 * @param date Datum zu dem der Buchungsschnitt durchgeführt werden soll.
	 */
	@Override
	public void starteBuchungsschnitt(final Date date) {
		logger.info("Starte kompletten Buchungsschnitt zum " + date.toString());
		createBuchungsschnitt(date);
		buchungService.removeOldBuchungen(date);
		spielService.removeOldSpiele(date);
		seasonService.removeOldSeasons(date);
	}

	/**
	 * Macht einen Buchungsschnitt zum aktuellen Datum. Es wird eine neue Saison
	 * erstellt und ein neues Spiel.
	 * 
	 * @param datum Datum, zu dem der Buchungsschnitt stattfindet
	 * 
	 */
	private void createBuchungsschnitt(final Date datum) {
		logger.debug("Erstelle Buchungsschnitt");

		Season season = SeasonFactory.createSeason(datum, datum, SeasonFactory.DESC_BUCHUNGSSCHNITT, BigDecimal.ZERO);
		seasonService.saveSeason(season);

		Spiel spiel = SpielFactory.createSpiel(datum, season);
		spielService.saveSpiel(spiel);

		spielerService.getSpieler().stream().forEach(spieler -> {
			BigDecimal saldo = getSaldoForSpieler(spieler);
			Buchung buchung = BuchungFactory.createBuchung(datum, BuchungFactory.DESC_BUCHUNGSSCHNITT, saldo, spiel,
					spieler);
			buchungService.saveBuchung(buchung);
			spieler.setActivityLevel(0);
			spielerService.saveSpieler(spieler);
		});

	}

	/**
	 * Liefert das Saldo für einen Spieler.
	 * 
	 * @param spieler Spieler
	 * @return Saldo
	 */
	public final BigDecimal getSaldoForSpieler(final Spieler spieler) {
		List<Buchung> buchungen = spieler.getBuchungen();
		BigDecimal saldo = buchungen.stream().map(Buchung::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
		return saldo;
	}

}

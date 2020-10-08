package de.docfaust.vbb.jsfbeans;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.docfaust.vbb.data.entity.Buchung;
import de.docfaust.vbb.data.entity.Spiel;
import de.docfaust.vbb.data.entity.Spieler;
import de.docfaust.vbb.service.BuchungService;
import de.docfaust.vbb.service.SaldoService;
import de.docfaust.vbb.service.SeasonService;
import de.docfaust.vbb.service.SpielService;
import de.docfaust.vbb.service.SpielerService;
import de.docfaust.vbb.util.messages.UIMessages;
import de.docfaust.vbb.util.statusliste.Statusliste;

/**
 * Bean zur Verwaltung des Benutzerprofils.
 * 
 * @author xhu1011
 *
 */
@ViewScoped
@Named
public class CreateIndividualBookingBean extends AbstractJSFBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6159834826207911500L;

	@Inject
	private Logger logger;

	private Buchung selectedBuchung = null;
	private List<Spieler> alleSpieler = new ArrayList<>();
	private BigDecimal saldo;
	private BigDecimal completeSaldo;
	private Spiel selectedSpiel;

	@Inject
	private SpielerService spielerService;
	
	@Inject
	private SpielService spielService;

	@Inject
	private SeasonService seasonService;

	@Inject
	private BuchungService buchungService;

	@Inject
	private SaldoService saldoService;

	/**
	 * Konstruktor ohne EJB Kontext.
	 * 
	 * @param uiMessages     from JUnit
	 * @param spielerService from JUnit
	 * @param spielService   from JUnit
	 * @param seasonService  from JUnit
	 * @param buchungService from JUnit
	 * @param saldoService   from JUnit
	 */
	public CreateIndividualBookingBean(final UIMessages uiMessages, final SpielerService spielerService,
			final SpielService spielService, final SeasonService seasonService, final BuchungService buchungService,
			final SaldoService saldoService) {
		super(uiMessages);
		this.spielerService = spielerService;
		this.spielService = spielService;
		this.seasonService = seasonService;
		this.buchungService = buchungService;
		this.saldoService = saldoService;
		logger = LoggerFactory.getLogger(getClass());
	}

	/**
	 * Konstruktor mit EJB Kontext.
	 * 
	 */
	public CreateIndividualBookingBean() {
		super();
	}

	/**
	 * Lädt den aktuell angemeldeten User.
	 */
	@PostConstruct
	public void init() {
		setAlleSpieler(spielerService.getSpieler());
		setCompleteSaldo(saldoService.getCompleteSaldo());
		setSelectedSpiel(new Spiel());
		setSelectedDate(new Date());
		selectedSpiel.setSeason(seasonService.getSeason(selectedSpiel.getDatum()));

	}

	/**
	 * Speichert den aktuellen User.
	 */
	public void saveBuchungen() {
		logger.info("Speichere " + selectedBuchung.toString());
		if (getSelectedSpiel() == null) {
			setSelectedSpiel(new Spiel());
		}
		Statusliste statusliste = spielService.saveSpiel(selectedSpiel);
		selectedSpiel.getBuchungen().forEach(buchung -> buchungService.saveBuchung(buchung));
		showMessages(statusliste);
		completeSaldo = saldoService.getCompleteSaldo();
		saldo = getSaldo();
	}

	public Spiel getSelectedSpiel() {
		return selectedSpiel;
	}

	public void setSelectedSpiel(final Spiel selectedSpiel) {
		this.selectedSpiel = selectedSpiel;
	}

	/**
	 * Fügt eine neue Buchung hinzu.
	 */
	public void addBuchung() {
		logger.info("Lege neue Buchung an");

		setSelectedBuchung(new Buchung());
		selectedBuchung.setDatum(selectedSpiel.getDatum());
		selectedSpiel.addBuchung(selectedBuchung);
	}

	public Buchung getSelectedBuchung() {
		return selectedBuchung;
	}

	public void setSelectedBuchung(final Buchung selectedBuchung) {
		this.selectedBuchung = selectedBuchung;
	}

	public Date getSelectedDate() {
		return selectedSpiel.getDatum();
	}

	/**
	 * Setzt das Selektierte Datum in das ausgewählte Spiel.
	 * 
	 * @param selectedDate Datum
	 */
	public void setSelectedDate(final Date selectedDate) {
		selectedSpiel.setDatum(selectedDate);
	}

	public List<Spieler> getAlleSpieler() {
		return alleSpieler;
	}

	public void setAlleSpieler(final List<Spieler> alleSpieler) {
		this.alleSpieler = alleSpieler;
	}

	/**
	 * Berechnet des Saldo des ausgewählten Spiels und gibt ihn zurück.
	 * 
	 * @return Saldo des ausgewählten Spiels
	 */
	public BigDecimal getSaldo() {
		setSaldo(selectedSpiel.getBuchungen().stream().map(Buchung::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add));
		return saldo;
	}

	public void setSaldo(final BigDecimal saldo) {
		this.saldo = saldo;
	}

	public BigDecimal getCompleteSaldo() {
		return completeSaldo;
	}

	public void setCompleteSaldo(final BigDecimal completeSaldo) {
		this.completeSaldo = completeSaldo;
	}

}

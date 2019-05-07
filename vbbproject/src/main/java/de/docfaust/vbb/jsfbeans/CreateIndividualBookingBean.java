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
import de.docfaust.vbb.service.VBBServices;
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

	// private List<Buchung> buchungen = new ArrayList<>();
	private Buchung selectedBuchung = null;
	private List<Spieler> alleSpieler = new ArrayList<>();
	private BigDecimal saldo;
	private BigDecimal completeSaldo;
	private Spiel selectedSpiel;

	/**
	 * Konstruktor ohne EJB Kontext.
	 * 
	 * @param services
	 *            Services
	 * @param uiMessages
	 *            UIMessages
	 */
	public CreateIndividualBookingBean(final VBBServices services, final UIMessages uiMessages) {
		super(services, uiMessages);
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
		setAlleSpieler(getServices().getSpieler());
		setCompleteSaldo(getServices().getCompleteSaldo());
		setSelectedSpiel(new Spiel());
		setSelectedDate(new Date());
		selectedSpiel.setSeason(getServices().getSeason(selectedSpiel.getDatum()));

	}

	/**
	 * Speichert den aktuellen User.
	 */
	public void saveBuchungen() {
		logger.info("Speichere " + selectedBuchung.toString());
		if (getSelectedSpiel() == null) {
			setSelectedSpiel(new Spiel());
		}
		Statusliste statusliste = getServices().saveSpiel(selectedSpiel);
		selectedSpiel.getBuchungen().forEach(buchung -> getServices().saveBuchung(buchung));
		showMessages(statusliste);
		completeSaldo = getServices().getCompleteSaldo();
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

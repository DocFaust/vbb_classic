package de.docfaust.vbb.jsfbeans;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.docfaust.vbb.data.entity.Buchung;
import de.docfaust.vbb.data.entity.Spiel;
import de.docfaust.vbb.data.entity.Spieler;
import de.docfaust.vbb.service.BuchungService;
import de.docfaust.vbb.service.SpielService;
import de.docfaust.vbb.service.SpielerService;
import de.docfaust.vbb.util.messages.MessageConstants;
import de.docfaust.vbb.util.messages.UIMessages;
import de.docfaust.vbb.util.statusliste.Statusliste;

/**
 * Managed Bean für die Funktionalität der Spiele Verwaltung.
 * 
 * @author xhu1011
 *
 */
@ViewScoped
@Named
public class SearchSpielBean extends AbstractJSFBean {
	private static final long serialVersionUID = 2715020733886030572L;

	@Inject
	private Logger logger;

	/**
	 * Liste aller Spiele.
	 */
	private List<Spiel> spiele = null;

	/**
	 * Ausgewähltes Spiel.
	 */
	private Spiel selectedSpiel;

	/**
	 * Liste aller Spieler.
	 */
	private List<Spieler> spieler = null;

	/**
	 * Ausgewählte Buchung.
	 */
	private Buchung selectedBuchung;

	@Inject
	private SpielerService spielerService;

	@Inject
	private SpielService spielService;

	@Inject
	private BuchungService buchungService;

	/**
	 * Konstruktor ohne EJB Kontext.
	 * 
	 * @param uiMessages UIMessages
	 */
	public SearchSpielBean(final UIMessages uiMessages) {
		super(uiMessages);
		logger = LoggerFactory.getLogger(getClass());
	}

	/**
	 * Konstruktor mit EJB Kontext.
	 * 
	 */
	public SearchSpielBean() {
		super();
	}

	/**
	 * Initialisiert die Felder mit den Werten aus der Datenbank.
	 */
	@PostConstruct
	public void init() {
		logger.debug("init called");
		spiele = spielService.getSpiele();
		spieler = spielerService.getSpieler();
		if (spiele.size() > 0) {
			selectedSpiel = spiele.get(0);
			List<Buchung> buchungen = selectedSpiel.getBuchungen();
			if (!buchungen.isEmpty()) {
				selectedBuchung = buchungen.get(0);
			}
		}
	}

	public Spiel getSelectedSpiel() {
		return selectedSpiel;
	}

	public void setSelectedSpiel(final Spiel selectedSpiel) {
		this.selectedSpiel = selectedSpiel;
	}

	public Buchung getSelectedBuchung() {
		return selectedBuchung;
	}

	public void setSelectedBuchung(final Buchung selectedBuchung) {
		this.selectedBuchung = selectedBuchung;
	}

	public List<Spiel> getSpiele() {
		return spiele;
	}

	public void setSpiele(final List<Spiel> spiele) {
		this.spiele = spiele;
	}

	public List<Spieler> getSpieler() {
		return spieler;
	}

	public void setSpieler(final List<Spieler> spieler) {
		this.spieler = spieler;
	}

	/**
	 * Speichert die ausgewählte Buchung.
	 */
	public void saveBuchung() {
		logger.debug("Speichere Buchung: " + ToStringBuilder.reflectionToString(selectedBuchung));
		if (selectedBuchung.getDescription() != null && selectedBuchung.getSpieler() != null
				&& selectedBuchung.getPrice() != null) {
			buchungService.saveBuchung(selectedBuchung);
			showUIMessage(MessageConstants.ENTRY_SAVED);
		} else {
			logger.warn("Keine Auswahl getätigt");
			showUIMessage(MessageConstants.NO_INPUT);
		}
	}

	/**
	 * Löscht das ausgewählte Spiel.
	 */
	public void deleteSpiel() {
		if (selectedSpiel != null) {
			logger.info("Lösche: " + selectedSpiel.toString());
			Statusliste statusliste = spielService.deleteSpiel(selectedSpiel);

			if (!statusliste.booleanValue()) {
				this.showMessages(statusliste);
			} else {
				showUIMessage(MessageConstants.GAME_DELETED);
				spiele.remove(selectedSpiel);
			}
		} else {
			logger.warn("Kein Spiel ausgewählt");
			showUIMessage(MessageConstants.NO_GAME_CHOSEN);
		}
	}

	/**
	 * Fügt eine neue Buchung hinzu.
	 */
	public void addBuchung() {
		logger.info("Lege neue Buchung an");
		// Gibt kein Spiel zu dem die Buchung angelegt werden kann
		if (selectedSpiel != null) {
			Buchung buchung = new Buchung();
			buchung.setDatum(selectedSpiel.getDatum());
			buchung.setSpiel(selectedSpiel);
			selectedBuchung = buchung;
		} else {
			showUIMessage(MessageConstants.NO_GAME_CHOSEN);
		}
	}

	/**
	 * Löscht die ausgewählte Buchung.
	 */
	public void deleteBuchung() {
		logger.info("Lösche ausgewählte Buchung: " + selectedBuchung);
		if (selectedBuchung != null) {
			selectedSpiel.getBuchungen().remove(selectedBuchung);
			// TODO Buchungservice
			buchungService.deleteBuchung(selectedBuchung);
			selectedBuchung = selectedSpiel.getBuchungen().get(0);
		} else {
			logger.warn("Keine Buchung ausgewählt");
			showUIMessage(MessageConstants.NO_BUCHUNG_SELECTED);
		}
	}

	/**
	 * liefert das Saldo des ausgewählten Spiels.
	 * 
	 * @return Saldobetrag
	 */
	public BigDecimal getSaldo() {
		BigDecimal saldo = BigDecimal.ZERO;
		if (selectedSpiel != null) {
			List<Buchung> buchungen = selectedSpiel.getBuchungen();
			saldo = buchungen.stream().map(Buchung::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
		}
		return saldo;
	}

	public boolean isNewGameDisabled() {
		return selectedSpiel == null;
	}

	/**
	 * Just for meeting conventions.
	 * 
	 * @param newGameDisabled nix
	 */
	public void setNewGameDisabled(final boolean newGameDisabled) {
	}
}

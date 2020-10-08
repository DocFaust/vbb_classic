package de.docfaust.vbb.jsfbeans;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.docfaust.vbb.data.entity.Spieler;
import de.docfaust.vbb.service.SpielerService;
import de.docfaust.vbb.util.messages.MessageConstants;
import de.docfaust.vbb.util.messages.UIMessages;
import de.docfaust.vbb.util.statusliste.Statusliste;

/**
 * Managed Bean für die Spielerverwaltung.
 * 
 * @author xhu1011
 *
 */
@ViewScoped
@Named
public class EditSpielerBean extends AbstractJSFBean {
	private static final long serialVersionUID = 7172540684751844438L;
	@Inject
	private Logger logger;

	@Inject
	private SpielerService spielerService;

	private Spieler selectedSpieler = null;
	private List<Spieler> spieler = null;

	/**
	 * Konstruktor ohne EJB Kontext.
	 * 
	 * @param uiMessages     UIMessages
	 * @param spielerService for JUnit
	 */
	public EditSpielerBean(final UIMessages uiMessages, final SpielerService spielerService) {
		super(uiMessages);
		this.spielerService = spielerService;
		logger = LoggerFactory.getLogger(getClass());
	}

	/**
	 * Konstruktor mit EJB Kontext.
	 * 
	 */
	public EditSpielerBean() {
		super();
	}

	/**
	 * Initialisiert das Bean mit Werten aus der Datenbank.
	 */
	@PostConstruct
	public void init() {
		setSpieler(spielerService.getSpieler());
		if (spieler != null && spieler.size() > 0) {
			setSelectedSpieler(spieler.get(0));
		}
	}

	/**
	 * Fügt einen Spieler hinzu.
	 */
	public void addSpieler() {
		selectedSpieler = new Spieler();
	}

	/**
	 * Speichert den ausgewählten Spieler.
	 */
	public void saveSpieler() {
		spielerService.saveSpieler(selectedSpieler);
		logger.info(selectedSpieler.getName() + " wurde erfolgreich gespeichert");
		showUIMessage(MessageConstants.PLAYER_SAVED);
		spieler.add(selectedSpieler);
	}

	/**
	 * Löscht den ausgewählten Spieler.
	 */
	public void deleteSpieler() {
		if (selectedSpieler == null) {
			showUIMessage("Kein Spieler gewählt", "Bitte markieren Sie einen zu Löschenden Spieler");
			return;
		}
		int idx = spieler.indexOf(selectedSpieler);
		Statusliste statusliste = spielerService.deleteSpieler(selectedSpieler);
		if (statusliste.booleanValue()) {
			spieler.remove(selectedSpieler);
			if (spieler != null && spieler.size() > 0) {
				// CHECKSTYLE.OFF: AvoidInlineConditionals
				selectedSpieler = spieler.get((--idx < 0 ? 0 : idx));
				// CHECKSTYLE.OFF
			}
		} else {
			showMessages(statusliste);
		}
	}

	public Spieler getSelectedSpieler() {
		return selectedSpieler;
	}

	public void setSelectedSpieler(final Spieler selectedSpieler) {
		this.selectedSpieler = selectedSpieler;
	}

	public List<Spieler> getSpieler() {
		return spieler;
	}

	public void setSpieler(final List<Spieler> spieler) {
		this.spieler = spieler;
	}
}

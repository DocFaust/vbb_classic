package de.docfaust.vbb.jsfbeans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.docfaust.vbb.data.entity.Spieler;
import de.docfaust.vbb.service.VBBServices;
import de.docfaust.vbb.util.messages.MessageConstants;
import de.docfaust.vbb.util.messages.UIMessages;
import de.docfaust.vbb.util.statusliste.Statusliste;

/**
 * JSFBean für die Spielbearbeitung.
 * 
 * @author xhu1011
 *
 */
@ViewScoped
@Named
public class SpielBean extends AbstractJSFBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6000859361366366829L;

	@Inject
	private Logger logger;

	private Date datum = new Date();

	private List<Spieler> spielerList = new ArrayList<>();

	/**
	 * Konstruktor ohne EJB Kontext.
	 * 
	 * @param services
	 *            Services
	 * @param uiMessages
	 *            UIMessages
	 */
	public SpielBean(final VBBServices services, final UIMessages uiMessages) {
		super(services, uiMessages);
		logger = LoggerFactory.getLogger(getClass());
		init();
	}

	/**
	 * Konstruktor mit EJB Kontext.
	 * 
	 */
	public SpielBean() {
		super();
	}


	/**
	 * Befüllt die Spielerliste.
	 */
	@PostConstruct
	public void init() {
		this.setSpielerList(getServices().getSpielerModelList());
	}

	public Date getDatum() {
		return datum;
	}

	public void setDatum(final Date datum) {
		this.datum = datum;

	}

	/**
	 * Gibt die Liste der Spieler zurück.
	 * 
	 * @return Liste der Spieler
	 */
	public List<Spieler> getSpielerList() {
		return this.spielerList;
	}

	public void setSpielerList(final List<Spieler> spielerList) {
		this.spielerList = spielerList;
	}

	private boolean checkSpielerList() {
		logger.info(spielerList.toString());
		
		long count = spielerList.stream().filter(Spieler::isBezahlt).count();
		
		if (count > 1) {
			logger.warn("Mehr als ein bezahlender Spieler");
			showUIMessage(MessageConstants.GAME_MORE_PAYERS);
			return false;
		} else if (count == 0) {
			logger.warn("Kein Spieler hat bezahlt");
			showUIMessage(MessageConstants.GAME_NO_PAYER);
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Speichert ein Spiel.
	 */
	public void saveSpiel() {
		logger.info("Das Spiel wird gespeichert");
		if (checkSpielerList()) {
			Statusliste statusliste = getServices().saveSpiel(this.spielerList, getDatum());
			if (!statusliste.booleanValue()) {
				statusliste.forEach(status -> showUIMessage(status.getCode()));
			} else {
				showUIMessage(MessageConstants.GAME_SAVED);
			}
		}
	}

	public List<Entry<String, BigDecimal>> getSaldo() {
		return getServices().getSaldo();
	}

	public BigDecimal getCompleteSaldo() {
		return getServices().getCompleteSaldo();
	}
}

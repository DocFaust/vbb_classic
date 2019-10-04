package de.docfaust.vbb.jsfbeans;

import java.util.Date;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.docfaust.vbb.service.CutOffService;
import de.docfaust.vbb.util.messages.MessageConstants;
import de.docfaust.vbb.util.messages.UIMessages;

/**
 * JSFBean für den Buchungsschnitt.
 * 
 * @author xhu1011
 *
 */
@ViewScoped
@Named
public class ReorgDatabaseBean extends AbstractJSFBean {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5112060097524596736L;

	@Inject
	private CutOffService cutOffService;

	@Inject
	private Logger logger;

	private Date selectedDatum = new Date();

	/**
	 * Konstruktor ohne EJB Kontext.
	 * 
	 * @param uiMessages UIMessages
	 * @param cutOffService for JUnit
	 */
	public ReorgDatabaseBean(final UIMessages uiMessages, final CutOffService cutOffService) {
		super(uiMessages);
		this.cutOffService = cutOffService;
		logger = LoggerFactory.getLogger(getClass());
	}

	/**
	 * Konstruktor mit EJB Kontext.
	 * 
	 */
	public ReorgDatabaseBean() {
		super();
	}

	/**
	 * Startet den Buchungsschnitt. Wenn alles ohne Exception durchgelaufen ist,
	 * wird eine Meldung ausgegeben.
	 */
	public void starteBuchungsschnitt() {
		logger.debug("Starte den Buchungsschnitt");
		try {
			cutOffService.starteBuchungsschnitt(getSelectedDatum());
			showUIMessage(MessageConstants.REORG_SUCCESSFUL, getSelectedDatum());
		} catch (Exception e) {
			logger.error("Es ist ein Fehler beim Buchungsschnitt aufgetreten", e);
			showUIMessage(MessageConstants.REORG_ERROR, e.getMessage());
		}
	}

	public Date getSelectedDatum() {
		return selectedDatum;
	}

	public void setSelectedDatum(final Date selectedDatum) {
		this.selectedDatum = selectedDatum;
	}

}

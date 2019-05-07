package de.docfaust.vbb.jsfbeans;

import java.io.Serializable;

import javax.inject.Inject;

import de.docfaust.vbb.service.VBBServices;
import de.docfaust.vbb.util.messages.MessageConstants;
import de.docfaust.vbb.util.messages.UIMessages;
import de.docfaust.vbb.util.messages.annotations.JAXBMessages;
import de.docfaust.vbb.util.statusliste.Status;
import de.docfaust.vbb.util.statusliste.Statusliste;

/**
 * Abstraktes JSFBean mit den StandardKlassen, die jedes Bean braucht.
 * 
 * @author Werner
 *
 */
public abstract class AbstractJSFBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7390205631588081273L;
	@Inject
	private VBBServices services;
	@Inject
	@JAXBMessages
	private UIMessages uiMessages;

	
	/**
	 * Konstruktor ohne EJB Kontext.
	 * @param services VBBServices
	 * @param uiMessages UIMessages
	 */
	public AbstractJSFBean(final VBBServices services, final UIMessages uiMessages) {
		super();
		this.services = services;
		this.uiMessages = uiMessages;
	}

	/**
	 * Konstruktor für EJB Kontext.
	 */
	public AbstractJSFBean() {
		super();
	}

	public VBBServices getServices() {
		return services;
	}

	private UIMessages getUiMessages() {
		return uiMessages;
	}

	/**
	 * Zeigt eine Nachricht im Growl.
	 * 
	 * @param code
	 *            Code der nachricht
	 */
	public void showUIMessage(final MessageConstants code) {
		getUiMessages().showMessage(code);
	}

	/**
	 * Zeigt eine Nachricht im Growl.
	 * 
	 * @param summary
	 *            Zusammenfassung
	 * @param detail
	 *            Detailnachricht
	 * 
	 */
	public void showUIMessage(final String summary, final String detail) {
		getUiMessages().showErrorMessage(summary, detail);
	}

	/**
	 * Zeigt eine Message im Growl.
	 * 
	 * @param code
	 *            Message Code
	 * @param message
	 *            Message
	 */
	public void showUIMessage(final MessageConstants code, final Object... message) {
		getUiMessages().showMessage(code, message);
	}

	/**
	 * Macht aus einer Statusliste Messages, die per faces angezeigt werden.
	 * 
	 * @param statusliste
	 *            Statusliste mit Meldungen
	 */
	public void showMessages(final Statusliste statusliste) {
		for (Status status : statusliste) {
			uiMessages.showMessage(status.getCode(), status.getZusatzInfo());
		}
	}
}

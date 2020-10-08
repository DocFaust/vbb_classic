package de.docfaust.vbb.util.messages;

import javax.faces.application.FacesMessage.Severity;

import de.docfaust.vbb.util.messages.impl.MessageData;

/**
 * Message Interface. Interface für da Anzeigen von Nachrichten in der UI.
 * 
 * @author xhu1011
 *
 */
public interface UIMessages {
	/**
	 * Zeigt eine Nachricht an.
	 * 
	 * @param severity
	 *            Schwere
	 * @param summary
	 *            Zusammenfassung Titelzeile
	 * @param detail
	 *            Detailmeldung
	 */
	void showMessage(Severity severity, String summary, String detail);

	/**
	 * Zeigt eine Fehler Nachricht an.
	 * 
	 * @param summary
	 *            Zusammenfassung Titelzeile
	 * @param detail
	 *            Detailmeldung
	 */
	void showErrorMessage(String summary, String detail);

	/**
	 * Zeigt eine Info Nachricht an.
	 * 
	 * @param summary
	 *            Zusammenfassung Titelzeile
	 * @param detail
	 *            Detailmeldung
	 */
	void showInfoMessage(String summary, String detail);

	/**
	 * Zeigt eine Warnung Nachricht an.
	 * 
	 * @param summary
	 *            Zusammenfassung Titelzeile
	 * @param detail
	 *            Detailmeldung
	 */
	void showWarnMessage(String summary, String detail);

	/**
	 * Zeigt eine Schwerer Fehler Nachricht an.
	 * 
	 * @param summary
	 *            Zusammenfassung Titelzeile
	 * @param detail
	 *            Detailmeldung
	 */
	void showFatalMessage(String summary, String detail);

	/**
	 * Zeigt eine Nachricht an.
	 * 
	 * @param code
	 *            Code des Fehlers
	 * 
	 */
	void showMessage(String code);

	/**
	 * Zeigt eine Nachricht an.
	 * 
	 * @param code
	 *            Code des Fehlers
	 * @param obj
	 *            Objekte, die in die Nachricht eingefügt werden
	 */
	void showMessage(String code, Object... obj);

	/**
	 * Zeigt eine Nachricht an.
	 * 
	 * @param code
	 *            Code des Fehlers
	 * 
	 */
	void showMessage(MessageConstants code);

	/**
	 * Zeigt eine Nachricht an.
	 * 
	 * @param code
	 *            Code des Fehlers
	 * @param obj
	 *            Objekte, die in die Nachricht eingefügt werden
	 * 
	 */
	void showMessage(MessageConstants code, Object... obj);

	/**
	 * Gibt die Messagedaten zu einem Code zurück.
	 * 
	 * @param code
	 *            Code des Fehlers
	 * @return Daten zu dem Fehler
	 */
	MessageData getMessageData(MessageConstants code);
}
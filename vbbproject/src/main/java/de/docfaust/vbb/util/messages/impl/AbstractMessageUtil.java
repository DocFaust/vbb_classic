package de.docfaust.vbb.util.messages.impl;

import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;

import org.slf4j.Logger;

import de.docfaust.vbb.util.messages.MessageConstants;
import de.docfaust.vbb.util.messages.UIMessages;

/**
 * Abstrakte Implementierung der UI Messages.
 * 
 * @author xhu1011
 *
 */
public abstract class AbstractMessageUtil implements UIMessages {
	private Map<String, MessageData> data = null;

	/**
	 * Methode zum Lesen der Messages. Muss von der konkreten Klasse
	 * implementiert werden.
	 * 
	 * @return Map mit Code als Key und MessageData als Value
	 */
	protected abstract Map<String, MessageData> readMessages();

	/**
	 * Gibt den konkreten Logger zurück.
	 * 
	 * @return Logger
	 */
	protected abstract Logger getLogger();

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void showMessage(final Severity severity, final String summary, final String detail) {
		getLogger().debug(String.format("Zeige Message: Severity: %s Summary: %s Detail: %s", severity.toString(), summary, detail));
		FacesMessage message = new FacesMessage(severity, summary, detail);
		FacesContext context = FacesContext.getCurrentInstance();
		if (context != null) {
			context.addMessage(null, message);
		} else {
			getLogger().warn("Kein FacesContext vorhanden!");
			getLogger().info(String.format("Message: Severity: %s Summary: %s Detail: %s", severity.toString(), summary, detail));
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void showErrorMessage(final String summary, final String detail) {
		showMessage(FacesMessage.SEVERITY_ERROR, summary, detail);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void showInfoMessage(final String summary, final String detail) {
		showMessage(FacesMessage.SEVERITY_INFO, summary, detail);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void showWarnMessage(final String summary, final String detail) {
		showMessage(FacesMessage.SEVERITY_WARN, summary, detail);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void showFatalMessage(final String summary, final String detail) {
		showMessage(FacesMessage.SEVERITY_FATAL, summary, detail);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void showMessage(final String code) {
		MessageData ndata = getMessageData(code);
		showMessage(getSeverity(ndata.getSeverity()), ndata.getSummary(), ndata.getDetail());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void showMessage(final String code, final Object... obj) {
		MessageData ndata = getMessageData(code);
		showMessage(getSeverity(ndata.getSeverity()), ndata.getSummary(), String.format(ndata.getDetail(), obj));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void showMessage(final MessageConstants code) {
		showMessage(code.toString());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void showMessage(final MessageConstants code, final Object... obj) {
		showMessage(code.toString(), obj);
	}

	@Override
	public MessageData getMessageData(final MessageConstants code) {
		return getMessageData(code.toString());
	}

	private MessageData getMessageData(final String code) {
		if (data == null) {
			data = readMessages();
		}
		MessageData mData = data.get(code);
		if (mData == null) {
			mData = new MessageData();
			mData.setSeverity("ERROR");
			mData.setSummary("Unbekannte Meldung");
			mData.setDetail("Die Meldung " + code + " ist unbekannt!");
		}
		return mData;
	}

	private Severity getSeverity(final String severity) {
		Severity sev = null;
		switch (severity) {
		case "INFO":
			sev = FacesMessage.SEVERITY_INFO;
			break;
		case "WARN":
			sev = FacesMessage.SEVERITY_WARN;
			break;
		case "FATAL":
			sev = FacesMessage.SEVERITY_FATAL;
			break;
		case "ERROR":
			sev = FacesMessage.SEVERITY_ERROR;
			break;
		default:
			sev = FacesMessage.SEVERITY_INFO;
			break;
		}
		return sev;
	}
}

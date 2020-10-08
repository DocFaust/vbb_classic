package de.docfaust.vbb.util;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;

import org.slf4j.LoggerFactory;

import de.docfaust.vbb.util.messages.MessageConstants;
import de.docfaust.vbb.util.messages.UIMessages;
import de.docfaust.vbb.util.messages.impl.MessageData;

public class UIMessagesTestImpl implements UIMessages {

	@Override
	public void showMessage(Severity severity, String summary, String detail) {
		LoggerFactory.getLogger(getClass()).info("Severity: " + severity.toString());
		LoggerFactory.getLogger(getClass()).info("Summary:  " + summary);
		LoggerFactory.getLogger(getClass()).info("Detail:   " + detail);

	}

	@Override
	public void showErrorMessage(String summary, String detail) {
		showMessage(FacesMessage.SEVERITY_ERROR, summary, detail);

	}

	@Override
	public void showInfoMessage(String summary, String detail) {
		showMessage(FacesMessage.SEVERITY_INFO, summary, detail);

	}

	@Override
	public void showWarnMessage(String summary, String detail) {
		showMessage(FacesMessage.SEVERITY_WARN, summary, detail);

	}

	@Override
	public void showFatalMessage(String summary, String detail) {
		showMessage(FacesMessage.SEVERITY_FATAL, summary, detail);

	}

	@Override
	public void showMessage(String code) {
		LoggerFactory.getLogger(getClass()).info("Code: " + code);

	}

	@Override
	public void showMessage(String code, Object... obj) {
		LoggerFactory.getLogger(getClass()).info("Code: " + code);
		for (Object object : obj) {
			LoggerFactory.getLogger(getClass()).info("zusatz: " + object);
		}

	}

	@Override
	public void showMessage(MessageConstants code) {
		LoggerFactory.getLogger(getClass()).info("Code: " + code.toString());
	}

	@Override
	public void showMessage(MessageConstants code, Object... obj) {
		LoggerFactory.getLogger(getClass()).info("Code: " + code.toString());
		for (Object object : obj) {
			LoggerFactory.getLogger(getClass()).info("zusatz: " + object);
		}

	}

	@Override
	public MessageData getMessageData(MessageConstants code) {
		return null;
	}

}

package de.docfaust.vbb.util;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.lang.reflect.Method;

import javax.faces.application.FacesMessage.Severity;

import org.junit.Test;
import org.slf4j.LoggerFactory;

import de.docfaust.vbb.util.messages.MessageConstants;
import de.docfaust.vbb.util.messages.impl.AbstractMessageUtil;
import de.docfaust.vbb.util.messages.impl.MessageData;
import de.docfaust.vbb.util.messages.impl.MessageUtilJAXB;

public class TestUIMessages {

	@Test
	public void testUIMessages() throws Exception {
		Class<AbstractMessageUtil> clazz = AbstractMessageUtil.class;
		Method m = clazz.getDeclaredMethod("getMessageData", String.class);
		m.setAccessible(true);

		MessageUtilJAXB mu = new MessageUtilJAXB("messages.xml", "messages.xsd");

		MessageData md = (MessageData) m.invoke(mu, "bubu");

		assertNotNull(md);
	}

	@Test
	public void testUIMessagesJAXB() throws Exception {
		Class<AbstractMessageUtil> clazz = AbstractMessageUtil.class;
		Method m = clazz.getDeclaredMethod("getMessageData", String.class);
		m.setAccessible(true);

		MessageUtilJAXB mu = new MessageUtilJAXB("messages.xml", "messages.xsd");

		MessageData md = (MessageData) m.invoke(mu, "bubu");

		assertNotNull(md);
	}

	@Test
	public void testUIMessagesSuccessfulJAXB() throws Exception {
		Class<AbstractMessageUtil> clazz = AbstractMessageUtil.class;
		Method m = clazz.getDeclaredMethod("getMessageData", String.class);
		m.setAccessible(true);

		MessageUtilJAXB mu = new MessageUtilJAXB("messages.xml", "messages.xsd");

		MessageData md = (MessageData) m.invoke(mu, MessageConstants.ALREADYREGISTERED.toString());

		assertNotNull(md);

		assertEquals("ALREADYREGISTERED", md.getCode());
		assertEquals("Der User ist bereits registriert", md.getDetail());
		assertEquals("ERROR", md.getSeverity());
		assertEquals("Registrierung fehlerhaft", md.getSummary());
	}

	@Test
	public void testMessage() {
		MessageUtilJAXB mu = new MessageUtilJAXB("messages.xml", "messages.xsd") {

			private static final long serialVersionUID = -2653537252023389218L;

			public void showMessage(final Severity severity, final String summary, final String detail) {
				LoggerFactory.getLogger(getClass()).info("Severity: {}, summary: {}, Detail: {}", severity.toString(), summary, detail);
			}
		};

		mu.showErrorMessage("Fehler", "Fehler");
		mu.showFatalMessage("Fatal", "Fataler Fehler");
		mu.showInfoMessage("Info", "Informative Nachricht");
		mu.showWarnMessage("Warnung", "Ich wanr Dich");
		mu.showMessage(MessageConstants.GAME_DELETED);
		mu.showMessage(MessageConstants.EMAIL_EXISTS, "xyz@abc.de");
		mu.showMessage(MessageConstants.EMAIL_EXISTS, "xyz@abc.de");
		mu.showMessage(MessageConstants.EMAIL_EXISTS, "xyz@abc.de");
		mu.showMessage("USERID_EXISTS", "ahuber");

		MessageData data = mu.getMessageData(MessageConstants.EMAIL_EXISTS);
		assertThat(data.getCode(), equalTo("EMAIL_EXISTS"));
		assertThat(data.getSeverity(), equalTo("ERROR"));
		assertThat(data.getDetail(), equalTo("Die Emailadresse %s ist bereits registriert"));
		assertThat(data.getSummary(), equalTo("Email vorhanden"));
	}

	@Test
	public void testRealMessage() {
		MessageUtilJAXB mu = new MessageUtilJAXB("messages.xml", "messages.xsd");

		mu.showMessage(MessageConstants.EMAIL_EXISTS, "xyz@abc.de");
	}
	
	@Test
	public void test() {
		assertThat(new MessageUtilJAXB(), not(nullValue()));
	}
	@Test
	public void testNoXSD() {
		MessageUtilJAXB mu = new MessageUtilJAXB("messages.xml", null);
		mu.showMessage(MessageConstants.EMAIL_EXISTS, "xyz@abc.de");
	}
	@Test
	public void testNoXML() {
		MessageUtilJAXB mu = new MessageUtilJAXB(null, null);
		mu.showMessage(MessageConstants.EMAIL_EXISTS, "xyz@abc.de");
	}
	@Test
	public void testFATAL() {
		MessageUtilJAXB mu = new MessageUtilJAXB("messages_test.xml", "messages.xsd");
		mu.showMessage("FATAL");
	}
	@Test
	public void testWARN() {
		MessageUtilJAXB mu = new MessageUtilJAXB("messages_test.xml", "messages.xsd");
		mu.showMessage("WARN");
	}
}

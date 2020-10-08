package de.docfaust.vbb.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Properties;

import javax.mail.PasswordAuthentication;
import javax.mail.Session;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import de.docfaust.vbb.data.entity.Mail;
import de.docfaust.vbb.data.facades.MailFacade;
import de.docfaust.vbb.data.util.JpaBaseRolledBackTestCase;

public class MailSenderTest extends JpaBaseRolledBackTestCase {

	private static Session session;
	public static final String SMTP_MAIL_HOST = "mail.smtp.host";
	public static final String MAIL_SMTP_AUTH = "mail.smtp.auth";
	public static final String MAIL_SMTP_STARTTLS_ENABLE = "mail.smtp.starttls.enable";
	public static final String MAIL_SMTP_PORT = "mail.smtp.port";
	public static final String SENDER_ADDRESS = "sender.address";
	public static final String SENDER_PASSWORD = "sender.password";
	public static final String SUBJECT = "subject";
	public static final String TEXT = "text";
	public static final String MAIL_SMTP_TIMEOUT = "mail.smtp.timeout";
	public static final String MAIL_SMTP_CONNECTIONTIMEOUT = "mail.smtp.connectiontimeout";

	@BeforeAll
	public static void initSession() {
		Properties props = new Properties();
		props.put("mail.debug", "true");
		props.put(SMTP_MAIL_HOST, "smtp.gmail.com");
		props.put(MAIL_SMTP_AUTH, true);
		props.put(MAIL_SMTP_STARTTLS_ENABLE, true);
		props.put(MAIL_SMTP_PORT, 25);
		props.put(MAIL_SMTP_CONNECTIONTIMEOUT, 3000);
		props.put(MAIL_SMTP_TIMEOUT, 3000);

		session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(facadenFactory.getConfigFacade().getValue(SENDER_ADDRESS), facadenFactory.getConfigFacade().getValue(
						SENDER_PASSWORD));
			}
		});
	}

	@Test
	public void testSendMailsNoMail() {
		MailSender sender = new MailSender(em, session);
		sender.sendMails();
	}

	@Test
	public void testSendMails() {
		Mail m = new Mail();
		m.setAttempt(0);
		m.setRecipient("info@docfaust.de");
		m.setSubject("Subject");
		m.setText("text");
		MailFacade mailFacade = facadenFactory.getMailFacade();
		mailFacade.create(m);

		assertThat(m.getAttempt()).isEqualTo(0);
		MailSender sender = new MailSender(em, session);
		sender.sendMails();
		// Assert.assertEquals(0, mailFacade.findAll().size());
	}

	// FIXME Repair
//	@Test
//	public void testSendSpielMails() {
//		VBBServices services = new VBBServices(em);
//		Spiel spiel = facadenFactory.getSpielFacade().find(1);
//		services.sendSpielMail(spiel);
//
//
//		MailSender sender = new MailSender(em, session);
//		sender.sendMails();
//		// Assert.assertEquals(0, mailFacade.findAll().size());
//	}
	
	@Test
	public void test() {
		assertThat(new MailSender()).isNotNull();
	}
}

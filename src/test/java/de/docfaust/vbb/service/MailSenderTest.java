package de.docfaust.vbb.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;

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
	private static int sentMessageCount;

	@BeforeAll
	public static void initSession() {
		Properties props = new Properties();
		session = Session.getInstance(props);
	}

	@Test
	public void testSendMailsNoMail() {
		clearMails();
		sentMessageCount = 0;
		MailSender sender = new TestMailSender(em, session);
		sender.sendMails();
		assertThat(sentMessageCount).isEqualTo(0);
	}

	@Test
	public void testSendMails() {
		clearMails();
		Mail m = new Mail();
		m.setAttempt(0);
		m.setRecipient("info@docfaust.de");
		m.setSubject("Subject");
		m.setText("text");
		MailFacade mailFacade = facadenFactory.getMailFacade();
		mailFacade.create(m);

		assertThat(m.getAttempt()).isEqualTo(0);
		sentMessageCount = 0;
		MailSender sender = new TestMailSender(em, session);
		sender.sendMails();
		assertThat(sentMessageCount).isEqualTo(1);
		assertThat(mailFacade.findAll()).isEmpty();
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

	private void clearMails() {
		MailFacade mailFacade = facadenFactory.getMailFacade();
		mailFacade.findAll().forEach(mailFacade::remove);
	}

	private static class TestMailSender extends MailSender {

		TestMailSender(javax.persistence.EntityManager em, Session session) {
			super(em, session);
		}

		@Override
		protected void sendMessage(final MimeMessage message) throws MessagingException {
			sentMessageCount++;
			assertThat(message.getFrom()).isNotNull();
			assertThat(message.getRecipients(Message.RecipientType.TO)).isNotNull();
		}
	}
}

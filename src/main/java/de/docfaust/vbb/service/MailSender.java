package de.docfaust.vbb.service;

import java.util.List;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.docfaust.vbb.data.entity.Mail;
import de.docfaust.vbb.data.facades.MailFacade;
import de.docfaust.vbb.util.configuration.MailConfiguration;
import de.docfaust.vbb.util.configuration.MailConfigurationDB;
import de.docfaust.vbb.util.configuration.MailConfigurationDBImpl;

/**
 * Scheduled Task um persistierte Mails zu verschicken.
 * 
 * @author xhu1011
 *
 */
@Singleton
@Startup
public class MailSender {
	private static final int MAX_ATTEMPTS = 10;
	@EJB
	private MailFacade mailFacade;
	@Inject
	@MailConfigurationDB
	private MailConfiguration mailConfig;
	@Inject
	private Logger logger;

	@Resource(name = "java:/mail/DocFaust")
	private Session session;

	/**
	 * Leerer Default Konstruktor.
	 */
	public MailSender() {
	}

	/**
	 * Konstruktor für Aufruf ohne EJB Context.
	 * 
	 * @param em
	 *            Entitymanager
	 * @param session
	 *            JavaMail Session
	 */
	public MailSender(final EntityManager em, final Session session) {
		this.mailFacade = new MailFacade(em);
		this.mailConfig = new MailConfigurationDBImpl(em);
		this.logger = LoggerFactory.getLogger(getClass());
		this.session = session;
	}

	/**
	 * Versendet alle gespeicherten Mails.
	 */
	@Schedule(hour = "*", minute = "*/1", persistent = false)
	public void sendMails() {
		logger.info("Starte Mailscheduler");
		if (session != null) {
			List<Mail> mails = mailFacade.findAll();
			mails.stream().filter(mail -> mail.getAttempt() < MAX_ATTEMPTS).forEach(mail -> sendMail(mail));
		} else {
			logger.warn("Keine Session definiert! Mails können nicht versendet werden.");
		}
	}

	private void sendMail(final Mail mail) {
		logger.info("Versende Mail an " + mail.getRecipient());
		try {
			MimeMessage message = new MimeMessage(session);
			InternetAddress from = new InternetAddress(mailConfig.getSenderAddress());
			message.setFrom(from);
			InternetAddress to = new InternetAddress(mail.getRecipient());
			message.addRecipient(Message.RecipientType.TO, to);
			message.setSubject(mail.getSubject());
			String text = mail.getText();
			logger.debug("Sending: " + text);
			message.setText(text, "UTF-8", "html");
			logger.debug("Session Properties: " + session.getProperties());
			Transport.send(message);
			mailFacade.remove(mail);
		} catch (MessagingException e) {
			logger.error("Fehler beim Versenden der Email", e);
			mail.setAttempt(mail.getAttempt() + 1);
			mailFacade.edit(mail);
		}
	}
}

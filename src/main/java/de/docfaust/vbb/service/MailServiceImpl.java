package de.docfaust.vbb.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.docfaust.vbb.data.entity.Buchung;
import de.docfaust.vbb.data.entity.Mail;
import de.docfaust.vbb.data.entity.Spiel;
import de.docfaust.vbb.data.entity.User;
import de.docfaust.vbb.data.facades.MailFacade;
import de.docfaust.vbb.util.templates.MailTemplates;
import de.docfaust.vbb.util.templates.VelocityMailTemplate;

/**
 * Implementation of the MailService.
 * @author wfa339
 *
 */
@Dependent
public class MailServiceImpl implements MailService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2026627644929892693L;

	@EJB
	private MailFacade mailFacade;

	@Inject
	private Logger logger;

	@Inject
	private SaldoService saldoService;

	@Inject
	private ConfigService configService;

	@Inject
	@VelocityMailTemplate
	private MailTemplates mailTemplates;

	/**
	 * @param mailFacade from JUnit
	 * @param saldoService from JUnit
	 * @param configService from JUnit
	 * @param mailTemplates from JUnit
	 */
	public MailServiceImpl(final MailFacade mailFacade, final SaldoService saldoService, final ConfigService configService,
			final MailTemplates mailTemplates) {
		super();
		this.mailFacade = mailFacade;
		this.saldoService = saldoService;
		this.configService = configService;
		this.mailTemplates = mailTemplates;
		this.logger = LoggerFactory.getLogger(getClass());
	}

	/**
	 * 
	 */
	public MailServiceImpl() {
		super();
	}

	/**
	 * Sendet eine Mail an alle Spieler, die am Spiel teilgenommen haben, um über
	 * den aktuellen Status zu informieren.
	 * 
	 * @param spiel Spiel über das informiert werden soll.
	 */
	@Override
	public void sendSpielMail(final Spiel spiel) {

		Map<String, String> emails = new Hashtable<>();
		List<String> spielerList = new ArrayList<>();
		for (Buchung buchung : spiel.getBuchungen()) {
			String email = buchung.getSpieler().getEmail();
			// Keine Doppelversendung und es muss eine Mailadresse vorhanden
			// sein
			if (StringUtils.isEmpty(email) || spielerList.contains(email)) {
				continue;
			} else {
				spielerList.add(email);
			}

			String htmlMail = mailTemplates.getSaldoMail(spiel, buchung.getSpieler().getName(), saldoService.getSaldo());
			emails.put(email, htmlMail);
			Mail mail = new Mail();
			mail.setRecipient(email);
			mail.setSubject("Saldo des Spiels vom " + new SimpleDateFormat("dd.MM.yyyy").format(spiel.getDatum()));
			mail.setText(htmlMail);
			mailFacade.create(mail);
		}
	}

	/**
	 * Speichert die Registrierungsmail in der Mail Tabelle, wo sie vom MailSender
	 * abgeholt wird.
	 * 
	 * @see MailSender
	 * @param user Benutzer, dem die Mail geschickt werden soll.
	 */
	@Override
	public final void sendRegistrationMail(final User user) {
		Mail mail = new Mail();
		mail.setRecipient(user.getEmail());
		mail.setSubject(configService.getMailConfig().getRegistrationSubject());
		String text = mailTemplates.getRegisterMail(user, configService.getMailConfig().getDomain());
		logger.debug(text);
		mail.setText(text);

		mailFacade.create(mail);

	}

}

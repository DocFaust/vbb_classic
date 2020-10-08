package de.docfaust.vbb.jsfbeans;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.docfaust.vbb.service.ConfigService;
import de.docfaust.vbb.util.messages.MessageConstants;
import de.docfaust.vbb.util.messages.UIMessages;

/**
 * Bean zur Verwaltung des Benutzerprofils.
 * 
 * @author xhu1011
 *
 */
@ViewScoped
@Named
public class EditConfigBean extends AbstractJSFBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6159834826207911500L;

	@Inject
	private Logger logger;

	@Inject
	private ConfigService configService;

	private String domain;
	private String subject;
	private String from;

	/**
	 * Konstruktor ohne EJB Kontext.
	 * 
	 * @param uiMessages    UIMessages
	 * @param configService for JUnit
	 */
	public EditConfigBean(final UIMessages uiMessages, final ConfigService configService) {
		super(uiMessages);
		this.configService = configService;
		logger = LoggerFactory.getLogger(getClass());
	}

	/**
	 * Konstruktor mit EJB Kontext.
	 * 
	 */
	public EditConfigBean() {
		super();
	}

	/**
	 * Lädt die aktuelle Config.
	 */
	@PostConstruct
	public void init() {
		domain = configService.getMailConfig().getDomain();
		subject = configService.getMailConfig().getRegistrationSubject();
		from = configService.getMailConfig().getSenderAddress();
	}

	/**
	 * Setzt die UI zurück.
	 */
	public void reset() {
		init();
		showUIMessage(MessageConstants.CONFIG_RESET);
	}

	/**
	 * Speichert die neuen Werte.
	 */
	public void save() {
		logger.debug("Speichere Werte.");
		configService.getMailConfig().setDomain(domain);
		configService.getMailConfig().setRegistrationSubject(subject);
		configService.getMailConfig().setSenderaddress(from);
		showUIMessage(MessageConstants.CONFIG_SAVED);
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(final String domain) {
		this.domain = domain;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(final String subject) {
		this.subject = subject;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(final String from) {
		this.from = from;
	}

}

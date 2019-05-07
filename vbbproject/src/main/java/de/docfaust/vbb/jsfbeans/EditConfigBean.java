package de.docfaust.vbb.jsfbeans;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.docfaust.vbb.service.VBBServices;
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
	
	private String domain;
	private String subject;
	private String from;

	/**
	 * Konstruktor ohne EJB Kontext.
	 * 
	 * @param services
	 *            Services
	 * @param uiMessages
	 *            UIMessages
	 */
	public EditConfigBean(final VBBServices services, final UIMessages uiMessages) {
		super(services, uiMessages);
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
		domain = getServices().getMailConfig().getDomain();
		subject = getServices().getMailConfig().getRegistrationSubject();
		from = getServices().getMailConfig().getSenderAddress();
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
		getServices().getMailConfig().setDomain(domain);
		getServices().getMailConfig().setRegistrationSubject(subject);
		getServices().getMailConfig().setSenderaddress(from);
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

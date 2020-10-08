package de.docfaust.vbb.util.configuration;

import javax.ejb.EJB;
import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;

import de.docfaust.vbb.data.entity.Config;
import de.docfaust.vbb.data.facades.ConfigFacade;

/**
 * Implementierung Der MailCOnfiguration über eine Datenbank.
 * @author xhu1011
 */
@Dependent
@MailConfigurationDB
public class MailConfigurationDBImpl implements MailConfiguration {

	/**
	 * Konstante für die Absenderadresse.
	 */
	private static final String SENDER_ADDRESS = "sender.address";

	/**
	 * Konstante für das Subject.
	 */
	private static final String SUBJECT = "subject";

	/**
	 * Konstante für die Domäne.
	 */
	private static final String DOMAIN = "domain";

	@EJB
	private ConfigFacade facade;

	/**
	 * Konstruktor ohne EJB Context.
	 * 
	 * @param em
	 *            EntityManager
	 */
	public MailConfigurationDBImpl(final EntityManager em) {
		facade = new ConfigFacade(em);
	}

	/**
	 * Konstruktor mit EJB Context.
	 */
	public MailConfigurationDBImpl() {
		super();
	}

	@Override
	public String getSenderAddress() {
		return getString(SENDER_ADDRESS);
	}

	@Override
	public void setSenderaddress(final String address) {
		setString(SENDER_ADDRESS, address);
	}

	@Override
	public String getDomain() {
		return getString(DOMAIN);
	}

	@Override
	public void setDomain(final String domain) {
		setString(DOMAIN, domain);
	}

	@Override
	public String getRegistrationSubject() {
		return getString(SUBJECT);
	}

	@Override
	public void setRegistrationSubject(final String subject) {
		setString(SUBJECT, subject);

	}

	private String getString(final String key) {
		return facade.getValue(key);
	}

	private void setString(final String key, final String value) {

		Config c = facade.findByKey(key);
		if (c == null) {
			Config config = new Config();
			config.setConfigkey(key);
			config.setConfigvalue(value);
			facade.create(config);
		} else {
			c.setConfigvalue(value);
			facade.edit(c);
		}
	}
}

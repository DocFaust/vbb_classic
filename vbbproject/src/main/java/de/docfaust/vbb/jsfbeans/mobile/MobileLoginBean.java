package de.docfaust.vbb.jsfbeans.mobile;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.docfaust.vbb.jsfbeans.LoginBean;
import de.docfaust.vbb.service.VBBServices;
import de.docfaust.vbb.util.messages.UIMessages;

/**
 * Bean zum Mobilen Login.
 * 
 * @author xhu1011
 *
 */
@Named
@SessionScoped
public class MobileLoginBean extends LoginBean {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3420290712412451442L;

	private static final String INDEX2 = "index2";

	private static final String SUMMARY = "summary";
	
	private static final String INDEX = "index";

	@Inject
	private Logger logger;

	/**
	 * Konstruktor ohne EJB Kontext.
	 * 
	 * @param services
	 *            Services
	 * @param uiMessages
	 *            UIMessages
	 */
	public MobileLoginBean(final VBBServices services, final UIMessages uiMessages) {
		super(services, uiMessages);
		logger = LoggerFactory.getLogger(getClass());
	}

	/**
	 * Konstruktor mit EJB Kontext.
	 * 
	 */
	public MobileLoginBean() {
		super();
	}

	/**
	 * Login das die richtige Seite aufruft.
	 * 
	 * @return Seite
	 */
	public String loginWithOutcome() {
		login();
		if (getLoggedInUser() != null) {
			logger.info("Login erfolgreich weiter zu summary");
			return SUMMARY;
		} else {
			logger.info("Login nicht erfolgreich auf der Seite bleiben");
			return INDEX2;
		}
	}
	
	/**
	 * Meldet einen benutzer ab.
	 * 
	 * @return outcome
	 */
	public String logoutWithOutcome() {
		logout();
		return INDEX;
	}
}

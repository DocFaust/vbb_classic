package de.docfaust.vbb.jsfbeans;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.docfaust.vbb.data.entity.User;
import de.docfaust.vbb.service.VBBServices;
import de.docfaust.vbb.util.RegistrationState;
import de.docfaust.vbb.util.messages.MessageConstants;
import de.docfaust.vbb.util.messages.UIMessages;

/**
 * Managed Bean zum Login von der mobilen Seite.
 * 
 * @author xhu1011
 *
 */
@SessionScoped
@Named
public class LoginBean extends AbstractJSFBean {

	private static final long serialVersionUID = 1039692857096518949L;

	@Inject
	private Logger logger;
	private String userid;
	private String password;
	private User loggedInUser = null;

	/**
	 * Konstruktor ohne EJB Kontext.
	 * 
	 * @param services
	 *            Services
	 * @param uiMessages
	 *            UIMessages
	 */
	public LoginBean(final VBBServices services, final UIMessages uiMessages) {
		super(services, uiMessages);
		logger = LoggerFactory.getLogger(getClass());
	}

	/**
	 * Konstruktor mit EJB Kontext.
	 * 
	 */
	public LoginBean() {
		super();
	}

	/**
	 * Meldet einen Benutzer an.
	 */
	public void login() {
		FacesContext context = FacesContext.getCurrentInstance();
		if (context != null) {
			HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
			request.isUserInRole("USER");
		} else {
			logger.warn("Kein FacesContext");
		}

		logger.info("Melde User " + userid + " an");
		if (userid != null && password != null) {

			User user = getServices().login(userid, password);
			if (user != null) {
				if (user.getState().equals(RegistrationState.PROOFED)) {
					logger.info("Anmeldung erfolgreich");
					setLoggedInUser(user);
					showUIMessage(MessageConstants.LOGIN_SUCCESSFUL);
				} else {
					logger.info("Benutzer nicht registriert: " + user.getState());
					showUIMessage(MessageConstants.REGISTRATION_PENDING);
					userid = null;
					password = null;
				}
			} else {
				logger.info("Benutzer konnte nicht angemeldet werden");
				showUIMessage(MessageConstants.PASSWORD_ERROR);
				userid = null;
				password = null;
			}
		} else {
			showUIMessage(MessageConstants.PASSWORD_ERROR);
		}

		password = null;
	}

	/**
	 * Meldet einen benutzer ab.
	 * 
	 * @return outcome
	 */
	public String logout() {
		setLoggedInUser(null);
		userid = null;
		password = null;
		FacesContext context = FacesContext.getCurrentInstance();
		if (context != null) {
			context.getExternalContext().invalidateSession();
			logger.info("Invalidiere Session");
		} else {
			logger.info("Kein Faces Context");
		}
		logger.info("ausgeloggt");
		return "/index";
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(final String userid) {
		this.userid = userid;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	/**
	 * Gibt zurück, ob ein Nutzer angemeldet ist.
	 * 
	 * @return true, wenn angemeldet
	 */
	public boolean getLoginSuccessful() {
		boolean loginSuccessful = getLoggedInUser() != null;
		logger.info("LoginSuccessful: " + loginSuccessful);
		return loginSuccessful;
	}

	public User getLoggedInUser() {
		return loggedInUser;
	}

	public void setLoggedInUser(final User loggedInUser) {
		this.loggedInUser = loggedInUser;
	}
}
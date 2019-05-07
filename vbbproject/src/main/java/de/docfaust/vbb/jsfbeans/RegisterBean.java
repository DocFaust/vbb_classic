package de.docfaust.vbb.jsfbeans;

import java.util.UUID;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.docfaust.vbb.data.entity.User;
import de.docfaust.vbb.service.VBBServices;
import de.docfaust.vbb.util.PasswordUtil;
import de.docfaust.vbb.util.RegistrationState;
import de.docfaust.vbb.util.messages.MessageConstants;
import de.docfaust.vbb.util.messages.UIMessages;
import de.docfaust.vbb.util.statusliste.Statusliste;

/**
 * JSF Bean für die Registrierung.
 * 
 * @author xhu1011
 *
 */
@Named
@RequestScoped
public class RegisterBean extends AbstractJSFBean {
	private static final long serialVersionUID = 7092718502438533869L;

	@Inject
	private Logger logger;

	private String userid;
	private String username;
	private String email;
	private String password;
	private String passwordrepeat;

	/**
	 * Konstruktor ohne EJB Kontext.
	 * 
	 * @param services
	 *            Services
	 * @param uiMessages
	 *            UIMessages
	 */
	public RegisterBean(final VBBServices services, final UIMessages uiMessages) {
		super(services, uiMessages);
		logger = LoggerFactory.getLogger(getClass());
	}

	/**
	 * Konstruktor mit EJB Kontext.
	 * 
	 */
	public RegisterBean() {
		super();
	}


	public String getUserid() {
		return userid;
	}

	public void setUserid(final String userid) {
		this.userid = userid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(final String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	public String getPasswordrepeat() {
		return passwordrepeat;
	}

	public void setPasswordrepeat(final String passwordrepeat) {
		this.passwordrepeat = passwordrepeat;
	}

	/**
	 * Registriert einen Benutzer.
	 * 
	 * @return Outcome
	 */
	public String register() {
		logger.info("Registriere " + userid);

		User user = new User();
		user.setUserid(getUserid());
		user.setUsername(getUsername());
		user.setEmail(getEmail());
		user.setPassword(PasswordUtil.encryptPassword(getPassword()));
		user.setState(RegistrationState.OPEN);
		user.setRegid(UUID.randomUUID().toString());
		Statusliste statusliste = getServices().register(user);
		if (!statusliste.booleanValue()) {
			this.showMessages(statusliste);
		} else {
			showUIMessage(MessageConstants.REGISTER_SUCCESSFUL);
			getServices().sendRegistrationMail(user);
			return "/registered";
		}
		return "";
	}
}

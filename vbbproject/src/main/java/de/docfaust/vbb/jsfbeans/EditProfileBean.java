package de.docfaust.vbb.jsfbeans;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.docfaust.vbb.data.entity.User;
import de.docfaust.vbb.service.UserService;
import de.docfaust.vbb.util.FacesHelper;
import de.docfaust.vbb.util.PasswordUtil;
import de.docfaust.vbb.util.messages.MessageConstants;
import de.docfaust.vbb.util.messages.UIMessages;
import de.docfaust.vbb.util.statusliste.Statusliste;

/**
 * Bean zur Verwaltung des Benutzerprofils.
 * 
 * @author xhu1011
 *
 */
@ViewScoped
@Named
public class EditProfileBean extends AbstractJSFBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4186808529137731797L;

	@Inject
	private Logger logger;

	private User user;
	private String password;
	private String passwordrepeat;

	@Inject
	private UserService userService;

	/**
	 * Konstruktor ohne EJB Kontext.
	 * 
	 * @param uiMessages
	 *            UIMessages
	 */
	public EditProfileBean(final UIMessages uiMessages, final UserService userService ) {
		super(uiMessages);
		this.userService = userService;
		logger = LoggerFactory.getLogger(getClass());
	}

	/**
	 * Konstruktor mit EJB Kontext.
	 * 
	 */
	public EditProfileBean() {
		super();
	}

	/**
	 * Lädt den aktuell angemeldeten User.
	 */
	@PostConstruct
	public void init() {
		String userid = FacesHelper.getCurrentUserID();
		if (userid != null) {
			user = userService.getLoggedInUser(userid);
		}
	}

	public User getUser() {
		return user;
	}

	public void setUser(final User user) {
		this.user = user;
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
	 * Speichert den aktuellen User.
	 */
	public void save() {
		logger.info("Speichere " + user.toString());
		Statusliste statusliste = userService.saveUser(user);

		showMessages(statusliste);
	}

	/**
	 * Editiert das aktuelle Passwort.
	 */
	public void editPassword() {
		user.setPassword(PasswordUtil.encryptPassword(password));

		save();
		showUIMessage(MessageConstants.PASSWORD_CHANGED);
	}
}

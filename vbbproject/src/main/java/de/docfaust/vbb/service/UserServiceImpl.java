package de.docfaust.vbb.service;

import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.docfaust.vbb.data.entity.User;
import de.docfaust.vbb.data.facades.UserFacade;
import de.docfaust.vbb.util.PasswordUtil;
import de.docfaust.vbb.util.messages.MessageConstants;
import de.docfaust.vbb.util.statusliste.Statusliste;

@Dependent
public class UserServiceImpl implements UserService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1197274787359079774L;

	@EJB
	private UserFacade userFacade;
	
	@Inject 
	private Logger logger;
	
	/**
	 * @param userFacade from JUnit
	 */
	public UserServiceImpl(final UserFacade userFacade) {
		super();
		this.userFacade = userFacade;
		this.logger = LoggerFactory.getLogger(getClass());
	}

	/**
	 * 
	 */
	public UserServiceImpl() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * Gibt alle User zurück.
	 * 
	 * @return Liste der User
	 */
	@Override
	public List<User> getUsers() {
		return userFacade.findAll();
	}

	/**
	 * Löscht einen User.
	 * 
	 * @param user
	 *            Zu löschender User
	 */
	@Override
	public void deleteUser(final User user) {
		user.setGroup(null);
		userFacade.remove(user);
	}

	/**
	 * Speichert einen User.
	 * 
	 * @param user
	 *            zu speichernder User
	 * @return Statusliste
	 * 
	 */
	@Override
	public Statusliste saveUser(final User user) {
		logger.info("Speichere den User: " + user.toString());
		Statusliste statusliste = userFacade.checkEmail(user);
		statusliste.addStatusliste(userFacade.checkUserID(user));
		if (statusliste.booleanValue()) {
			userFacade.createOrUpdate(user);
			statusliste.addStatus(MessageConstants.USER_SAVED, user.getUserid());
		}
		return statusliste;
	}

	/**
	 * Sucht den aktuell angemeldeten User.
	 * 
	 * @param userid
	 *            Userid
	 * @return User
	 */
	@Override
	public User getLoggedInUser(final String userid) {
		return userFacade.findByUserName(userid);
	}
	
	/**
	 * Registriert einen Benutzer.
	 * 
	 * @param user
	 *            Benutzer
	 * @return Returncode
	 */
	@Override
	public final Statusliste register(final User user) {
		Statusliste statusliste = userFacade.checkEmail(user);
		if (statusliste.booleanValue()) {
			statusliste = userFacade.checkUserID(user);
		}
		if (statusliste.booleanValue()) {
			statusliste = userFacade.register(user);
		}
		return statusliste;
	}

	/**
	 * Meldet einen Benutzer an.
	 * 
	 * @param user
	 *            Benutzer
	 * @return Benutzerdaten
	 */
	@Override
	public final User login(final User user) {
		User userEntity = userFacade.login(user);
		return userEntity;
	}

	/**
	 * Meldet einen Benutzer an.
	 * 
	 * @param userid
	 *            Benutzerid
	 * @param password
	 *            Passwort
	 * @return Benutzerdaten
	 */
	@Override
	public final User login(final String userid, final String password) {
		User user = new User();
		user.setUserid(userid);
		String hash = PasswordUtil.encryptPassword(password);
		logger.debug(hash);
		user.setPassword(hash);
		return login(user);
	}

	@Override
	public User findByUserName(final String userid) {
		return userFacade.findByUserName(userid);
	}


}

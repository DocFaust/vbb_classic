package de.docfaust.vbb.service;

import java.io.Serializable;
import java.util.List;

import de.docfaust.vbb.data.entity.User;
import de.docfaust.vbb.util.statusliste.Statusliste;

/**
 * Service for Entity User.
 * @author wfa339
 *
 */
public interface UserService extends Serializable {

	/**
	 * Gibt alle User zurück.
	 * 
	 * @return Liste der User
	 */
	List<User> getUsers();

	/**
	 * Löscht einen User.
	 * 
	 * @param user
	 *            Zu löschender User
	 */
	void deleteUser(User user);

	/**
	 * Speichert einen User.
	 * 
	 * @param user
	 *            zu speichernder User
	 * @return Statusliste
	 * 
	 */
	Statusliste saveUser(User user);

	/**
	 * Sucht den aktuell angemeldeten User.
	 * 
	 * @param userid
	 *            Userid
	 * @return User
	 */
	User getLoggedInUser(String userid);

	/**
	 * Login with User and password.
	 * @param userid UserID
	 * @param password Password
	 * @return User
	 */
	User login(String userid, String password);

	/**
	 * Login with User.
	 * @param user User
	 * @return Logged in user
	 */
	User login(User user);

	/**
	 * registers a User.
	 * @param user User
	 * @return status
	 */
	Statusliste register(User user);

	/**
	 * Finds a User by Name.
	 * @param userid id
	 * @return User.
	 */
	User findByUserName(String userid);

}
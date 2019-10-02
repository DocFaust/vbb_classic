package de.docfaust.vbb.service;

import java.io.Serializable;
import java.util.List;

import de.docfaust.vbb.data.entity.User;
import de.docfaust.vbb.util.statusliste.Statusliste;

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

	User login(final String userid, final String password);

	User login(final User user);

	Statusliste register(final User user);

	User findByUserName(String userid);

}
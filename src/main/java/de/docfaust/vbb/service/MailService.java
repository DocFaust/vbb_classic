package de.docfaust.vbb.service;

import java.io.Serializable;

import de.docfaust.vbb.data.entity.Spiel;
import de.docfaust.vbb.data.entity.User;

/**
 * Services for Mail sending.
 * 
 * @author wfa339
 *
 */
public interface MailService extends Serializable {

	/**
	 * Sends the SpielMail.
	 * 
	 * @param spiel Spiel
	 */
	void sendSpielMail(Spiel spiel);

	/**
	 * Sends the RegistrationMail.
	 * 
	 * @param user User
	 */
	void sendRegistrationMail(User user);

}
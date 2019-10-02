package de.docfaust.vbb.service;

import java.io.Serializable;

import de.docfaust.vbb.data.entity.Spiel;
import de.docfaust.vbb.data.entity.User;

public interface MailService extends Serializable {

	void sendSpielMail(final Spiel spiel);

	void sendRegistrationMail(final User user);

}
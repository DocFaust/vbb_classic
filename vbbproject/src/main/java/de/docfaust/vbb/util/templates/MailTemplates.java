package de.docfaust.vbb.util.templates;

import de.docfaust.vbb.data.entity.Spiel;
import de.docfaust.vbb.data.entity.User;
import de.docfaust.vbb.model.SaldoModel;

/**
 * Interface für das Erstellen der Mails aus Templates.
 * 
 * @author xhu1011
 *
 */
public interface MailTemplates {

	/**
	 * Liefert das Registrierungs Mail als HTML String.
	 * 
	 * @param user   Benutzer Objekt
	 * @param domain Domäne in der der Server läuft
	 * @return HTML Mail String
	 */
	String getRegisterMail(User user, String domain);

	/**
	 * Gets SaldoMail filled with given Parameters.
	 * 
	 * @param spiel  SPiel
	 * @param name   Name of the Recipient
	 * @param salden Model of Saldo
	 * @return filled mail string
	 */
	String getSaldoMail(Spiel spiel, String name, SaldoModel salden);
}

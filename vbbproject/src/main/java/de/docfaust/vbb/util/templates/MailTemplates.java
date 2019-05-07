package de.docfaust.vbb.util.templates;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map.Entry;

import de.docfaust.vbb.data.entity.Spiel;
import de.docfaust.vbb.data.entity.User;

/**
 * Interface für das Erstellen der Mails aus Templates.
 * 
 * @author xhu1011
 *
 */
public interface MailTemplates {
	/**
	 * Liefert das SaldoMail als HTML String.
	 * 
	 * @param spiel
	 *            Spiel Objekt
	 * @param name
	 *            Name des Empfängers
	 * @param salden
	 *            SaldenListe
	 * @param completesaldo
	 *            Komplettes Saldo
	 * @return HTML Mail String
	 */
	String getSaldoMail(Spiel spiel, String name, List<Entry<String, BigDecimal>> salden, BigDecimal completesaldo);

	/**
	 * Liefert das Registrierungs Mail als HTML String.
	 * @param user Benutzer Objekt
	 * @param domain Domäne in der der Server läuft
	 * @return HTML Mail String
	 */
	String getRegisterMail(User user, String domain);
}

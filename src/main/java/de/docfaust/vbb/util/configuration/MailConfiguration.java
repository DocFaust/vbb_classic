package de.docfaust.vbb.util.configuration;

/**
 * Interfacte für die Konfiguration von Mails.
 * 
 * @author xhu1011
 *
 */
public interface MailConfiguration {

	/**
	 * Liefert die Absender Adresse.
	 * 
	 * @return Absender Adresse
	 */
	String getSenderAddress();

	/**
	 * Setzt die Absender Adresse.
	 * 
	 * @param address
	 *            Absender Adresse
	 */
	void setSenderaddress(String address);

	/**
	 * Liefert die Domäne.
	 * 
	 * @return Domäne
	 */
	String getDomain();

	/**
	 * Satzt die Domäne.
	 * 
	 * @param domain
	 *            Domäne
	 */
	void setDomain(String domain);

	/**
	 * Liefert den Betreff zur Registrierung.
	 * 
	 * @return Betreff
	 */
	String getRegistrationSubject();

	/**
	 * Setzt den Betreff zur Registrierung.
	 * 
	 * @param subject
	 *            Betreff
	 */
	void setRegistrationSubject(String subject);
}

package de.docfaust.vbb.util.templates;

/**
 * Interface für die Templates für die Registrierung.
 * 
 * @author xhu1011
 *
 */
public interface RegisterTemplates {
	/**
	 * Gibt die OK Meldung zurück.
	 * 
	 * @param userid
	 *            Registrierte UserID
	 * @param domain
	 *            Domain
	 * @return befüllte Meldung
	 */
	String getOk(String userid, String domain);

	/**
	 * Gibt die bereits registriert Meldung zurück.
	 * 
	 * @param userid
	 *            Registrierte UserID
	 * @return befüllte Meldung
	 */
	String getYetRegistered(String userid);

	/**
	 * Gibt die ID nicht gefunden Meldung zurück.
	 * 
	 * @param userid
	 *            Nicht gefundene UserID
	 * @return befüllte Meldung
	 */
	String getWrongID(String userid);

	/**
	 * Gibt die Registrierung nicht gefunden Meldung zurück.
	 * 
	 * @param userid
	 *            Nicht gefundene UserID
	 * @return befüllte Meldung
	 */
	String getNotRegistered(String userid);

	/**
	 * Gibt die Registrierung nicht gefunden Meldung zurück.
	 * 
	 * @return befüllte Meldung
	 */
	String getWrongRequest();
}

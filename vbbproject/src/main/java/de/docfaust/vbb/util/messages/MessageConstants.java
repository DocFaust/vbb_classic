package de.docfaust.vbb.util.messages;

/**
 * Enum von Konstanten für die Messages.
 * 
 * @author xhu1011
 *
 */
public enum MessageConstants {
	/**
	 * Keine Saison gewählt.
	 */
	NO_SEASON_CHOSEN(false),

	/**
	 * Saison gelöscht.
	 */
	SEASON_DELETED(true),

	/**
	 * Fehler beim Löschen der Saison.
	 */
	SEASON_DELETION_ERROR(false),

	/**
	 * Spaison gespeichert.
	 */
	SEASON_SAVED(true),

	/**
	 * Fehler beim Speichern der Saison.
	 */
	SEASON_SAVE_ERROR(false),

	/**
	 * Saison hat Referenzen.
	 */
	SEASON_HAS_REFERENCES(false),

	/**
	 * Allgemeiner Fehler.
	 */
	UNKNOWN_ERROR(false),

	/**
	 * Spiel gelöscht.
	 */
	GAME_DELETED(true),

	/**
	 * Fehler beim Löschen des Spiels.
	 */
	GAME_DELETION_ERROR(false),

	/**
	 * Kein Spiel gewählt.
	 */
	NO_GAME_CHOSEN(false),

	/**
	 * Fehler beim Speichern des Spiels.
	 */
	GAME_SAVE_ERROR(false),

	/**
	 * Mehr als ein Bezahler.
	 */
	GAME_MORE_PAYERS(false),

	/**
	 * kein Bezahler.
	 */
	GAME_NO_PAYER(false),

	/**
	 * Bezahler nicht anwesend.
	 */
	GAME_PAYER_NOT_PRESENT(false),

	/**
	 * Spiel gespeichert.
	 */
	GAME_SAVED(true),

	/**
	 * Spieler gespeichert.
	 */
	PLAYER_SAVED(true),

	/**
	 * Fehler beim Speichern.
	 */
	PLAYER_SAVE_ERROR(false),

	/**
	 * Spieler existiert bereits.
	 */
	PLAYER_EXISTING(false),

	/**
	 * Buchung gelöscht.
	 */
	ENTRY_DELETED(true),

	/**
	 * Löschen der Buchung fehlerhaft.
	 */
	ENTRY_DELETION_ERROR(false),

	/**
	 * Buchung gespeichert.
	 */
	ENTRY_SAVED(true),

	/**
	 * Registrierung erfolgreich.
	 */
	REGISTER_SUCCESSFUL(true),

	/**
	 * Benutzer bereits registriert.
	 */
	ALREADYREGISTERED(false),

	/**
	 * Passwort falsch.
	 */
	PASSWORD_ERROR(false),

	/**
	 * Registrierung ausstehend.
	 */
	REGISTRATION_PENDING(false),

	/**
	 * Anmeldung erfolgreich.
	 */
	LOGIN_SUCCESSFUL(true),

	/**
	 * Spielsaldo ist nicht 0.
	 */
	SPIELSALDO_NICHT_NULL(false),

	/**
	 * Buchungsschnitt erfolgreich.
	 */
	REORG_SUCCESSFUL(true),

	/**
	 * Fehler beim Buchungsschnitt.
	 */
	REORG_ERROR(false),

	/**
	 * Spiel schon vorhanden.
	 */
	GAME_ALREADYEXISTING(false),

	/**
	 * Saison ist ein Buchungsschnitt.
	 */
	SEASON_IS_BUCHUNGSSCHNITT(false),

	/**
	 * Saison nicht gefunden.
	 */
	SEASON_NOT_FOUND(false),

	/**
	 * Saison hat keinen Preis.
	 */
	SEASON_NO_PRICE(false),

	/**
	 * Allgemein Erfolgreich.
	 */
	SUCCESSFUL(true),

	/**
	 * Spiel nicht gefunden.
	 */
	SPIEL_NOT_FOUND(false),

	/**
	 * Benutzer gespeichert.
	 */
	USER_SAVED(true),

	/**
	 * Passwort geändert.
	 */
	PASSWORD_CHANGED(true),

	/**
	 * Email bereits vorhanden.
	 */
	EMAIL_EXISTS(false),

	/**
	 * Userid bereits vorhanden.
	 */
	USERID_EXISTS(false), SPIELER_HAS_BUCHUNGEN(false), NO_INPUT(false), NO_BUCHUNG_SELECTED(false), CONFIG_SAVED(true), CONFIG_RESET(true);

	/**
	 * Gibt an, ob eine Nachricht positiv ist.
	 */
	private boolean positive;

	/**
	 * Konstruktor mit der Möglichkeit zum Setzen des Nachrichtenstatus.
	 * 
	 * @param positive
	 *            true, wenn die Nachricht kein Fehler ist
	 */
	MessageConstants(final boolean positive) {
		this.setPositive(positive);
	}

	/**
	 * Zeigt an, ob die Meldung eine Fehlermeldung ist.
	 * <ul>
	 * <li>true = Meldung ist Erfolgsmeldung
	 * <li>false = Meldung ist Fehlermeldung
	 * </ul>
	 * 
	 * @return true, wenn Erfolgsmeldung.
	 */
	public boolean isPositive() {
		return positive;
	}

	/**
	 * Setzt, ob die Meldung eine Fehlermeldung ist.
	 * <ul>
	 * <li>true = Meldung ist Erfolgsmeldung
	 * <li>false = Meldung ist Fehlermeldung
	 * </ul>
	 * 
	 * @param positive
	 *            true, wenn Erfolgsmeldung.
	 */
	public void setPositive(final boolean positive) {
		this.positive = positive;
	}

}

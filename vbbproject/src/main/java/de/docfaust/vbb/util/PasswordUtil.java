package de.docfaust.vbb.util;

import org.jboss.crypto.CryptoUtil;

/**
 * Utility zur Passwortverschlüsselung.
 * 
 * @author wfa339
 *
 */
public final class PasswordUtil {

	/**
	 * UTF-8 Encoding.
	 */
	private static final String UTF_8 = "UTF-8";

	/**
	 * MD5 verschlüsselung.
	 */
	private static final String MD5 = "MD5";

	/**
	 * Versteckter Konstruktor.
	 */
	private PasswordUtil() {
	}

	/**
	 * Verschlüsselt ein Passwort.
	 * 
	 * @param password
	 *            entschlüsseltes Passwort
	 * @return verschlüsseltes Passwort
	 */
	public static String encryptPassword(final String password) {
		String passwordHash = CryptoUtil.createPasswordHash(MD5, CryptoUtil.BASE64_ENCODING, UTF_8, null, password);
		return passwordHash;
	}
}

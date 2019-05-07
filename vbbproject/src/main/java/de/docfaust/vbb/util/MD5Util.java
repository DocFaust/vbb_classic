package de.docfaust.vbb.util;

import java.security.MessageDigest;

/**
 * Utilityklasse für die Umrechnung in MD5 Hash.
 * 
 * @author xhu1011
 *
 */
public final class MD5Util {
	private static final int X00FF = 0x00FF;

	/**
	 * Versteckter Konstruktor.
	 */
	private MD5Util() {
	}

	/**
	 * Generiert einen Hash aus dem Passwort.
	 * 
	 * @param password
	 *            Passwort
	 * @return MD5-Hashwert oder null falls password = null
	 */
	public static byte[] createHash(final String password) {
		if (password == null) {
			return null;
		} else {
			return md5(password).getBytes();
		}
	}

	/**
	 * Macht einen MD5-Hash.
	 * 
	 * @param source
	 *            Quelle
	 * @return MD5 Hash
	 */
	public static String md5(final String source) {
		if (source == null) {
			return null;
		}
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] bytes = md.digest(source.getBytes("UTF-8"));
			return getString(bytes);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * HexString aus Bytes.
	 * 
	 * @param bytes
	 *            Byte Array
	 * @return HexString
	 */
	private static String getString(final byte[] bytes) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < bytes.length; i++) {
			byte b = bytes[i];
			String hex = Integer.toHexString((int) X00FF & b);
			if (hex.length() == 1) {
				sb.append("0");
			}
			sb.append(hex);
		}
		return sb.toString();
	}
}

package de.docfaust.vbb;

import java.io.UnsupportedEncodingException;

import org.jboss.crypto.CryptoUtil;

import de.docfaust.vbb.util.PasswordUtil;

public class TestMD5 {

	public static void main(String[] args) throws UnsupportedEncodingException {
		//"MTRhNmE4YWY2Njk1ZWZiYTk0NjQ0OTk0YmQ3ODMwMjE="
		System.out.println(PasswordUtil.encryptPassword("o7juei0p"));
		String hashedPassword=CryptoUtil.createPasswordHash("MD5", "base64", "UTF-8", null, "o7juei0p");
		System.out.println(hashedPassword);
	}

}

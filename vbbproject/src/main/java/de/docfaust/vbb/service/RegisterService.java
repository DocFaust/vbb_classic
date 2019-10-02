package de.docfaust.vbb.service;

import java.io.Serializable;

public interface RegisterService extends Serializable {

	/**
	 * Verarbeitet die Registrierung.
	 * 
	 * @param regid  Registrierungsid.
	 * @param userid Benutzerid.
	 * @return ServerResponse.
	 */
	String processRegistration(String regid, String userid);

}
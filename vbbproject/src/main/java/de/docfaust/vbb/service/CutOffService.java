package de.docfaust.vbb.service;

import java.io.Serializable;
import java.util.Date;

public interface CutOffService extends Serializable {

	/**
	 * Führt einen kompletten Buchungsschnitt durch.
	 * 
	 * @param date
	 *            Datum zu dem der Buchungsschnitt durchgeführt werden soll.
	 */
	void starteBuchungsschnitt(Date date);

}
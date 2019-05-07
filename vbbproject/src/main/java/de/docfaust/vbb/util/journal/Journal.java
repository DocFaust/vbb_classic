package de.docfaust.vbb.util.journal;

import de.docfaust.vbb.data.entity.BusinessCase;

/**
 * Interface for Journalwriting.
 * @author wfa339
 *
 */
public interface Journal {
	/**
	 * Writes a Journal.
	 * @param businessCase Business case the Journal belongs to
	 * @param user User that called the business case
	 * @param description Details
	 */
	void writeJournal(BusinessCase businessCase, String user, String description);
}

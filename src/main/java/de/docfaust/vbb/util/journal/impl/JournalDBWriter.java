package de.docfaust.vbb.util.journal.impl;

import java.util.Date;

import javax.ejb.EJB;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.docfaust.vbb.data.entity.BusinessCase;
import de.docfaust.vbb.data.entity.JournalEntry;
import de.docfaust.vbb.data.facades.JournalFacade;
import de.docfaust.vbb.util.journal.Journal;
import de.docfaust.vbb.util.journal.annotations.JournalWriter;

/**
 * Implementation of Journal with Journal in Database.
 * 
 * @author wfaust
 *
 */
@Dependent
@JournalWriter
public class JournalDBWriter implements Journal {
	@EJB
	private JournalFacade journalFacade;

	@Inject
	private Logger logger;

	/**
	 * Standard constructor for EJB context.
	 */
	public JournalDBWriter() {
	}

	/**
	 * Constructor for Non EJB context.
	 * 
	 * @param journalFacade
	 *            Journalfacade if not Injected
	 */
	public JournalDBWriter(final JournalFacade journalFacade) {
		this.journalFacade = journalFacade;
		logger = LoggerFactory.getLogger(getClass());
	}

	@Override
	public void writeJournal(final BusinessCase businessCase, final String user, final String description) {
		logger.info(String.format("Writing Journal for Businesscase: %s; User: %s; Description: %s ",
				businessCase.toString(), user, description));
		final JournalEntry entry = new JournalEntry();
		entry.setBusinessCase(businessCase);
		entry.setDescription(description);
		entry.setUserId(user);
		entry.setTms(new Date());
		try {
			journalFacade.create(entry);
		} catch (Exception e) {
			logger.error("Journal konnte nicht geschrieben werden", e);
		}
	}
}

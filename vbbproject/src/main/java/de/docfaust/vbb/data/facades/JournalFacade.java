package de.docfaust.vbb.data.facades;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.docfaust.vbb.data.entity.JournalEntry;

/**
 * Facade für den Datenbankzugriff zur JournalEntry via JPA. Implementierung als EJB.
 * Für JUnit Tests kann ein Entitymanager gesetzt werden, es ist aber selbst für
 * eine Transaktionsklammer zu sorgen.
 * 
 * @author xhu1011
 *
 */
@Stateless
public class JournalFacade extends AbstractFacade<JournalEntry> {
	@Inject
	private Logger logger;

	@PersistenceContext(name = "vbb")
	private EntityManager em;

	/**
	 * Leerer Konstruktor. Standard für den Zugriff via EJB. Die Entitätsklasse
	 * wird der Superklasse gesetzt.
	 */
	public JournalFacade() {
		super(JournalEntry.class);
	}

	/**
	 * Konstruktor für JUnit Tests. Die Entitätsklasse wird der Superklasse
	 * gesetzt. Hier kann ein außerhalb erzeugter EntityManager getzt werden.
	 * Achtung, für eine Transaktionsklammer muss selbst gesorgt werden.
	 * 
	 * @param em
	 *            Außerhalb erzeugter EntityManager
	 */
	public JournalFacade(final EntityManager em) {
		super(JournalEntry.class);
		logger = LoggerFactory.getLogger(getClass());
		this.em = em;
		setAutocommit(false);
		logger.debug("Constructor with explicit EntityManager");
	}

	@Override
	public EntityManager getEntityManager() {
		return em;
	}
}

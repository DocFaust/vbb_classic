package de.docfaust.vbb.data.facades;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.docfaust.vbb.data.entity.Buchung;
import de.docfaust.vbb.data.entity.Buchung_;

/**
 * Facade für den Datenbankzugriff zur Buchung via JPA. Implementierung als EJB.
 * Für JUnit Tests kann ein Entitymanager gesetzt werden, es ist aber selbst für
 * eine Transaktionsklammer zu sorgen.
 * 
 * @author xhu1011
 *
 */
@Stateless
public class BuchungFacade extends AbstractFacade<Buchung> {
	@Inject
	private Logger logger;

	@PersistenceContext(name = "vbb")
	private EntityManager em;

	/**
	 * Leerer Konstruktor. Standard für den Zugriff via EJB. Die Entitätsklasse
	 * wird der Superklasse gesetzt.
	 */
	public BuchungFacade() {
		super(Buchung.class);
	}

	/**
	 * Konstruktor für JUnit Tests. Die Entitätsklasse wird der Superklasse
	 * gesetzt. Hier kann ein außerhalb erzeugter EntityManager getzt werden.
	 * Achtung, für eine Transaktionsklammer muss selbst gesorgt werden.
	 * 
	 * @param em
	 *            Außerhalb erzeugter EntityManager
	 */
	public BuchungFacade(final EntityManager em) {
		super(Buchung.class);
		logger = LoggerFactory.getLogger(getClass());
		this.em = em;
		setAutocommit(false);
		logger.debug("Constructor with explicit EntityManager");
	}

	@Override
	public EntityManager getEntityManager() {
		return em;
	}

	/**
	 * Liefert alle Buchungen, die vor einem Bestimmten Datum getätigt wurden.
	 * 
	 * @param date
	 *            Datum.
	 * @return Liste der Buchungen
	 */
	public List<Buchung> getBuchungenBeforeDate(final Date date) {
		logger.debug("Suche Buchungen vor " + new SimpleDateFormat("dd.MM.yyyy HH:mm:ss,SSS").format(date));
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Buchung> cq = cb.createQuery(Buchung.class);
		Root<Buchung> root = cq.from(Buchung.class);

		cq.select(root);

		cq.where(cb.lessThan(root.get(Buchung_.datum), date));

		TypedQuery<Buchung> q = em.createQuery(cq);
		logger.info("Result: " + q.getResultList().toString());

		return q.getResultList();
	}
}

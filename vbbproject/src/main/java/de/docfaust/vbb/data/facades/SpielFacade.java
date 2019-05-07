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

import de.docfaust.vbb.data.entity.Spiel;
import de.docfaust.vbb.data.entity.Spiel_;
import de.docfaust.vbb.util.messages.MessageConstants;
import de.docfaust.vbb.util.statusliste.Statusliste;

/**
 * Facade für den Datenbankzugriff zur Spiel via JPA. Implementierung als EJB.
 * Für JUnit Tests kann ein Entitymanager gesetzt werden, es ist aber selbst für
 * eine Transaktionsklammer zu sorgen.
 * 
 * @author xhu1011
 *
 */
@Stateless
public class SpielFacade extends AbstractFacade<Spiel> {

	@PersistenceContext(name = "vbb")
	private EntityManager em;
	@Inject
	private Logger logger;

	/**
	 * Leerer Konstruktor. Standard für den Zugriff via EJB. Die Entitätsklasse
	 * wird der Superklasse gesetzt.
	 */
	public SpielFacade() {
		super(Spiel.class);
	}

	/**
	 * Konstruktor für JUnit Tests. Die Entitätsklasse wird der Superklasse
	 * gesetzt. Hier kann ein außerhalb erzeugter EntityManager getzt werden.
	 * Achtung, für eine Transaktionsklammer muss selbst gesorgt werden.
	 * 
	 * @param em
	 *            Außerhalb erzeugter EntityManager
	 */
	public SpielFacade(final EntityManager em) {
		super(Spiel.class);
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
	 * Löscht ein Spiel.
	 * 
	 * @param spiel
	 *            zu löschendes Spiel
	 * @return Statusliste
	 */
	public Statusliste deleteSpiel(final Spiel spiel) {
		Statusliste statusliste = new Statusliste();
		Spiel tmp = find(spiel.getId());
		if (tmp == null) {
			logger.info("Spiel nicht gefunden");
			statusliste.addStatus(MessageConstants.SPIEL_NOT_FOUND);
		} else {
			logger.info("Lösche Spiel");
			spiel.setSeason(null);
			remove(spiel);
			statusliste.addStatus(MessageConstants.GAME_DELETED);
		}
		return statusliste;
	}

	/**
	 * Prüft ob ein Spiel für das Datum existiert.
	 * 
	 * @param datum
	 *            Datum, zu dem ein Spiel gesucht werden soll.
	 * @return true, wenn es ein Spiel zu dem Datum gibt
	 */
	public List<Spiel> getSpieleForDate(final Date datum) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Spiel> cq = cb.createQuery(Spiel.class);
		Root<Spiel> root = cq.from(Spiel.class);
		cq.select(root);

		cq.where(cb.equal(root.get(Spiel_.datum), datum));

		TypedQuery<Spiel> q = em.createQuery(cq);
		List<Spiel> resultList = q.getResultList();
		logger.info("Result: " + resultList.toString());

		return resultList;

	}

	/**
	 * Sucht die Spiele vor einem bestimmten Datum.
	 * 
	 * @param date
	 *            Datum
	 * @return Spiele
	 */
	public List<Spiel> getSpieleBeforeDate(final Date date) {
		logger.debug("Suche Spiele vor " + new SimpleDateFormat("dd.MM.yyyy HH:mm:ss,SSS").format(date));
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Spiel> cq = cb.createQuery(Spiel.class);
		Root<Spiel> root = cq.from(Spiel.class);

		cq.select(root);

		cq.where(cb.lessThanOrEqualTo(root.get(Spiel_.datum), date));
		
		TypedQuery<Spiel> q = em.createQuery(cq);
		logger.info("Result: " + q.getResultList().toString());

		return q.getResultList();
	}

	@Override
	public List<Spiel> findAll() {
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Spiel> cq = cb.createQuery(Spiel.class);
		Root<Spiel> root = cq.from(Spiel.class);
		cq.select(root);
		cq.orderBy(cb.asc(root.get(Spiel_.datum)));
		return getEntityManager().createQuery(cq).getResultList();
	}
}

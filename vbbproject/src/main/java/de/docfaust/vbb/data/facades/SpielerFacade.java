package de.docfaust.vbb.data.facades;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.docfaust.vbb.data.entity.Spieler;
import de.docfaust.vbb.data.entity.Spieler_;

/**
 * Facade für den Datenbankzugriff zum Spieler via JPA. Implementierung als EJB.
 * Für JUnit Tests kann ein Entitymanager gesetzt werden, es ist aber selbst für
 * eine Transaktionsklammer zu sorgen.
 * 
 * @author xhu1011
 *
 */
@Stateless
public class SpielerFacade extends AbstractFacade<Spieler> {

	@PersistenceContext(name = "vbb")
	private EntityManager em;
	@Inject
	private Logger logger;

	/**
	 * Leerer Konstruktor. Standard für den Zugriff via EJB. Die Entitätsklasse wird
	 * der Superklasse gesetzt.
	 */
	public SpielerFacade() {
		super(Spieler.class);
	}

	/**
	 * Konstruktor für JUnit Tests. Die Entitätsklasse wird der Superklasse gesetzt.
	 * Hier kann ein außerhalb erzeugter EntityManager getzt werden. Achtung, für
	 * eine Transaktionsklammer muss selbst gesorgt werden.
	 * 
	 * @param em Außerhalb erzeugter EntityManager
	 */
	public SpielerFacade(final EntityManager em) {
		super(Spieler.class);
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
	 * Sucht einen Spieler anhand seines Namens.
	 * 
	 * @param name Name des Spielers
	 * @return Spieler Objekt
	 */
	public Spieler findByName(final String name) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Spieler> cq = cb.createQuery(Spieler.class);
		Root<Spieler> root = cq.from(Spieler.class);
		cq.select(root);

		cq.where(cb.equal(root.get(Spieler_.name), name));

		TypedQuery<Spieler> q = em.createQuery(cq);
		logger.info("Result: " + q.getResultList().toString());

		Spieler s = null;
		try {
			s = q.getSingleResult();
		} catch (NoResultException e) {
			logger.warn("Kein Spieler mit dem Namen " + name + " gefunden");
		}

		return s;
	}

	/**
	 * Find All.
	 * @return List of players.
	 */
	public List<Spieler> findSpieler() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Spieler> cq = cb.createQuery(Spieler.class);
		Root<Spieler> root = cq.from(Spieler.class);
		cq.select(root);
		cq.orderBy(cb.desc(root.get(Spieler_.activityLevel)));
		TypedQuery<Spieler> q = em.createQuery(cq);
		List<Spieler> list = q.getResultList();
		return list;
	}
}

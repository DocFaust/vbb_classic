package de.docfaust.vbb.data.facades;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.docfaust.vbb.data.entity.Season;
import de.docfaust.vbb.data.entity.Season_;
import de.docfaust.vbb.util.messages.MessageConstants;
import de.docfaust.vbb.util.statusliste.Statusliste;

/**
 * Facade für den Datenbankzugriff zur Season via JPA. Implementierung als EJB.
 * Für JUnit Tests kann ein Entitymanager gesetzt werden, es ist aber selbst für
 * eine Transaktionsklammer zu sorgen.
 * 
 * @author xhu1011
 *
 */
@Stateless
public class SeasonFacade extends AbstractFacade<Season> {

	@PersistenceContext(name = "vbb")
	private EntityManager em;
	@Inject
	private Logger logger;

	/**
	 * Leerer Konstruktor. Standard für den Zugriff via EJB. Die Entitätsklasse
	 * wird der Superklasse gesetzt.
	 */
	public SeasonFacade() {
		super(Season.class);
	}

	/**
	 * Konstruktor für JUnit Tests. Die Entitätsklasse wird der Superklasse
	 * gesetzt. Hier kann ein außerhalb erzeugter EntityManager getzt werden.
	 * Achtung, für eine Transaktionsklammer muss selbst gesorgt werden.
	 * 
	 * @param em
	 *            Außerhalb erzeugter EntityManager
	 */
	public SeasonFacade(final EntityManager em) {
		super(Season.class);
		logger = LoggerFactory.getLogger(getClass());
		this.em = em;
		setAutocommit(false);
		logger.debug("Constructor with explicit EntityManager");

	}

	@Override
	public EntityManager getEntityManager() {
		logger.debug("getEntityManager");
		return em;
	}

	/**
	 * Zeigt an, ob eine gegebene Saison mit anderen gespeicherten kollidiert.
	 * 
	 * @param season
	 *            zu prüfende Saison.
	 * @return true, wenn die Saison kollidiert.
	 */
	public boolean hasCollisions(final Season season) {
		logger.debug("hasCollisions");
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Season> cq = cb.createQuery(Season.class);
		Root<Season> root = cq.from(Season.class);

		cq.select(root);

		Date startdate = season.getStartdate();
		Date enddate = season.getEnddate();

		Predicate start = cb.between(root.get(Season_.startdate), startdate, enddate);
		Predicate end = cb.between(root.get(Season_.enddate), startdate, enddate);
		Predicate between = cb.or(start, end);

		Predicate startdrin = cb.lessThan(root.get(Season_.startdate), startdate);
		Predicate enddrin = cb.greaterThan(root.get(Season_.enddate), enddate);
		Predicate drin = cb.and(startdrin, enddrin);

		cq.where(cb.or(between, drin));

		TypedQuery<Season> q = em.createQuery(cq);
		return !q.getResultList().isEmpty();
	}

	/**
	 * Liefert die Saison anhand des Datums. Es wird die Saison geliefert, deren
	 * Startdatum um End Endedatum um das gegebene Datum liegen.
	 * 
	 * @param datum
	 *            Datum, in der die Saison liegen sollte
	 * @return Saison
	 * 
	 */
	public Season getSeasonFromDate(final Date datum) {
		logger.debug("Suche Saison für das Datum: " + datum.toString());
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Season> cq = cb.createQuery(Season.class);
		Root<Season> root = cq.from(Season.class);

		cq.select(root);

		Predicate start = cb.lessThanOrEqualTo(root.get(Season_.startdate), datum);
		Predicate end = cb.greaterThanOrEqualTo(root.get(Season_.enddate), datum);

		cq.where(cb.and(start, end));

		TypedQuery<Season> q = em.createQuery(cq);
		logger.info("Result: " + q.getResultList().toString());

		Season s = null;
		try {
			s = q.getSingleResult();
		} catch (NoResultException e) {
			logger.warn("Keine Saison für Datum gefunden");
		} catch (NonUniqueResultException nue) {
			logger.error("Mehr als eine Saison für Datum gefunden", nue);
		}

		return s;
	}

	/**
	 * Löscht eine Saison, wenn sie vorhanden ist und keine Referenzen mehr zu
	 * Spielen hat.
	 * 
	 * @param season
	 *            zu löschende Saison
	 * @return Statusliste
	 */
	public Statusliste deleteSeason(final Season season) {

		Statusliste statusliste = new Statusliste();
		Season tmp = find(season.getId());
		if (tmp == null) {
			logger.info("Saison nicht gefunden");
			statusliste.addStatus(MessageConstants.SEASON_NOT_FOUND);
		} else if (tmp.getSpiele() != null && !tmp.getSpiele().isEmpty()) {
			logger.info("Saison hat noch gültige Spiele");
			statusliste.addStatus(MessageConstants.SEASON_HAS_REFERENCES);
		} else {
			logger.info("Lösche Saison");
			remove(season);
			statusliste.addStatus(MessageConstants.SEASON_DELETED);
		}
		return statusliste;
	}

	/**
	 * Löscht alle Seasons bevor einem bestimmten Datum.
	 * 
	 * @param date
	 *            Datum, vor dem die Seans gelöscht werden sollen.
	 * @return Liste der Seasons vor dem Datum
	 */
	public List<Season> getSeasonsBeforeDate(final Date date) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Season> cq = cb.createQuery(Season.class);
		Root<Season> root = cq.from(Season.class);

		cq.select(root);

		Predicate start = cb.lessThan(root.get(Season_.startdate), date);
		Predicate end = cb.lessThan(root.get(Season_.enddate), date);

		cq.where(cb.and(start, end));

		TypedQuery<Season> q = em.createQuery(cq);
		logger.info("Result: " + q.getResultList().toString());

		return q.getResultList();
	}

}

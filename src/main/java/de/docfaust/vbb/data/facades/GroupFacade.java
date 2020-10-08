package de.docfaust.vbb.data.facades;

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

import de.docfaust.vbb.data.entity.Group;
import de.docfaust.vbb.data.entity.Group_;

/**
 * Facade für den Datenbankzugriff zum User via JPA. Implementierung als EJB.
 * Für JUnit Tests kann ein Entitymanager gesetzt werden, es ist aber selbst für
 * eine Transaktionsklammer zu sorgen.
 * 
 * @author xhu1011
 *
 */
@Stateless
public class GroupFacade extends AbstractFacade<Group> {

	@PersistenceContext(name = "vbb")
	private EntityManager em;
	@Inject
	private Logger logger;

	/**
	 * Leerer Konstruktor. Standard für den Zugriff via EJB. Die Entitätsklasse
	 * wird der Superklasse gesetzt.
	 */
	public GroupFacade() {
		super(Group.class);
	}

	/**
	 * Konstruktor für JUnit Tests. Die Entitätsklasse wird der Superklasse
	 * gesetzt. Hier kann ein außerhalb erzeugter EntityManager getzt werden.
	 * Achtung, für eine Transaktionsklammer muss selbst gesorgt werden.
	 * 
	 * @param em
	 *            Außerhalb erzeugter EntityManager
	 */
	public GroupFacade(final EntityManager em) {
		super(Group.class);
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
	 * Sucht die Gruppe anhand des Namens.
	 * 
	 * @param groupName
	 *            Name der Gruppe
	 * @return Gruppenentität
	 */
	public Group findByName(final String groupName) {
		logger.info("Suche Gruppe " + groupName);
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Group> cq = cb.createQuery(Group.class);
		Root<Group> root = cq.from(Group.class);

		cq.where(cb.equal(root.get(Group_.name), groupName));
		TypedQuery<Group> q = em.createQuery(cq);

		return q.getSingleResult();
	}

}

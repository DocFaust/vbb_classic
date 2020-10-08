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
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.docfaust.vbb.data.entity.User;
import de.docfaust.vbb.data.entity.User_;
import de.docfaust.vbb.util.messages.MessageConstants;
import de.docfaust.vbb.util.statusliste.Statusliste;

/**
 * Facade für den Datenbankzugriff zum User via JPA. Implementierung als EJB.
 * Für JUnit Tests kann ein Entitymanager gesetzt werden, es ist aber selbst für
 * eine Transaktionsklammer zu sorgen.
 * 
 * @author xhu1011
 *
 */
@Stateless
public class UserFacade extends AbstractFacade<User> {

	@PersistenceContext(name = "vbb")
	private EntityManager em;
	@Inject
	private Logger logger;

	/**
	 * Leerer Konstruktor. Standard für den Zugriff via EJB. Die Entitätsklasse
	 * wird der Superklasse gesetzt.
	 */
	public UserFacade() {
		super(User.class);
	}

	/**
	 * Konstruktor für JUnit Tests. Die Entitätsklasse wird der Superklasse
	 * gesetzt. Hier kann ein außerhalb erzeugter EntityManager getzt werden.
	 * Achtung, für eine Transaktionsklammer muss selbst gesorgt werden.
	 * 
	 * @param em
	 *            Außerhalb erzeugter EntityManager
	 */
	public UserFacade(final EntityManager em) {
		super(User.class);
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
	 * Meldet einen Benutzer an.
	 * 
	 * @param user
	 *            Benutzer.
	 * @return Angemeldeter Benutzer
	 */
	public User login(final User user) {
		logger.info("Melde " + user.getUserid() + " an");
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<User> cq = cb.createQuery(User.class);
		Root<User> root = cq.from(User.class);

		Predicate userid = cb.equal(root.get(User_.userid), user.getUserid().toLowerCase());
		Predicate pw = cb.equal(root.get(User_.password), user.getPassword());
		cq.where(cb.and(userid, pw));

		TypedQuery<User> q = em.createQuery(cq);

		User result = null;
		try {
			result = q.getSingleResult();
			logger.debug("User " + user.getUserid() + " angemeldet");
		} catch (NoResultException e) {
			logger.debug("User " + user.getUserid() + " konnte nicht angemeldet werden");
		}
		return result;
	}

	/**
	 * Registriert einen Benutzer.
	 * 
	 * @param user
	 *            Benutzer
	 * @return ReturnCode
	 */
	public Statusliste register(final User user) {
		logger.info("Registriere User " + user.getUserid());

		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<User> cq = cb.createQuery(User.class);
		Root<User> root = cq.from(User.class);

		cq.where(cb.equal(root.get(User_.userid), user.getUserid().toLowerCase()));
		TypedQuery<User> q = em.createQuery(cq);

		List<User> list = q.getResultList();
		Statusliste statusliste = new Statusliste();
		if (!list.isEmpty()) {
			logger.warn("User " + user.getUserid() + " ist bereits in der Datenbank");
			statusliste.addStatus(MessageConstants.ALREADYREGISTERED);
		} else {
			create(user);
			statusliste.addStatus(MessageConstants.REGISTER_SUCCESSFUL);
		}
		return statusliste;
	}

	/**
	 * Sucht einen User anhand der userid.
	 * 
	 * @param userid
	 *            UserID
	 * @return Userentität
	 */
	public User findByUserName(final String userid) {
		logger.info("Suche User " + userid);
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<User> cq = cb.createQuery(User.class);
		Root<User> root = cq.from(User.class);

		cq.where(cb.equal(root.get(User_.userid), userid.toLowerCase()));
		TypedQuery<User> q = em.createQuery(cq);

		try {
			return q.getSingleResult();
		} catch (NoResultException e) {
			logger.warn("User " + userid + " nicht gefunden");
			return null;
		}
	}

	/**
	 * Überprüft, ob die Mailadresse bereits vorhanden ist.
	 * 
	 * @param user
	 *            Benutzer, dessen Adresse geprüft wird
	 * @return Statusliste
	 */
	public Statusliste checkEmail(final User user) {
		Statusliste liste = new Statusliste();
		logger.info("Checke Email von User: " + user.toString());
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<User> cq = cb.createQuery(User.class);
		Root<User> root = cq.from(User.class);

		cq.where(cb.equal(cb.lower(root.get(User_.email)), user.getEmail().toLowerCase()));

		TypedQuery<User> q = em.createQuery(cq);

		List<User> resultList = q.getResultList();
		for (User tmp : resultList) {
			if (user.getId() != tmp.getId()) {

				liste.addStatus(MessageConstants.EMAIL_EXISTS, user.getEmail());
			}
		}
		return liste;
	}

	/**
	 * Überprüft, ob die ID bereits vorhanden ist.
	 * 
	 * @param user
	 *            Benutzer, dessen ID geprüft wird
	 * @return Statusliste
	 */
	public Statusliste checkUserID(final User user) {
		Statusliste liste = new Statusliste();
		logger.info("Checke ID von User: " + user.toString());
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<User> cq = cb.createQuery(User.class);
		Root<User> root = cq.from(User.class);

		cq.where(cb.equal(cb.lower(root.get(User_.userid)), user.getUserid().toLowerCase()));

		TypedQuery<User> q = em.createQuery(cq);

		List<User> resultList = q.getResultList();
		for (User tmp : resultList) {
			if (user.getId() != tmp.getId()) {

				liste.addStatus(MessageConstants.USERID_EXISTS, user.getUserid());
			}
		}
		return liste;
	}
}

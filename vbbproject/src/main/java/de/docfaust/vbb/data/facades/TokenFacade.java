package de.docfaust.vbb.data.facades;

import java.util.Optional;

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

import de.docfaust.vbb.data.entity.Token;
import de.docfaust.vbb.data.entity.Token_;

/**
 * Facade für den Datenbankzugriff zum Token via JPA. Implementierung als EJB.
 * Für JUnit Tests kann ein Entitymanager gesetzt werden, es ist aber selbst für
 * eine Transaktionsklammer zu sorgen.
 * 
 * @author xhu1011
 *
 */
@Stateless
public class TokenFacade extends AbstractFacade<Token> {

	@PersistenceContext(name = "vbb")
	private EntityManager em;
	
	@Inject
	private Logger logger;

	/**
	 * Leerer Konstruktor. Standard für den Zugriff via EJB. Die Entitätsklasse wird
	 * der Superklasse gesetzt.
	 */
	public TokenFacade() {
		super(Token.class);
	}

	/**
	 * Konstruktor für JUnit Tests. Die Entitätsklasse wird der Superklasse gesetzt.
	 * Hier kann ein außerhalb erzeugter EntityManager getzt werden. Achtung, für
	 * eine Transaktionsklammer muss selbst gesorgt werden.
	 * 
	 * @param em Außerhalb erzeugter EntityManager
	 */
	public TokenFacade(final EntityManager em) {
		super(Token.class);
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
	 * Sucht einen Token anhand seines Namens.
	 * 
	 * @param token Name des Token
	 * @return Token Objekt
	 */
	public Optional<Token> findByToken(final String token) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Token> cq = cb.createQuery(Token.class);
		Root<Token> root = cq.from(Token.class);
		cq.select(root);

		cq.where(cb.equal(root.get(Token_.token), token));

		TypedQuery<Token> q = em.createQuery(cq);
		logger.info("Result: " + q.getResultList().toString());

		Optional<Token> s = Optional.empty();
		try {
			s = Optional.of(q.getSingleResult());
		} catch (NoResultException e) {
			logger.warn("Kein Token mit dem Namen " + token + " gefunden");
		}

		return s;
	}
}

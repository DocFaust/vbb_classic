package de.docfaust.vbb.data.facades;

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

import de.docfaust.vbb.data.entity.Config;
import de.docfaust.vbb.data.entity.Config_;

/**
 * Facade für den Datenbankzugriff zur Config via JPA. Implementierung als EJB.
 * Für JUnit Tests kann ein Entitymanager gesetzt werden, es ist aber selbst für
 * eine Transaktionsklammer zu sorgen.
 * 
 * @author xhu1011
 *
 */
@Stateless
public class ConfigFacade extends AbstractFacade<Config> {

	@PersistenceContext(name = "vbb")
	private EntityManager em;
	@Inject
	private Logger logger;

	/**
	 * Leerer Konstruktor. Standard für den Zugriff via EJB. Die Entitätsklasse
	 * wird der Superklasse gesetzt.
	 */
	public ConfigFacade() {
		super(Config.class);
	}

	/**
	 * Konstruktor für JUnit Tests. Die Entitätsklasse wird der Superklasse
	 * gesetzt. Hier kann ein außerhalb erzeugter EntityManager getzt werden.
	 * Achtung, für eine Transaktionsklammer muss selbst gesorgt werden.
	 * 
	 * @param em
	 *            Außerhalb erzeugter EntityManager
	 */
	public ConfigFacade(final EntityManager em) {
		super(Config.class);
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
	 * Gibt den Wert anhand des Schlüssels zurück.
	 * 
	 * @param key
	 *            Schlüssel
	 * @return Wert
	 */
	public String getValue(final String key) {

		Config config = findByKey(key);
		if (config == null) {
			return null;
		}
		return config.getConfigvalue();
	}

	/**
	 * Liefert die Config anhand des Schlüssels.
	 * @param key Schlüssel
	 * @return Config Entity
	 */
	public Config findByKey(final String key) {
		logger.info("Hole: " + key);
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Config> cq = cb.createQuery(Config.class);

		Root<Config> root = cq.from(Config.class);
		cq.select(root).where(cb.equal(root.get(Config_.configkey), key));

		TypedQuery<Config> q = em.createQuery(cq);
		Config value = null;
		try {
			value = q.getSingleResult();
			logger.info("Config: " + key + "=" + value);
		} catch (NoResultException e) {
			logger.warn(key + " not Found");
		}
		return value;

	}

	/**
	 * Gibt den Wert anhand des Schlüssels zurück.
	 * 
	 * @param key
	 *            Schlüssel
	 * @param def
	 *            Defaultwert
	 * @return Wert
	 */
	public String getValue(final String key, final String def) {
		String value = getValue(key);
		if (value == null) {
			logger.warn("Liefere Defaultwert: " + def);
			value = def;
		}
		return value;
	}
}

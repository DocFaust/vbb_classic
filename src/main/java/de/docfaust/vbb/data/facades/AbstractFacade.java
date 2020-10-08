package de.docfaust.vbb.data.facades;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.docfaust.vbb.data.entity.IEntity;

/**
 * Abstrakte Facade mit den üblichsten Datenbankoperationen. Alle Facaden leiten
 * davon ab und implementieren nur den Zugriff auf die DB und die spezifischen
 * Zugriffs Methoden.
 * 
 * @author xhu1011
 *
 * @param <T>
 *            Entität, für die die konkrete Implementierung ist.
 */
public abstract class AbstractFacade<T extends IEntity> {

	/**
	 * Logger.
	 */
	@Inject
	private Logger logger;

	private boolean autocommit = true;

	/**
	 * Entität.
	 */
	private Class<T> entityClass;

	/**
	 * Instanziiert die Facade und setzt die konkrete Entität.
	 * 
	 * @param entityClass
	 *            Entität
	 */
	public AbstractFacade(final Class<T> entityClass) {
		this.entityClass = entityClass;
		if (logger == null) {
			logger = LoggerFactory.getLogger(getClass());
		}
	}

	/**
	 * Abstrakte Methode für den Datenbankzugriff.
	 * 
	 * @return Manager für die Datenbank.
	 */
	protected abstract EntityManager getEntityManager();

	/**
	 * Persistiert eine Entität. Ist sie nicht vorhanden, wird sie erstellt.
	 * 
	 * @param entity
	 *            Entität
	 */
	public void createOrUpdate(final T entity) {
		logger.debug("createorUpdate");
		T tmp = find(entity.getId());
		if (tmp == null) {
			create(entity);
		} else {
			edit(entity);
		}
	}

	/**
	 * Erstellt eine neue Entität.
	 * 
	 * @param entity
	 *            Entität
	 */
	public void create(final T entity) {
		logger.debug("create");
		startTransaction();
		getEntityManager().persist(entity);
		commitTransaction();
	}

	/**
	 * Aktualisiert eine Entität.
	 * 
	 * @param entity
	 *            Entität
	 */
	public void edit(final T entity) {
		logger.debug("edit");
		startTransaction();
		getEntityManager().merge(entity);
		commitTransaction();
	}

	/**
	 * Löscht eine Entität.
	 * 
	 * @param entity
	 *            Entität
	 */
	public void remove(final T entity) {
		logger.debug("remove " + entity.toString());
		startTransaction();
		//getEntityManager().remove(entity);
		getEntityManager().remove(getEntityManager().merge(entity));
		commitTransaction();
	}

	/**
	 * Sucht eine Entitär anhand der id.
	 * 
	 * @param id
	 *            ID
	 * @return Entität
	 */
	public T find(final Object id) {
		logger.debug("find");
		return getEntityManager().find(entityClass, id);
	}

	/**
	 * Liefert alle Entitäten.
	 * 
	 * @return Liste der gefundenen Entitäten
	 */
	public List<T> findAll() {
		logger.debug("findAll");
		CriteriaQuery<T> cq = getEntityManager().getCriteriaBuilder().createQuery(entityClass);
		cq.select(cq.from(entityClass));
		return getEntityManager().createQuery(cq).getResultList();
	}

	/**
	 * Zählt den Inhalt der Entitäten.
	 * 
	 * @return Anzahl der Entitäten
	 */
	public int count() {
		logger.debug("count");
		CriteriaQuery<Long> cq = getEntityManager().getCriteriaBuilder().createQuery(Long.class);
		Root<T> rt = cq.from(entityClass);
		cq.select(getEntityManager().getCriteriaBuilder().count(rt));
		Query q = getEntityManager().createQuery(cq);
		return ((Long) q.getSingleResult()).intValue();
	}

	/**
	 * Startet eine Transaktion, wenn kein Autocommit.
	 */
	protected void startTransaction() {
		if (!isAutocommit()) {
			logger.debug("Starting Transaction");
			getEntityManager().getTransaction().begin();
		}
	}

	/**
	 * Committet eine Transaktion, wenn kein autocommit.
	 * 
	 */
	protected void commitTransaction() {
		if (!isAutocommit()) {
			try {
				logger.debug("Comitting Transaction");
				getEntityManager().getTransaction().commit();
			} catch (Exception e) {
				logger.error("Error committing", e);
				if (getEntityManager().getTransaction().isActive()) {
					logger.info("Rolling back");
					getEntityManager().getTransaction().rollback();
				}
			}
		}
	}

	protected boolean isAutocommit() {
		return autocommit;
	}

	protected void setAutocommit(final boolean autocommit) {
		this.autocommit = autocommit;
	}
}

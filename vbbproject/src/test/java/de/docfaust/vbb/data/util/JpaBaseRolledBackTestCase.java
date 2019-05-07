package de.docfaust.vbb.data.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.ext.hsqldb.HsqldbDataTypeFactory;
import org.dbunit.operation.DatabaseOperation;
import org.hibernate.internal.SessionImpl;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.docfaust.vbb.data.entity.Buchung;
import de.docfaust.vbb.data.entity.Season;
import de.docfaust.vbb.data.entity.Spiel;
import de.docfaust.vbb.data.entity.Spieler;
import de.docfaust.vbb.data.entity.User;
import de.docfaust.vbb.data.facade.util.FacadenFactory;

public abstract class JpaBaseRolledBackTestCase {
	private static EntityManagerFactory emf;

	protected EntityManager em;
	private IDatabaseConnection connection;
	protected Logger logger = LoggerFactory.getLogger(getClass());
	protected static FacadenFactory facadenFactory = null;

	@BeforeAll
	public static void createEntityManagerFactory() throws DatabaseUnitException {
		LoggerFactory.getLogger(JpaBaseRolledBackTestCase.class).info(">> createEntityManagerFactory");
	}

	@AfterAll
	public static void closeEntityManagerFactory() {
		LoggerFactory.getLogger(JpaBaseRolledBackTestCase.class).info(">> closeEntityManagerFactory");
//		em.close();
		emf.close();
		LoggerFactory.getLogger(JpaBaseRolledBackTestCase.class).info("<< closeEntityManagerFactory");
	}

	@BeforeEach
	public void beginTransaction() throws DatabaseUnitException, SQLException, IOException {
		LoggerFactory.getLogger(JpaBaseRolledBackTestCase.class).info(">> beginTransaction");
		emf = Persistence.createEntityManagerFactory("vbb_test");
		em = emf.createEntityManager();
		facadenFactory = new FacadenFactory(em);
		
		// Hibernate
		connection = new DatabaseConnection(((SessionImpl) (em.getDelegate())).connection());
		
// EclipseLink
//		Connection con = ((EntityManagerImpl) (em
//		        .getDelegate())).getServerSession().getAccessor()
//		        .getConnection();
//		connection = new DatabaseConnection(con);
		connection.getConfig().setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new HsqldbDataTypeFactory());
		connection.getConfig().setProperty(DatabaseConfig.FEATURE_ALLOW_EMPTY_FIELDS, true);

		FlatXmlDataSetBuilder flatXmlDataSetBuilder = new FlatXmlDataSetBuilder();
		flatXmlDataSetBuilder.setColumnSensing(true);

		InputStream xmlStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("test-data.xml");
		InputStream dtdStream = ClassLoader.getSystemResourceAsStream("dataset.dtd");

		flatXmlDataSetBuilder.setMetaDataSetFromDtd(dtdStream);
		IDataSet dataset = flatXmlDataSetBuilder.build(xmlStream);

		DatabaseOperation.CLEAN_INSERT.execute(connection, dataset);
		LoggerFactory.getLogger(JpaBaseRolledBackTestCase.class).info("<< beginTransaction");
	}

	@AfterEach
	public void rollbackTransaction() {
		LoggerFactory.getLogger(JpaBaseRolledBackTestCase.class).info(">> rollbackTransaction");
		try {
			em.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		LoggerFactory.getLogger(JpaBaseRolledBackTestCase.class).info("<< rollbackTransaction");
	}

	public boolean commitTransaction() {
		LoggerFactory.getLogger(JpaBaseRolledBackTestCase.class).info(">> commitTransaction");
		boolean ret = false;
		try {
			em.getTransaction().commit();
			ret = true;
		} catch (Exception e) {
			if (em.getTransaction().isActive()) {
				logger.info("Rolling back");
				em.getTransaction().rollback();
				ret = false;
			}
		}
		LoggerFactory.getLogger(JpaBaseRolledBackTestCase.class).info("<< commitTransaction " + ret);
		return ret;
	}

	public void startTransaction() {
		LoggerFactory.getLogger(JpaBaseRolledBackTestCase.class).info(">> startTransaction");
		em.getTransaction().begin();
		LoggerFactory.getLogger(JpaBaseRolledBackTestCase.class).info("<< startTransaction");
	}

	protected void printDatabaseContent() {
		List<Season> findAllSeason = facadenFactory.getSeasonFacade().findAll();
		List<Spieler> findAllSpieler = facadenFactory.getSpielerFacade().findAll();
		List<Spiel> findAllSpiel = facadenFactory.getSpielFacade().findAll();
		List<Buchung> findAllBuchung = facadenFactory.getBuchungFacade().findAll();
		List<User> findAllUser = facadenFactory.getUserFacade().findAll();
		printSeasons(findAllSeason);
		printSpieler(findAllSpieler);
		printSpiele(findAllSpiel);
		printBuchungen(findAllBuchung);
		printUser(findAllUser);
	}

	protected void printSeasons(List<Season> findAll) {
		logger.info("Alle Seasons:");
		for (Season season : findAll) {
			logger.info(String.format("%1$3d %2$td.%2$tm.%2$tY - %3$td.%3$tm.%3$tY %4$-20s %5$5.2f EUR", season.getId(), season.getStartdate(),
					season.getEnddate(), season.getDescription(), season.getPrice()));
		}
	}

	protected void printSpieler(List<Spieler> findAll) {
		logger.info("Alle Spieler:");
		for (Spieler spieler : findAll) {
			logger.info(String.format("%1$3d %2$-20s", spieler.getId(), spieler.getName()));
		}
	}

	protected void printSpiele(List<Spiel> findAll) {
		logger.info("Alle Spiele:");
		for (Spiel spiel : findAll) {
			logger.info(String.format("%1$3d %2$td.%2$tm.%2$tY %3$-20s %4$5.2f EUR ", spiel.getId(), spiel.getDatum(), spiel.getSeason().getDescription(),
					spiel.getSeason().getPrice()));
		}
	}

	protected void printBuchungen(List<Buchung> findAll) {
		logger.info("Alle Buchungen:");
		for (Buchung buchung : findAll) {
			logger.info(String.format("%1$3d %2$td.%2$tm.%2$tY %3$-20s %4$-20s %5$5.2f EUR Spiel: %6$-2d", buchung.getId(), buchung.getDatum(), buchung
					.getSpieler().getName(), buchung.getDescription(), buchung.getPrice(), buchung.getSpiel().getId()));
		}
	}

	protected void printUser(List<User> findAll) {
		logger.info("Alle User:");
		for (User user : findAll) {
			logger.info(String.format("%1$3d %2$-20s %3$-22s %4$-20s %5$-20s %6$-20s %7$-20s ", user.getId(), user.getUserid(), user.getUsername(),
					user.getEmail(), user.getPassword(), user.getState(), user.getRegid()));
		}
	}

	protected void logTest(String string) {
		LoggerFactory.getLogger(getClass()).info(string);
	}
}
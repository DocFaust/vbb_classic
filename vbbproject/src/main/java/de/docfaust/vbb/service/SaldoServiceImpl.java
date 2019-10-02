package de.docfaust.vbb.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.docfaust.vbb.data.entity.Buchung;
import de.docfaust.vbb.data.entity.Spiel;
import de.docfaust.vbb.data.entity.Spieler;
import de.docfaust.vbb.data.facades.BuchungFacade;
import de.docfaust.vbb.data.facades.SpielerFacade;
import de.docfaust.vbb.model.SaldoModel;
import de.docfaust.vbb.model.SpielerSaldo;

/**
 * Implementation of the Saldo Service.
 * 
 * @author wfa339
 *
 */
@Dependent
public class SaldoServiceImpl implements SaldoService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4945075638691498985L;

	/**
	 * DB-Zugriff für Buchung.
	 */
	@EJB
	private BuchungFacade buchungFacade;

	/**
	 * DB-Zugriff für Spieler.
	 */
	@EJB
	private SpielerFacade spielerFacade;

	/**
	 * Logger.
	 */
	@Inject
	private Logger logger;

	/**
	 * CDI Usage.
	 */
	public SaldoServiceImpl() {
		
	}
	
	/**
	 * JUnit usage.
	 * 
	 * @param buchungFacade buchungFacade
	 * @param spielerFacade spielerFacade
	 */
	public SaldoServiceImpl(final BuchungFacade buchungFacade, final SpielerFacade spielerFacade) {
		super();
		this.buchungFacade = buchungFacade;
		this.spielerFacade = spielerFacade;
		logger = LoggerFactory.getLogger(getClass());
	}
	
	@Override
	public final SaldoModel getSaldo() {
		logger.debug("calculating Saldo");
		SaldoModel model = new SaldoModel();
		List<Spieler> names = spielerFacade.findSpieler();
		names.stream().forEach(spieler -> {
			BigDecimal saldo = spieler.getBuchungen().stream().map(Buchung::getPrice)
					.reduce(BigDecimal.ZERO, BigDecimal::add).setScale(2, RoundingMode.HALF_UP);
			model.getSpielersaldi().add(SpielerSaldo.of(spieler.getName(), saldo, spieler.getActivityLevel()));
		});
		model.setCompleteSaldo(getCompleteSaldo());
		logger.info(model.toString());
		return model;
	}

	/**
	 * Liefert das Komplette saldo über alle Daten.
	 * 
	 * @return Saldo
	 */
	@Override
	public BigDecimal getCompleteSaldo() {
		BigDecimal completeSaldo = buchungFacade.findAll().stream().map(Buchung::getPrice).reduce(BigDecimal.ZERO,
				BigDecimal::add);
		logger.debug("Komplettes Saldo: " + completeSaldo);
		return completeSaldo;
	}

	
	/**
	 * Zeigt an, ob der Saldo eines Spieles 0 ist.
	 * 
	 * @param spiel
	 *            zu prüfendes Spiel.
	 * @return true, wenn Saldo ausgeglichen
	 */
	@Override
	public boolean isSpielSaldoZero(final Spiel spiel) {
		boolean isSumZero;
		BigDecimal saldo = getSpielSaldo(spiel);
		if (saldo.compareTo(BigDecimal.ZERO) != 0) {
			logger.warn("Saldo Spiel " + spiel.toString() + " ist nicht Null");
			isSumZero = false;
		} else {
			isSumZero = true;
		}
		return isSumZero;
	}

	/**
	 * Liefert das Saldo eines Spieles.
	 * 
	 * @param spiel
	 *            , dessen Saldo gewollt ist.
	 * @return Saldo
	 */
	@Override
	public BigDecimal getSpielSaldo(final Spiel spiel) {
		List<Buchung> buchungen = spiel.getBuchungen();
		BigDecimal sum = buchungen.stream().map(Buchung::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
		logger.info("Saldo: " + sum);
		return sum;
	}


//	/**
//	 * Liefert eine Key Value Liste von Spielern mit deren Saldo.
//	 * 
//	 * @return Liats evon Saldi
//	 */
//	public final List<Entry<String, BigDecimal>> getSaldo() {
//		List<Spieler> names = spielerService.getSpieler();
//		Map<String, BigDecimal> saldi = new Hashtable<String, BigDecimal>();
//		
//		names.stream()
//			.sorted((s1, s2) -> s1.getActivityLevel() - s2.getActivityLevel())
//			.forEach(spieler -> {
//			BigDecimal saldo =  spieler.getBuchungen().stream()
//					.map(Buchung::getPrice)
//					.reduce(BigDecimal.ZERO, BigDecimal::add)
//					.setScale(2, RoundingMode.HALF_UP);
//
//			saldi.put(spieler.getName(), saldo);
//		});
//		
//		logger.info(saldi.toString());
//		
////		for (Spieler spieler : names) {
////
////			List<Buchung> buchungen = spieler.getBuchungen();
////			BigDecimal saldo =  buchungen.stream().map(Buchung::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
////			saldo.setScale(2, RoundingMode.HALF_UP);
////
////			saldi.put(spieler.getName(), saldo);
////		}
//		List<Entry<String, BigDecimal>> entries = new ArrayList<Entry<String, BigDecimal>>(saldi.entrySet());
//		return entries;
//	}

}

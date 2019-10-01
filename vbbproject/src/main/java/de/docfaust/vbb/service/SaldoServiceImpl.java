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
	private BigDecimal getCompleteSaldo() {
		BigDecimal completeSaldo = buchungFacade.findAll().stream().map(Buchung::getPrice).reduce(BigDecimal.ZERO,
				BigDecimal::add);
		logger.debug("Komplettes Saldo: " + completeSaldo);
		return completeSaldo;
	}

}

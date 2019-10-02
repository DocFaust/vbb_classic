package de.docfaust.vbb.service;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.docfaust.vbb.data.entity.Buchung;
import de.docfaust.vbb.data.facades.BuchungFacade;

@Dependent
public class BuchungServiceImpl implements BuchungService {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2294831781288340788L;

	/**
	 * Logger.
	 */
	@Inject
	private Logger logger;

	@EJB
	private BuchungFacade buchungFacade;

	
	
	public BuchungServiceImpl(final BuchungFacade buchungFacade) {
		super();
		this.buchungFacade = buchungFacade;
		this.logger = LoggerFactory.getLogger(getClass());
	}

	public BuchungServiceImpl() {
		super();
	}

	/**
	 * Gibt alle Buchungen zurück.
	 * 
	 * @return Liste der Buchungen.
	 */
	@Override
	public List<Buchung> getBuchungen() {
		return buchungFacade.findAll();
	}

	/**
	 * Löscht eine Buchung.
	 * 
	 * @param buchung zu Löschende Buchung.
	 */
	@Override
	public final void deleteBuchung(final Buchung buchung) {
		buchung.setSpiel(null);
		buchung.setSpieler(null);
		buchungFacade.remove(buchung);
	}

	/**
	 * Speichert eine Buchung.
	 * 
	 * @param buchung Zu speichernde Buchung
	 */
	@Override
	public final void saveBuchung(final Buchung buchung) {
		buchungFacade.createOrUpdate(buchung);
	}

	@Override
	public List<Buchung> getBuchungenBeforeDate(final Date date) {
		return buchungFacade.getBuchungenBeforeDate(date); 
	}
	
	/**
	 * Löscht alle Buchungen vor einem Datum.
	 * 
	 * @param datum
	 *            Datum
	 */
	@Override
	public void removeOldBuchungen(final Date datum) {
		logger.debug("Lösche alte Buchungen");
		List<Buchung> alteBuchungen = getBuchungenBeforeDate(datum);
		for (Buchung buchung : alteBuchungen) {
			buchung.getSpiel().removeBuchung(buchung);
			buchung.getSpieler().removeBuchung(buchung);
			deleteBuchung(buchung);
		}
	}


}

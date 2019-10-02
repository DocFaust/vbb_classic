package de.docfaust.vbb.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.EJB;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.docfaust.vbb.data.entity.Spieler;
import de.docfaust.vbb.data.facades.SpielerFacade;
import de.docfaust.vbb.util.messages.MessageConstants;
import de.docfaust.vbb.util.statusliste.Statusliste;

@Dependent
public class SpielerServiceImpl implements SpielerService {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5896783881790442053L;

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
	 * @param spielerFacade from JUnit
	 */
	public SpielerServiceImpl(final SpielerFacade spielerFacade) {
		super();
		this.spielerFacade = spielerFacade;
		this.logger = LoggerFactory.getLogger(getClass());
	}

	/**
	 * 
	 */
	public SpielerServiceImpl() {
		super();
	}

	@Override
	public final List<Spieler> getSpieler() {
		return spielerFacade.findSpieler();
	}

	/**
	 * Gibt die Namen der Spieler zurück.
	 * 
	 * @return Liste der Namen
	 */
	@Override
	public final List<String> getSpielerNames() {
		List<Spieler> spieler = spielerFacade.findSpieler();
		List<String> spielerNames = spieler.stream().map(Spieler::getName).collect(Collectors.toList());
		return spielerNames;
	}

	/**
	 * Speichert einen Spieler.
	 * 
	 * @param spieler
	 *            Zu speichernder Spieler.
	 */
	@Override
	public final void saveSpieler(final Spieler spieler) {
		spielerFacade.createOrUpdate(spieler);
	}

	/**
	 * Löscht einen Spieler.
	 * 
	 * @param spieler
	 *            Zu löschender Spieler
	 * @return Statusliste
	 */
	@Override
	public final Statusliste deleteSpieler(final Spieler spieler) {
		logger.debug("Lösche Spieler: " + spieler.getName());
		Statusliste liste = new Statusliste();
		if (!spieler.getBuchungen().isEmpty()) {
			liste.addStatus(MessageConstants.SPIELER_HAS_BUCHUNGEN, spieler.getName());
			logger.warn("Spieler hat noch Buchungen");
		} else {
			spielerFacade.remove(spieler);
		}
		return liste;
	}
	
	/**
	 * Increments the activity Level.
	 * @param spieler Spiler to increase.
	 */
	@Override
	public void incrementActivityLevel(final Spieler spieler) {
		int activityLevel = spieler.getActivityLevel();
		spieler.setActivityLevel(++activityLevel);
		spielerFacade.edit(spieler);
	}
	

}

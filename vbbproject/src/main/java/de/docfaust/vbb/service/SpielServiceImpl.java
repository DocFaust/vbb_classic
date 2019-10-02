package de.docfaust.vbb.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.docfaust.vbb.data.entity.Buchung;
import de.docfaust.vbb.data.entity.BusinessCase;
import de.docfaust.vbb.data.entity.Season;
import de.docfaust.vbb.data.entity.Spiel;
import de.docfaust.vbb.data.entity.Spieler;
import de.docfaust.vbb.data.facades.SpielFacade;
import de.docfaust.vbb.util.FacesHelper;
import de.docfaust.vbb.util.journal.Journal;
import de.docfaust.vbb.util.journal.annotations.JournalWriter;
import de.docfaust.vbb.util.messages.MessageConstants;
import de.docfaust.vbb.util.statusliste.Statusliste;
import de.docfaust.vbb.validation.ValidationFactory;

@Dependent
public class SpielServiceImpl implements SpielService {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7261699304641702847L;

	/**
	 * DB-Zugriff für Tokens.
	 */
	@EJB
	private SpielFacade spielFacade;

	@Inject
	private BuchungService buchungService;

	@Inject
	private SpielerService spielerService;
	
	@Inject
	private SeasonService seasonService;

	@Inject
	private MailService mailService;
	
	@Inject
	@JournalWriter
	private Journal journal;
	/**
	 * Logger.
	 */
	@Inject
	private Logger logger;
	
	/**
	 * @param spielFacade from JUnit
	 * @param buchungService from JUnit
	 * @param spielerService from JUnit
	 * @param seasonService from JUnit
	 * @param mailService from JUnit
	 * @param journal from JUnit
	 */
	public SpielServiceImpl(final SpielFacade spielFacade, final BuchungService buchungService, final SpielerService spielerService,
			final SeasonService seasonService, final MailService mailService, final Journal journal) {
		super();
		this.spielFacade = spielFacade;
		this.buchungService = buchungService;
		this.spielerService = spielerService;
		this.seasonService = seasonService;
		this.mailService = mailService;
		this.journal = journal;
		this.logger = LoggerFactory.getLogger(getClass());
	}

	/**
	 * 
	 */
	public SpielServiceImpl() {
		super();
	}

	/**
	 * Speichert ein Spiel. Es wird ermittelt, wie hoch der Preis pro Person
	 * ist, wenn die Summe nicht in möglichen Beträgen Aufgeht, so wird
	 * aufgerundet und der Bezahlende bekommt die Differenz
	 * 
	 * 
	 * <table>
	 * <tr>
	 * Liste der ReturnCodes:
	 * </tr>
	 * <tr>
	 * <th align="left">Returncode</th>
	 * <th align="left">Beschreibung</th>
	 * </tr>
	 * <tr>
	 * <td>SUCESSFUL</td>
	 * <td>Spiel ordnungsgemäß gespeichert</td>
	 * </tr>
	 * <tr>
	 * <td>ALREADYEXISTING</td>
	 * <td>Spiel existiert bereits für dieses Datum</td>
	 * </tr>
	 * <tr>
	 * <td>SEASON_NOT_FOUND</td>
	 * <td>Keine Season für das Spiel gefunden</td>
	 * </tr>
	 * <tr>
	 * <td>SEASON_NO_PRICE</td>
	 * <td>Die Saison hat keinen Preis</td>
	 * </tr>
	 * 
	 * </table>
	 * 
	 * @param spielerList
	 *            Liste der Spieler
	 * @param datum
	 *            Datum des Spiels
	 * @return Statusliste {@link Statusliste}
	 */
	@Override
	public final Statusliste saveSpiel(final List<Spieler> spielerList, final Date datum) {
		logger.info("Speichere Spieler: " + spielerList.toString());
		Statusliste statusliste = new Statusliste();
		if (spielFacade.getSpieleForDate(datum).isEmpty()) {
			Season season = seasonService.getSeason(datum);
			statusliste.addStatusliste(ValidationFactory.create().validate(season));
			if (statusliste.booleanValue()) {
				BigDecimal seasonprice = season.getPrice();
				Spiel spiel = new Spiel();
				spiel.setBuchungen(new ArrayList<Buchung>());
				spiel.setDatum(datum);
				spiel.setSeason(season);
				spielFacade.create(spiel);

				List<Buchung> buchungen = new ArrayList<Buchung>();
				int anwesendeSpieler = 0;
				String bezahlerName = null;
				for (Spieler spieler : spielerList) {
					logger.info(spieler.toString());
					if (spieler.isAnwesend()) {
						anwesendeSpieler++;
						Buchung buchung = new Buchung();
						buchung.setDatum(new Date());
						buchung.setSpieler(spieler);
						buchung.setDescription("Spieleinsatz");
						buchungService.saveBuchung(buchung);
						buchungen.add(buchung);
						spielerService.incrementActivityLevel(spieler);
					}

					if (spieler.isBezahlt()) {
						Buchung buchung = new Buchung();
						buchung.setDatum(new Date());
						buchung.setSpieler(spieler);
						buchung.setDescription("Bezahlung");
						buchung.setPrice(seasonprice);
						logger.info("Bezahlung " + spieler.getName() + ": " + buchung.getPrice());
						spiel.addBuchung(buchung);
						buchungService.saveBuchung(buchung);
						bezahlerName = buchung.getSpieler().getName();
					}
				}

				// Preis durch Anzahl anwesende Spieler, Auf 2 Nachkommastellen
				// aufrunden
				BigDecimal regularprice = seasonprice.divide(new BigDecimal(anwesendeSpieler), 2,
						BigDecimal.ROUND_CEILING);
				BigDecimal sum = regularprice.multiply(new BigDecimal(anwesendeSpieler));
				BigDecimal diff = sum.subtract(seasonprice);
				BigDecimal priceForZahler = regularprice.subtract(diff);

				for (Buchung buchung : buchungen) {
					logger.info("Bezahlername: " + bezahlerName);
					logger.info("Name aus Buchung: " + buchung.getSpieler().getName());
					if (buchung.getSpieler().getName().equals(bezahlerName)) {
						buchung.setPrice(priceForZahler.negate());
					} else {
						buchung.setPrice(regularprice.negate());
					}
					spiel.addBuchung(buchung);
					buchungService.saveBuchung(buchung);
				}
				mailService.sendSpielMail(spiel);
				
				String user = FacesHelper.getCurrentUserID();
				String description = "Spiel erzeugt: " + datum.toString();
				journal.writeJournal(BusinessCase.CREATE_GAME, user, description);
			}
		} else {
			statusliste.addStatus(MessageConstants.GAME_ALREADYEXISTING);
		}
		
		return statusliste;
	}
	
	/**
	 * Speichert ein Spiel.
	 * 
	 * @param spiel
	 *            zu speicherndes Spiel
	 * @return Statusliste
	 */
	@Override
	public Statusliste saveSpiel(final Spiel spiel) {
		spielFacade.createOrUpdate(spiel);
		Statusliste liste = Statusliste.create().addStatus(MessageConstants.GAME_SAVED);
		return liste;
	}

	@Override
	public final List<Spiel> getSpiele() {
		return spielFacade.findAll();
	}

	/**
	 * Löscht ein Spiel aus der Datenbank.
	 * 
	 * @param spiel
	 *            zu löschenden Spiel.
	 * @return ReturnCode
	 */
	@Override
	public final Statusliste deleteSpiel(final Spiel spiel) {
		return spielFacade.deleteSpiel(spiel);
	}

	/**
	 * Löscht alle Spiele vor einem Datum.
	 * 
	 * @param date
	 *            Datum
	 */
	@Override
	public void removeOldSpiele(final Date date) {
		logger.debug("Lösche alte Spiele");
		List<Spiel> oldSpiele = spielFacade.getSpieleBeforeDate(date);

		oldSpiele.stream()
				.filter(spiel -> !DateUtils.isSameDay(spiel.getDatum(), date)
						|| !spiel.getSeason().getDescription().equals("Buchungsschnitt"))
				.forEach(spiel -> spielFacade.remove(spiel));
	}


}

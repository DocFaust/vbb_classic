package de.docfaust.vbb.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import javax.ejb.EJB;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.docfaust.vbb.data.entity.Buchung;
import de.docfaust.vbb.data.entity.BusinessCase;
import de.docfaust.vbb.data.entity.Group;
import de.docfaust.vbb.data.entity.Mail;
import de.docfaust.vbb.data.entity.Season;
import de.docfaust.vbb.data.entity.Spiel;
import de.docfaust.vbb.data.entity.Spieler;
import de.docfaust.vbb.data.entity.User;
import de.docfaust.vbb.data.facades.BuchungFacade;
import de.docfaust.vbb.data.facades.GroupFacade;
import de.docfaust.vbb.data.facades.JournalFacade;
import de.docfaust.vbb.data.facades.MailFacade;
import de.docfaust.vbb.data.facades.SeasonFacade;
import de.docfaust.vbb.data.facades.SpielFacade;
import de.docfaust.vbb.data.facades.SpielerFacade;
import de.docfaust.vbb.data.facades.UserFacade;
import de.docfaust.vbb.data.factories.BuchungFactory;
import de.docfaust.vbb.data.factories.SeasonFactory;
import de.docfaust.vbb.data.factories.SpielFactory;
import de.docfaust.vbb.util.FacesHelper;
import de.docfaust.vbb.util.PasswordUtil;
import de.docfaust.vbb.util.RegistrationState;
import de.docfaust.vbb.util.configuration.MailConfiguration;
import de.docfaust.vbb.util.configuration.MailConfigurationDB;
import de.docfaust.vbb.util.configuration.MailConfigurationDBImpl;
import de.docfaust.vbb.util.journal.Journal;
import de.docfaust.vbb.util.journal.annotations.JournalWriter;
import de.docfaust.vbb.util.journal.impl.JournalDBWriter;
import de.docfaust.vbb.util.messages.MessageConstants;
import de.docfaust.vbb.util.statusliste.Statusliste;
import de.docfaust.vbb.util.templates.MailTemplates;
import de.docfaust.vbb.util.templates.RegisterTemplates;
import de.docfaust.vbb.util.templates.VelocityMailTemplate;
import de.docfaust.vbb.util.templates.VelocityMailTemplates;
import de.docfaust.vbb.util.templates.VelocityRegisterTemplate;
import de.docfaust.vbb.util.templates.VelocityRegisterTemplates;
import de.docfaust.vbb.validation.ValidationFactory;

/**
 * Session Bean implementation class VBBServices.
 */
@Dependent
public class VBBServices implements Serializable {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -8223435171027437924L;

	@EJB
	private MailSender sender;
	/**
	 * DB-Zugriff für Buchung.
	 */
	@EJB
	private BuchungFacade buchungFacade;

	/**
	 * DB-Zugriff für Season.
	 */
	@EJB
	private SeasonFacade seasonFacade;

	/**
	 * DB-Zugriff für User.
	 */
	@EJB
	private UserFacade userFacade;

	/**
	 * DB-Zugriff für Group.
	 */
	@EJB
	private GroupFacade groupFacade;

	/**
	 * DB-Zugriff für Spiel.
	 */
	@EJB
	private SpielFacade spielFacade;

	/**
	 * DB-Zugriff für Spieler.
	 */
	@EJB
	private SpielerFacade spielerFacade;

	/**
	 * DB-Zugriff für Mails.
	 */
	@EJB
	private MailFacade mailFacade;

	@Inject
	@JournalWriter
	private Journal journal;
	/**
	 * Logger.
	 */
	@Inject
	private Logger logger;

	@Inject
	@VelocityRegisterTemplate
	private RegisterTemplates templates;

	@Inject
	@VelocityMailTemplate
	private MailTemplates mailTemplates;

	@Inject
	@MailConfigurationDB
	private MailConfiguration mailConfig;

	/**
	 * Default constructor.
	 */
	public VBBServices() {
	}

	/**
	 * Konstruktor für Betrieb ohne EJB Context.
	 * 
	 * @param em
	 *            EntityManager
	 */
	public VBBServices(final EntityManager em) {
		this.buchungFacade = new BuchungFacade(em);
		this.setMailConfig(new MailConfigurationDBImpl(em));
		this.seasonFacade = new SeasonFacade(em);
		this.spielerFacade = new SpielerFacade(em);
		this.spielFacade = new SpielFacade(em);
		this.userFacade = new UserFacade(em);
		this.mailFacade = new MailFacade(em);
		this.groupFacade = new GroupFacade(em);
		this.templates = new VelocityRegisterTemplates();
		this.mailTemplates = new VelocityMailTemplates();
		this.logger = LoggerFactory.getLogger(getClass());
		this.journal = new JournalDBWriter(new JournalFacade(em));
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
	public final Statusliste saveSpiel(final List<Spieler> spielerList, final Date datum) {
		logger.info("Speichere Spieler: " + spielerList.toString());
		Statusliste statusliste = new Statusliste();
		if (spielFacade.getSpieleForDate(datum).isEmpty()) {
			Season season = getSeason(datum);
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
						buchungFacade.create(buchung);
						buchungen.add(buchung);
						incrementActivityLevel(spieler);
					}

					if (spieler.isBezahlt()) {
						Buchung buchung = new Buchung();
						buchung.setDatum(new Date());
						buchung.setSpieler(spieler);
						buchung.setDescription("Bezahlung");
						buchung.setPrice(seasonprice);
						logger.info("Bezahlung " + spieler.getName() + ": " + buchung.getPrice());
						spiel.addBuchung(buchung);
						buchungFacade.create(buchung);
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
					buchungFacade.createOrUpdate(buchung);
				}
				sendSpielMail(spiel);
				
				String user = FacesHelper.getCurrentUserID();
				String description = "Spiel erzeugt: " + datum.toString();
				journal.writeJournal(BusinessCase.CREATE_GAME, user, description);
			}
		} else {
			statusliste.addStatus(MessageConstants.GAME_ALREADYEXISTING);
		}
		
		return statusliste;
	}

	public void incrementActivityLevel(Spieler spieler) {
		int activityLevel = spieler.getActivityLevel();
		spieler.setActivityLevel(++activityLevel);
		spielerFacade.edit(spieler);
	}
	
	/**
	 * Liefert die Saison zu einem Datum.
	 * 
	 * @param datum
	 *            Datum
	 * @return Saison
	 */
	public Season getSeason(final Date datum) {
		logger.debug("Suche Saison für " + datum);
		Season season = seasonFacade.getSeasonFromDate(datum);
		return season;
	}

	/**
	 * Liefert das Komplette saldo über alle Daten.
	 * 
	 * @return Saldo
	 */
	public final BigDecimal getCompleteSaldo() {
		BigDecimal completeSaldo = BigDecimal.ZERO;

		List<Buchung> buchungen = buchungFacade.findAll();
		for (Buchung buchung : buchungen) {
			completeSaldo = completeSaldo.add(buchung.getPrice());
		}
		logger.debug("Komplettes Saldo: " + completeSaldo);
		return completeSaldo;
	}

	/**
	 * Liefert eine Key Value Liste von Spielern mit deren Saldo.
	 * 
	 * @return Liats evon Saldi
	 */
	public final List<Entry<String, BigDecimal>> getSaldo() {
		List<Spieler> names = getSpieler();
		Map<String, BigDecimal> saldi = new Hashtable<String, BigDecimal>();
		for (Spieler spieler : names) {

			List<Buchung> buchungen = spieler.getBuchungen();
			BigDecimal saldo = BigDecimal.ZERO;
			for (Buchung buchung : buchungen) {
				saldo = saldo.add(buchung.getPrice());
			}
			saldo.setScale(2, RoundingMode.HALF_UP);
			saldi.put(spieler.getName(), saldo);
		}
		List<Entry<String, BigDecimal>> entries = new ArrayList<Entry<String, BigDecimal>>(saldi.entrySet());
		return entries;
	}

	public final List<Spieler> getSpieler() {
		return spielerFacade.findSpieler();
	}

	/**
	 * Gibt die Liste der Spieler zurück.
	 * 
	 * @return Liste der Spieler
	 */
	public final List<Spieler> getSpielerModelList() {
		List<Spieler> spielerList = getSpieler();
		return spielerList;
	}

	/**
	 * Speichert eine Season.
	 * 
	 * @param season
	 *            Season
	 */
	public final void saveSeason(final Season season) {
		seasonFacade.createOrUpdate(season);
	}

	/**
	 * Löscht eine Season.
	 * 
	 * @param season
	 *            Season
	 * @return Returncode
	 */
	public final Statusliste deleteSaison(final Season season) {
		logger.info("Lösche Saison: " + season.getDescription());
		return seasonFacade.deleteSeason(season);
	}

	/**
	 * Registriert einen Benutzer.
	 * 
	 * @param user
	 *            Benutzer
	 * @return Returncode
	 */
	public final Statusliste register(final User user) {
		Statusliste statusliste = userFacade.checkEmail(user);
		if (statusliste.booleanValue()) {
			statusliste = userFacade.checkUserID(user);
		}
		if (statusliste.booleanValue()) {
			statusliste = userFacade.register(user);
		}
		return statusliste;
	}

	/**
	 * Meldet einen Benutzer an.
	 * 
	 * @param user
	 *            Benutzer
	 * @return Benutzerdaten
	 */
	public final User login(final User user) {
		User userEntity = userFacade.login(user);
		return userEntity;
	}

	/**
	 * Meldet einen Benutzer an.
	 * 
	 * @param userid
	 *            Benutzerid
	 * @param password
	 *            Passwort
	 * @return Benutzerdaten
	 */
	public final User login(final String userid, final String password) {
		User user = new User();
		user.setUserid(userid);
		String hash = PasswordUtil.encryptPassword(password);
		logger.debug(hash);
		user.setPassword(hash);
		return login(user);
	}

	public final List<Season> getSeasons() {
		return seasonFacade.findAll();
	}

	/**
	 * Speichert die Registrierungsmail in der Mail Tabelle, wo sie vom
	 * MailSender abgeholt wird.
	 * 
	 * @see MailSender
	 * @param user
	 *            Benutzer, dem die Mail geschickt werden soll.
	 */
	public final void sendRegistrationMail(final User user) {
		Mail mail = new Mail();
		mail.setRecipient(user.getEmail());
		mail.setSubject(getMailConfig().getRegistrationSubject());
		String text = mailTemplates.getRegisterMail(user, getMailConfig().getDomain());
		logger.debug(text);
		mail.setText(text);

		mailFacade.create(mail);

	}

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
	public final Statusliste deleteSpiel(final Spiel spiel) {
		return spielFacade.deleteSpiel(spiel);
	}

	/**
	 * Gibt die Namen der Spieler zurück.
	 * 
	 * @return Liste der Namen
	 */
	public final List<String> getSpielerNames() {
		List<Spieler> spieler = getSpieler();
		List<String> spielerNames = spieler.stream().map(Spieler::getName).collect(Collectors.toList());
		return spielerNames;
	}

	/**
	 * Speichert einen Spieler.
	 * 
	 * @param spieler
	 *            Zu speichernder Spieler.
	 */
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
	 * Zeigt an, ob der Saldo eines Spieles 0 ist.
	 * 
	 * @param spiel
	 *            zu prüfendes Spiel.
	 * @return true, wenn Saldo ausgeglichen
	 */
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
	public BigDecimal getSpielSaldo(final Spiel spiel) {
		List<Buchung> buchungen = spiel.getBuchungen();
		BigDecimal sum = buchungen.stream().map(Buchung::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
		logger.info("Saldo: " + sum);
		return sum;
	}

	/**
	 * Macht einen Buchungsschnitt zum aktuellen Datum. Es wird eine neue Saison
	 * erstellt und ein neues Spiel.
	 * 
	 * @param datum
	 *            Datum, zu dem der Buchungsschnitt stattfindet
	 * 
	 */
	private void createBuchungsschnitt(final Date datum) {
		logger.debug("Erstelle Buchungsschnitt");

		Season season = SeasonFactory.createSeason(datum, datum, SeasonFactory.DESC_BUCHUNGSSCHNITT, BigDecimal.ZERO);
		seasonFacade.create(season);

		Spiel spiel = SpielFactory.createSpiel(datum, season);
		spielFacade.create(spiel);

		getSpieler().stream().forEach(spieler -> {
			BigDecimal saldo = getSaldoForSpieler(spieler);
			Buchung buchung = BuchungFactory.createBuchung(datum, BuchungFactory.DESC_BUCHUNGSSCHNITT, saldo, spiel,
					spieler);
			buchungFacade.create(buchung);
			spieler.setActivityLevel(0);
			spielerFacade.edit(spieler);
		});

	}

	/**
	 * Löscht alle Seasons vor einem Datum.
	 * 
	 * @param date
	 *            Datum
	 */
	private void removeOldSeasons(final Date date) {
		logger.debug("Lösche alte Seasons");
		seasonFacade.getSeasonsBeforeDate(date).stream().forEach(season -> seasonFacade.remove(season));
	}

	/**
	 * Löscht alle Spiele vor einem Datum.
	 * 
	 * @param date
	 *            Datum
	 */
	private void removeOldSpiele(final Date date) {
		logger.debug("Lösche alte Spiele");
		List<Spiel> oldSpiele = spielFacade.getSpieleBeforeDate(date);

		oldSpiele.stream()
				.filter(spiel -> !DateUtils.isSameDay(spiel.getDatum(), date)
						|| !spiel.getSeason().getDescription().equals("Buchungsschnitt"))
				.forEach(spiel -> spielFacade.remove(spiel));
	}

	/**
	 * Löscht alle Buchungen vor einem Datum.
	 * 
	 * @param datum
	 *            Datum
	 */
	private void removeOldBuchungen(final Date datum) {
		logger.debug("Lösche alte Buchungen");
		List<Buchung> alteBuchungen = buchungFacade.getBuchungenBeforeDate(datum);
		for (Buchung buchung : alteBuchungen) {
			buchung.getSpiel().removeBuchung(buchung);
			buchung.getSpieler().removeBuchung(buchung);
			buchungFacade.remove(buchung);
		}
	}

	/**
	 * Führt einen kompletten Buchungsschnitt durch.
	 * 
	 * @param date
	 *            Datum zu dem der Buchungsschnitt durchgeführt werden soll.
	 */
	public void starteBuchungsschnitt(final Date date) {
		logger.info("Starte kompletten Buchungsschnitt zum " + date.toString());
		createBuchungsschnitt(date);
		removeOldBuchungen(date);
		removeOldSpiele(date);
		removeOldSeasons(date);
	}

	/**
	 * Liefert das Saldo für einen Spieler.
	 * 
	 * @param spieler
	 *            Spieler
	 * @return Saldo
	 */
	public final BigDecimal getSaldoForSpieler(final Spieler spieler) {
		List<Buchung> buchungen = spieler.getBuchungen();
		BigDecimal saldo = buchungen.stream().map(Buchung::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
		return saldo;
	}

	/**
	 * Gibt alle Buchungen zurück.
	 * 
	 * @return Liste der Buchungen.
	 */
	public List<Buchung> getBuchungen() {
		return buchungFacade.findAll();
	}

	/**
	 * Löscht eine Buchung.
	 * 
	 * @param buchung
	 *            zu Löschende Buchung.
	 */
	public final void deleteBuchung(final Buchung buchung) {
		buchung.setSpiel(null);
		buchung.setSpieler(null);
		buchungFacade.remove(buchung);
	}

	/**
	 * Speichert eine Buchung.
	 * 
	 * @param buchung
	 *            Zu speichernde Buchung
	 */
	public final void saveBuchung(final Buchung buchung) {
		buchungFacade.createOrUpdate(buchung);
	}

	/**
	 * Gibt alle Gruppen zurück.
	 * 
	 * @return liste der Gruppen
	 */
	public List<Group> getGroups() {
		return groupFacade.findAll();
	}

	/**
	 * Gibt alle User zurück.
	 * 
	 * @return Liste der User
	 */
	public List<User> getUsers() {
		return userFacade.findAll();
	}

	/**
	 * Löscht einen User.
	 * 
	 * @param user
	 *            Zu löschender User
	 */
	public void deleteUser(final User user) {
		user.setGroup(null);
		userFacade.remove(user);
	}

	/**
	 * Speichert einen User.
	 * 
	 * @param user
	 *            zu speichernder User
	 * @return Statusliste
	 * 
	 */
	public Statusliste saveUser(final User user) {
		logger.info("Speichere den User: " + user.toString());
		Statusliste statusliste = userFacade.checkEmail(user);
		statusliste.addStatusliste(userFacade.checkUserID(user));
		if (statusliste.booleanValue()) {
			userFacade.createOrUpdate(user);
			statusliste.addStatus(MessageConstants.USER_SAVED, user.getUserid());
		}
		return statusliste;
	}

	/**
	 * Sucht den aktuell angemeldeten User.
	 * 
	 * @param userid
	 *            Userid
	 * @return User
	 */
	public User getLoggedInUser(final String userid) {
		return userFacade.findByUserName(userid);
	}

	/**
	 * Verarbeitet die Registrierung.
	 * 
	 * @param regid
	 *            Registrierungsid.
	 * @param userid
	 *            Benutzerid.
	 * @return ServerResponse.
	 */
	public String processRegistration(final String regid, final String userid) {
		String serverResponse = null;
		if (userid != null) {
			User user = userFacade.findByUserName(userid);

			if (user == null) {
				// Keine Registrierung
				serverResponse = templates.getNotRegistered(userid);
			} else if (user.getRegid() == null || !user.getRegid().equals(regid)) {
				// Falsche Registrierung
				serverResponse = templates.getWrongID(user.getUserid());
			} else {
				// Registrierung gefunden
				if (RegistrationState.PROOFED != user.getState() && user.getGroup() == null) {
					// User ist noch keiner Gruppe zugeordnet
					Group group = groupFacade.findByName("READER");
					user.setGroup(group);
					String domain = getMailConfig().getDomain();
					serverResponse = templates.getOk(user.getUserid(), domain);
				} else {
					// User bereits registriert
					serverResponse = templates.getYetRegistered(user.getUserid());
				}
				user.setState(RegistrationState.PROOFED);
				userFacade.edit(user);
			}
		} else {
			serverResponse = templates.getWrongRequest();
		}
		return serverResponse;
	}

	/**
	 * Sendet eine Mail an alle Spieler, die am Spiel teilgenommen haben, um
	 * über den aktuellen Status zu informieren.
	 * 
	 * @param spiel
	 *            Spiel über das informiert werden soll.
	 */
	public void sendSpielMail(final Spiel spiel) {

		Map<String, String> emails = new Hashtable<>();
		List<String> spielerList = new ArrayList<>();
		for (Buchung buchung : spiel.getBuchungen()) {
			String email = buchung.getSpieler().getEmail();
			// Keine Doppelversendung und es muss eine Mailadresse vorhanden
			// sein
			if (StringUtils.isEmpty(email) || spielerList.contains(email)) {
				continue;
			} else {
				spielerList.add(email);
			}

			String htmlMail = mailTemplates.getSaldoMail(spiel, buchung.getSpieler().getName(), getSaldo(),
					getCompleteSaldo());
			emails.put(email, htmlMail);
			Mail mail = new Mail();
			mail.setRecipient(email);
			mail.setSubject("Saldo des Spiels vom " + new SimpleDateFormat("dd.MM.yyyy").format(spiel.getDatum()));
			mail.setText(htmlMail);
			mailFacade.create(mail);
		}
	}

	/**
	 * Speichert ein Spiel.
	 * 
	 * @param spiel
	 *            zu speicherndes Spiel
	 * @return Statusliste
	 */
	public Statusliste saveSpiel(final Spiel spiel) {
		spielFacade.createOrUpdate(spiel);
		Statusliste liste = Statusliste.create().addStatus(MessageConstants.GAME_SAVED);
		return liste;
	}

	public MailConfiguration getMailConfig() {
		return mailConfig;
	}

	public void setMailConfig(final MailConfiguration mailConfig) {
		this.mailConfig = mailConfig;
	}
}

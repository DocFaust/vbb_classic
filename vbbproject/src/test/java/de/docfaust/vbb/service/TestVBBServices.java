package de.docfaust.vbb.service;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map.Entry;

import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

import de.docfaust.vbb.data.entity.Buchung;
import de.docfaust.vbb.data.entity.Group;
import de.docfaust.vbb.data.entity.Mail;
import de.docfaust.vbb.data.entity.Season;
import de.docfaust.vbb.data.entity.Spiel;
import de.docfaust.vbb.data.entity.User;
import de.docfaust.vbb.data.facades.UserFacade;
import de.docfaust.vbb.data.util.JpaBaseRolledBackTestCase;
import de.docfaust.vbb.util.EntityFactory;
import de.docfaust.vbb.util.PasswordUtil;
import de.docfaust.vbb.util.RegistrationState;
import de.docfaust.vbb.util.statusliste.Statusliste;

public class TestVBBServices extends JpaBaseRolledBackTestCase {
	private static final String HANS_HOHLBIRNE = "Hans Hohlbirne";
	private static final String GRETCHEN_GROEHL = "Gretchen Gröhl";
	private static final String FRANZ_FROEHLICH = "Franz Fröhlich";
	private static final String ERICH_EHRLICH = "Erich Ehrlich";
	private static final String DOREEN_DURSTIG = "Doreen Durstig";
	private static final String CLAUS_CASPAR = "Claus Caspar";
	private static final String BERND_BROT = "Bernd Brot";
	private static final String ALFRED_ALTMANN = "Alfred Altmann";
	private static final String INES_IGNORANT = "Ines Ignorant";
	private static final String JOHANN_JOCHBEIN = "Johann Jochbein";
// FIXME Write new
//	@Test
//	public void testStartBuchungssschnitt() {
//		VBBServices services = new VBBServices(em);
//
//		
//		services.starteBuchungsschnitt(new Date());
//		
//
//		printDatabaseContent();
//
//		List<Buchung> allBuchungen = services.getBuchungen();
//		assertEquals(10, allBuchungen.size());
//		for (Buchung buchung : allBuchungen) {
//			if (ALFRED_ALTMANN.equals(buchung.getSpieler().getName())) {
//				assertEquals(-7, buchung.getPrice().intValue());
//			}
//			if (BERND_BROT.equals(buchung.getSpieler().getName())) {
//				assertEquals(-7, buchung.getPrice().intValue());
//			}
//			if (CLAUS_CASPAR.equals(buchung.getSpieler().getName())) {
//				assertThat(buchung.getPrice().floatValue(), equalTo(1.5F));
//			}
//			if (DOREEN_DURSTIG.equals(buchung.getSpieler().getName())) {
//				assertEquals(-4, buchung.getPrice().intValue());
//			}
//			if (ERICH_EHRLICH.equals(buchung.getSpieler().getName())) {
//				assertEquals(8, buchung.getPrice().intValue());
//			}
//			if (FRANZ_FROEHLICH.equals(buchung.getSpieler().getName())) {
//				assertEquals(-2, buchung.getPrice().intValue());
//			}
//			if (GRETCHEN_GROEHL.equals(buchung.getSpieler().getName())) {
//				assertEquals(-2, buchung.getPrice().intValue());
//			}
//			if (HANS_HOHLBIRNE.equals(buchung.getSpieler().getName())) {
//				assertEquals(-2, buchung.getPrice().intValue());
//			}
//			if (INES_IGNORANT.equals(buchung.getSpieler().getName())) {
//				assertEquals(7, buchung.getPrice().intValue());
//			}
//			if (JOHANN_JOCHBEIN.equals(buchung.getSpieler().getName())) {
//				assertEquals(9, buchung.getPrice().intValue());
//			}
//		}
//
//		List<Season> allSeasons = services.getSeasons();
//		assertThat(allSeasons.size(), equalTo(1));
//
//		List<Spiel> allSpiele = services.getSpiele();
//		assertEquals(1, allSpiele.size());
//
//	}

//	@Test
//	public void testSaveSpiel() {
//		VBBServices services = new VBBServices(em);
//		Date date = new Date();
//		Season season = EntityFactory.createSeason(EntityFactory.getActualDateAddDays(0), EntityFactory.getActualDateAddDays(2), BigDecimal.TEN,
//				"Aktuelle Saison");
//
//		services.saveSeason(season);
//		
//		List<Spieler> spielerList = new ArrayList<Spieler>();
//		Spieler spieler1 = facadenFactory.getSpielerFacade().findByName("Alfred Altmann");
//		spieler1.setAnwesend(true);
//		spieler1.setBezahlt(true);
//		spielerList.add(spieler1);
//
//		Spieler spieler2 = facadenFactory.getSpielerFacade().findByName("Claus Caspar");
//		spieler2.setAnwesend(true);
//		spieler2.setBezahlt(false);
//		spielerList.add(spieler2);
//
//		services.saveSpiel(spielerList, date);
//
//		List<Spiel> allSpiele = services.getSpiele();
//		assertThat(allSpiele.size(), equalTo(5));
//		printDatabaseContent();
//		List<Buchung> allBuchungen = services.getBuchungen();
//		assertEquals(34, allBuchungen.size());
//	}

//	@Test
//	public void testGetSaldo() {
//		VBBServices services = new VBBServices(em);
//		List<Entry<String, BigDecimal>> saldos = services.getSaldo();
//		for (Entry<String, BigDecimal> entry : saldos) {
//			String name = entry.getKey();
//			BigDecimal saldo = entry.getValue();
//			logger.info(String.format("%1$22s %2$6.2f", name, saldo));
//			if (name.equals(ALFRED_ALTMANN)) {
//				assertEquals(-7, saldo.intValue());
//			}
//			if (name.equals(BERND_BROT)) {
//				assertEquals(-7, saldo.intValue());
//			}
//			if (name.equals(CLAUS_CASPAR)) {
//				assertThat(saldo.floatValue(), equalTo(1.5F));
//			}
//			if (name.equals(DOREEN_DURSTIG)) {
//				assertEquals(-4, saldo.intValue());
//			}
//			if (name.equals(ERICH_EHRLICH)) {
//				assertEquals(8, saldo.intValue());
//			}
//			if (name.equals(FRANZ_FROEHLICH)) {
//				assertEquals(-2, saldo.intValue());
//			}
//			if (name.equals(GRETCHEN_GROEHL)) {
//				assertEquals(-2, saldo.intValue());
//			}
//			if (name.equals(HANS_HOHLBIRNE)) {
//				assertEquals(-2, saldo.intValue());
//			}
//			if (name.equals(INES_IGNORANT)) {
//				assertEquals(7, saldo.intValue());
//			}
//			if (name.equals(JOHANN_JOCHBEIN)) {
//				assertEquals(9, saldo.intValue());
//			}
//		}
//	}
//
//	@Test
//	public void testGetCompleteSaldo() {
//		VBBServices services = new VBBServices(em);
//		BigDecimal completeSaldo = services.getCompleteSaldo();
//		assertEquals(0, completeSaldo.intValue());
//	}
//
//	@Test
//	public void testSendRegistrationMail() {
//		VBBServices services = new VBBServices(em);
//		User user = new User();
//		user.setUserid("wfaust");
//		user.setUsername("Werner Faust");
//		user.setState(RegistrationState.OPEN);
//		user.setEmail("wfaust@gmx.de");
//		user.setRegid("a");
//		try {
//			services.sendRegistrationMail(user);
//		} catch (Exception e) {
//			logger.error("Fehler", e);
//			fail("Fehler beim Verschicken der Mail " + e.getMessage());
//		}
//	}
//
//	@Test
//	public void testIsSpielSaldoZeroTrue() {
//		VBBServices services = new VBBServices(em);
//		List<Spiel> spiele = services.getSpiele();
//		for (Spiel spiel : spiele) {
//			assertTrue("Falsch bei: " + spiel.getDatum().toString(), services.isSpielSaldoZero(spiel));
//		}
//	}
//
//	@Test
//	public void testIsSpielSaldoZeroFalse() throws ParseException {
//		VBBServices services = new VBBServices(em);
//
//		Season season = EntityFactory.createSeason("22.02.2015", "22.02.2015", 1, "jaja");
//		Spiel spiel = EntityFactory.createSpiel("22.02.2015");
//		season.addSpiel(spiel);
//		Buchung buchung = new Buchung();
//		buchung.setPrice(new BigDecimal(1));
//		buchung.setDatum(new SimpleDateFormat("dd.MM.yyyy").parse("22.02.2015"));
//		buchung.setSpieler(facadenFactory.getSpielerFacade().find(1));
//		buchung.setDescription("EInzige Buchung");
//		spiel.addBuchung(buchung);
//		
//		facadenFactory.getSeasonFacade().create(season);
//		facadenFactory.getSpielFacade().create(spiel);
//		services.saveBuchung(buchung);
//		
//		printDatabaseContent();
//		logger.info(services.getSpielSaldo(spiel).toString());
//		assertFalse(services.isSpielSaldoZero(spiel));
//	}
//
//	@Test
//	public void testGetSpielSaldo() {
//		VBBServices services = new VBBServices(em);
//		List<Spiel> spiele = services.getSpiele();
//		for (Spiel spiel : spiele) {
//			assertEquals("Falsch bei: " + spiel.getDatum().toString(), 0, services.getSpielSaldo(spiel).intValue());
//		}
//	}
//
////	@Test
////	public void testGetSpielerNames() {
////		VBBServices services = new VBBServices(em);
////		List<String> spielerModelList = services.getSpielerNames();
////		assertEquals(10, spielerModelList.size());
////	}
////	
//	@Test
//	public void testSendSpiel() {
//		int count = facadenFactory.getMailFacade().count();
//		VBBServices services = new VBBServices(em);
//		Spiel spiel = facadenFactory.getSpielFacade().find(1);
//		
//		services.sendSpielMail(spiel);
//		
//		List<Mail> list = facadenFactory.getMailFacade().findAll();
//		list.forEach(mail -> logger.info(mail.getText()));
//		System.out.println(list);
//		assertThat(facadenFactory.getMailFacade().count(), equalTo(count + spiel.getBuchungen().size()-1));
//	}
//	
////	@Test
////	public void testDeleteSaison()
////	{
////		VBBServices services = new VBBServices(em);
////		assertThat(facadenFactory.getSeasonFacade().count(), equalTo(3));
////		Season season = facadenFactory.getSeasonFacade().find(3);
////		Statusliste statusliste = services.deleteSaison(season);
////		assertTrue(statusliste.booleanValue());
////		assertThat(facadenFactory.getSeasonFacade().count(), equalTo(2));
////	}
//	
//	@Test
//	public void testRegister()
//	{
//		VBBServices services = new VBBServices(em);
//		assertThat(facadenFactory.getUserFacade().count(), equalTo(2));
//		String username = "Alfred";
//		String userid = "aalfred";
//		String email = "a@b.c";
//		String password = "asb";
//		String regid = "aa";
//		RegistrationState state = RegistrationState.OPEN;
//		User user = EntityFactory.createUser(userid, username,email, password, state, regid);
//		Statusliste statusliste = services.register(user);
//		assertTrue(statusliste.booleanValue());
//		assertThat(facadenFactory.getUserFacade().count(), equalTo(3));
//	}
//	
//	@Test
//	public void testRegisterExisting()
//	{
//		VBBServices services = new VBBServices(em);
//		assertThat(facadenFactory.getUserFacade().count(), equalTo(2));
//		String username = "Alfred Altmann";
//		String userid = "aaltmann";
//		String email = "aaltmann@mail.de";
//		String password = "wwwwwwww";
//		RegistrationState state = RegistrationState.OPEN;
//		String regid = "a";
//		User user = EntityFactory.createUser(userid, username,email, password, state, regid);
//		Statusliste statusliste = services.register(user);
//		assertFalse(statusliste.booleanValue());
//		assertThat(facadenFactory.getUserFacade().count(), equalTo(2));
//	}
//
//	@Test
//	public void testLogin()
//	{
//		VBBServices services = new VBBServices(em);
//		UserFacade userFacade = facadenFactory.getUserFacade();
//		String userid = "ccltmann";
//		String passwort = "12345678";
//		String name = "Alfred Altmann";
//		String email = "ccltmann@gmiks.de";
//		RegistrationState state = RegistrationState.OPEN;
//		String regid = "1";
//
//		User user = EntityFactory.createUser(userid, name, email, passwort, state, regid);
//		
//		userFacade.create(user);
//
//		User user2 = createUser(userid, passwort);
//		User lu = services.login(user2);
//		assertThat(lu, not(nullValue()));
//		assertThat(lu.getUsername(), equalTo(name));
//		assertThat(lu.getEmail(), equalTo(email));
//		assertThat(lu.getState(), equalTo(state));
//	}
//
//	@Test
//	public void testLoginUnsuccessful()
//	{
//		VBBServices services = new VBBServices(em);
//		UserFacade userFacade = facadenFactory.getUserFacade();
//		String userid = "ccltmann";
//		String passwort = "12345678";
//		String name = "Alfred Altmann";
//		String email = "ccltmann@gmiks.de";
//		RegistrationState state = RegistrationState.OPEN;
//		String regid = "1";
//
//		User user = EntityFactory.createUser(userid, name, email, passwort, state, regid);
//		
//		userFacade.create(user);
//
//		User user2 = createUser(userid, "falsches PW");
//		User lu = services.login(user2);
//		assertThat(lu, nullValue());
//	}
//	@Test
//	public void testLogin2()
//	{
//		VBBServices services = new VBBServices(em);
//		UserFacade userFacade = facadenFactory.getUserFacade();
//		String userid = "ccltmann";
//		String passwort = "12345678";
//		String name = "Alfred Altmann";
//		String email = "ccltmann@gmiks.de";
//		RegistrationState state = RegistrationState.OPEN;
//		String regid = "1";
//
//		User user = EntityFactory.createUser(userid, name, email, passwort, state, regid);
//		
//		userFacade.create(user);
//
//		User lu = services.login(userid, passwort);
//		assertThat(lu, not(nullValue()));
//		assertThat(lu.getUsername(), equalTo(name));
//		assertThat(lu.getEmail(), equalTo(email));
//		assertThat(lu.getState(), equalTo(state));
//	}
//
//	@Test
//	public void testLogin2Unsuccessful()
//	{
//		VBBServices services = new VBBServices(em);
//		UserFacade userFacade = facadenFactory.getUserFacade();
//		String userid = "ccltmann";
//		String passwort = "12345678";
//		String name = "Alfred Altmann";
//		String email = "ccltmann@gmiks.de";
//		RegistrationState state = RegistrationState.OPEN;
//		String regid = "1";
//
//		User user = EntityFactory.createUser(userid, name, email, passwort, state, regid);
//		
//		userFacade.create(user);
//
//		User lu = services.login(userid, "falsches PW");
//		assertThat(lu, nullValue());
//	}
//	@Test
//	public void testDeleteSpiel()
//	{
//		VBBServices services = new VBBServices(em);
//		assertThat(facadenFactory.getSpielFacade().count(), equalTo(4));
//		assertThat(facadenFactory.getBuchungFacade().count(), equalTo(31));
//		assertThat(facadenFactory.getSeasonFacade().count(), equalTo(3));
//		assertThat(facadenFactory.getSpielerFacade().count(), equalTo(10));
//
//		Spiel spiel = facadenFactory.getSpielFacade().find(1);
//		
//		services.deleteSpiel(spiel);
//		
//		assertThat(facadenFactory.getSpielFacade().count(), equalTo(3));
//		assertThat(facadenFactory.getBuchungFacade().count(), equalTo(27));
//		assertThat(facadenFactory.getSeasonFacade().count(), equalTo(3));
//		assertThat(facadenFactory.getSpielerFacade().count(), equalTo(10));
//	}
//
//	private User createUser(final String userid, final String passwort) {
//		return EntityFactory.createUser(userid, null, null, passwort, null, null);
//	}
//	
//	
////	@Test
////	public void testSaveSpieler(){
////		VBBServices services = new VBBServices(em);
////		assertThat(facadenFactory.getSpielFacade().count(), equalTo(4));
////		assertThat(facadenFactory.getBuchungFacade().count(), equalTo(31));
////		assertThat(facadenFactory.getSeasonFacade().count(), equalTo(3));
////		assertThat(facadenFactory.getSpielerFacade().count(), equalTo(10));
////		
////		Spieler spieler = EntityFactory.createSpieler(false, "Theodor Test", "ttest@test.de", false);
////		
////		services.saveSpieler(spieler);
////		
////		assertThat(facadenFactory.getSpielFacade().count(), equalTo(4));
////		assertThat(facadenFactory.getBuchungFacade().count(), equalTo(31));
////		assertThat(facadenFactory.getSeasonFacade().count(), equalTo(3));
////		assertThat(facadenFactory.getSpielerFacade().count(), equalTo(11));
////	}
////	
////	@Test
////	public void testDeleteSpielerNichtMöglich(){
////		VBBServices services = new VBBServices(em);
////		assertThat(facadenFactory.getSpielFacade().count(), equalTo(4));
////		assertThat(facadenFactory.getBuchungFacade().count(), equalTo(31));
////		assertThat(facadenFactory.getSeasonFacade().count(), equalTo(3));
////		assertThat(facadenFactory.getSpielerFacade().count(), equalTo(10));
////		
////		Spieler spieler = facadenFactory.getSpielerFacade().find(1);
////		
////		Statusliste liste = services.deleteSpieler(spieler);
////		
////		assertFalse(liste.booleanValue());
////		assertThat(facadenFactory.getSpielFacade().count(), equalTo(4));
////		assertThat(facadenFactory.getBuchungFacade().count(), equalTo(31));
////		assertThat(facadenFactory.getSeasonFacade().count(), equalTo(3));
////		assertThat(facadenFactory.getSpielerFacade().count(), equalTo(10));
////	}
////	
////	@Test
////	public void testDeleteSpielerOhneBuchungen(){
////		VBBServices services = new VBBServices(em);
////		assertThat(facadenFactory.getSpielFacade().count(), equalTo(4));
////		assertThat(facadenFactory.getBuchungFacade().count(), equalTo(31));
////		assertThat(facadenFactory.getSeasonFacade().count(), equalTo(3));
////		assertThat(facadenFactory.getSpielerFacade().count(), equalTo(10));
////		
////		Spieler spieler = facadenFactory.getSpielerFacade().find(1);
////		
////		spieler.getBuchungen().forEach(buchung -> facadenFactory.getBuchungFacade().remove(buchung));
////		spieler.getBuchungen().clear();
////		
////		Statusliste liste = services.deleteSpieler(spieler);
////		
////		assertTrue(liste.booleanValue());
////		assertThat(facadenFactory.getSpielFacade().count(), equalTo(4));
////		assertThat(facadenFactory.getBuchungFacade().count(), equalTo(27));
////		assertThat(facadenFactory.getSeasonFacade().count(), equalTo(3));
////		assertThat(facadenFactory.getSpielerFacade().count(), equalTo(9));
////	}
////
//	
//	@Test
//	public void testDeleteBuchung(){
//		VBBServices services = new VBBServices(em);
//		assertThat(facadenFactory.getSpielFacade().count(), equalTo(4));
//		assertThat(facadenFactory.getBuchungFacade().count(), equalTo(31));
//		assertThat(facadenFactory.getSeasonFacade().count(), equalTo(3));
//		assertThat(facadenFactory.getSpielerFacade().count(), equalTo(10));
//		Buchung buchung = facadenFactory.getBuchungFacade().find(1);
//		
//		services.deleteBuchung(buchung);
//		
//		assertThat(facadenFactory.getSpielFacade().count(), equalTo(4));
//		assertThat(facadenFactory.getBuchungFacade().count(), equalTo(30));
//		assertThat(facadenFactory.getSeasonFacade().count(), equalTo(3));
//		assertThat(facadenFactory.getSpielerFacade().count(), equalTo(10));
//		
//	}
//	
//	@Test
//	public void testGetGroups(){
//		VBBServices services = new VBBServices(em);
//		assertThat(facadenFactory.getSpielFacade().count(), equalTo(4));
//		assertThat(facadenFactory.getBuchungFacade().count(), equalTo(31));
//		assertThat(facadenFactory.getSeasonFacade().count(), equalTo(3));
//		assertThat(facadenFactory.getSpielerFacade().count(), equalTo(10));
//		assertThat(facadenFactory.getGroupFacade().count(), equalTo(3));
//		List<Group> groups = services.getGroups();
//		
//		assertThat(groups.size(), equalTo(facadenFactory.getGroupFacade().count()));
//	}
//	
//	@Test
//	public void testGetUsers(){
//		VBBServices services = new VBBServices(em);
//		assertThat(facadenFactory.getSpielFacade().count(), equalTo(4));
//		assertThat(facadenFactory.getBuchungFacade().count(), equalTo(31));
//		assertThat(facadenFactory.getSeasonFacade().count(), equalTo(3));
//		assertThat(facadenFactory.getSpielerFacade().count(), equalTo(10));
//		assertThat(facadenFactory.getGroupFacade().count(), equalTo(3));
//		assertThat(facadenFactory.getUserFacade().count(), equalTo(2));
//		List<User> users = services.getUsers();
//		
//		assertThat(users.size(), equalTo(facadenFactory.getUserFacade().count()));
//	}
//	
//	@Test
//	public void testDeleteUser(){
//		VBBServices services = new VBBServices(em);
//		assertThat(facadenFactory.getSpielFacade().count(), equalTo(4));
//		assertThat(facadenFactory.getBuchungFacade().count(), equalTo(31));
//		assertThat(facadenFactory.getSeasonFacade().count(), equalTo(3));
//		assertThat(facadenFactory.getSpielerFacade().count(), equalTo(10));
//		assertThat(facadenFactory.getGroupFacade().count(), equalTo(3));
//		assertThat(facadenFactory.getUserFacade().count(), equalTo(2));
//		
//		User user = facadenFactory.getUserFacade().find(1);
//		services.deleteUser(user);
//		
//		assertThat(facadenFactory.getSpielFacade().count(), equalTo(4));
//		assertThat(facadenFactory.getBuchungFacade().count(), equalTo(31));
//		assertThat(facadenFactory.getSeasonFacade().count(), equalTo(3));
//		assertThat(facadenFactory.getSpielerFacade().count(), equalTo(10));
//		assertThat(facadenFactory.getGroupFacade().count(), equalTo(3));
//		assertThat(facadenFactory.getUserFacade().count(), equalTo(1));
//
//	}
//	
//	@Test
//	public void testSaveUser(){
//		VBBServices services = new VBBServices(em);
//		assertThat(facadenFactory.getSpielFacade().count(), equalTo(4));
//		assertThat(facadenFactory.getBuchungFacade().count(), equalTo(31));
//		assertThat(facadenFactory.getSeasonFacade().count(), equalTo(3));
//		assertThat(facadenFactory.getSpielerFacade().count(), equalTo(10));
//		assertThat(facadenFactory.getGroupFacade().count(), equalTo(3));
//		assertThat(facadenFactory.getUserFacade().count(), equalTo(2));
//		
//		UserFacade userFacade = facadenFactory.getUserFacade();
//		String userid = "ccltmann";
//		String passwort = "12345678";
//		String name = "Alfred Altmann";
//		String email = "ccltmann@gmiks.de";
//		RegistrationState state = RegistrationState.OPEN;
//		String regid = "1";
//
//		User user = EntityFactory.createUser(userid, name, email, passwort, state, regid);
//		
//		services.saveUser(user);
//		
//		int id = user.getId();
//		
//		User u2 = userFacade.find(id);
//		assertThat(u2.getUserid(), equalTo(userid));
//		assertThat(u2.getPassword(), equalTo(PasswordUtil.encryptPassword(passwort)));
//		assertThat(u2.getUsername(), equalTo(name));
//		assertThat(u2.getEmail(), equalTo(email));
//		assertThat(u2.getState(), equalTo(state));
//		assertThat(u2.getRegid(), equalTo(regid));
//		
//		
//		assertThat(facadenFactory.getSpielFacade().count(), equalTo(4));
//		assertThat(facadenFactory.getBuchungFacade().count(), equalTo(31));
//		assertThat(facadenFactory.getSeasonFacade().count(), equalTo(3));
//		assertThat(facadenFactory.getSpielerFacade().count(), equalTo(10));
//		assertThat(facadenFactory.getGroupFacade().count(), equalTo(3));
//		assertThat(facadenFactory.getUserFacade().count(), equalTo(3));
//	}
//	
//	@Test
//	public void testGetLoggedInUser(){
//		VBBServices services = new VBBServices(em);
//		assertThat(facadenFactory.getSpielFacade().count(), equalTo(4));
//		assertThat(facadenFactory.getBuchungFacade().count(), equalTo(31));
//		assertThat(facadenFactory.getSeasonFacade().count(), equalTo(3));
//		assertThat(facadenFactory.getSpielerFacade().count(), equalTo(10));
//		assertThat(facadenFactory.getGroupFacade().count(), equalTo(3));
//		assertThat(facadenFactory.getUserFacade().count(), equalTo(2));
//
//		User user = services.getLoggedInUser("aaltmann");
//		assertThat(user.getUsername(), equalTo("Alfred Altmann"));
//		
//	}
//	
////	@Test
////	public void testgetSpieler(){
////		VBBServices services = new VBBServices(em);
////		assertThat(facadenFactory.getSpielFacade().count(), equalTo(4));
////		assertThat(facadenFactory.getBuchungFacade().count(), equalTo(31));
////		assertThat(facadenFactory.getSeasonFacade().count(), equalTo(3));
////		assertThat(facadenFactory.getSpielerFacade().count(), equalTo(10));
////		assertThat(facadenFactory.getGroupFacade().count(), equalTo(3));
////		assertThat(facadenFactory.getUserFacade().count(), equalTo(2));
////
////		List<Spieler> spieler = services.getSpieler();
////		
////		assertThat(spieler.size(), equalTo(10));
////		assertThat(spieler, equalTo(facadenFactory.getSpielerFacade().findAll()));
////	}
////	
//	@Test
//	public void testProcessRegistrationYetRegistered(){
//		VBBServices services = new VBBServices(em);
//		assertThat(facadenFactory.getSpielFacade().count(), equalTo(4));
//		assertThat(facadenFactory.getBuchungFacade().count(), equalTo(31));
//		assertThat(facadenFactory.getSeasonFacade().count(), equalTo(3));
//		assertThat(facadenFactory.getSpielerFacade().count(), equalTo(10));
//		assertThat(facadenFactory.getGroupFacade().count(), equalTo(3));
//		assertThat(facadenFactory.getUserFacade().count(), equalTo(2));
//
//		String registration = services.processRegistration("a", "aaltmann");
//		LoggerFactory.getLogger(getClass()).info(registration);
//		assertThat(registration, containsString("Bereits registriert"));
//	}
//	@Test
//	public void testProcessRegistrationWrongRegid(){
//		VBBServices services = new VBBServices(em);
//		assertThat(facadenFactory.getSpielFacade().count(), equalTo(4));
//		assertThat(facadenFactory.getBuchungFacade().count(), equalTo(31));
//		assertThat(facadenFactory.getSeasonFacade().count(), equalTo(3));
//		assertThat(facadenFactory.getSpielerFacade().count(), equalTo(10));
//		assertThat(facadenFactory.getGroupFacade().count(), equalTo(3));
//		assertThat(facadenFactory.getUserFacade().count(), equalTo(2));
//
//		String registration = services.processRegistration("abc", "aaltmann");
//		LoggerFactory.getLogger(getClass()).info(registration);
//		assertThat(registration, containsString("Falsche ID"));
//	}
//	@Test
//	public void testProcessRegistrationOk(){
//		VBBServices services = new VBBServices(em);
//		assertThat(facadenFactory.getSpielFacade().count(), equalTo(4));
//		assertThat(facadenFactory.getBuchungFacade().count(), equalTo(31));
//		assertThat(facadenFactory.getSeasonFacade().count(), equalTo(3));
//		assertThat(facadenFactory.getSpielerFacade().count(), equalTo(10));
//		assertThat(facadenFactory.getGroupFacade().count(), equalTo(3));
//		assertThat(facadenFactory.getUserFacade().count(), equalTo(2));
//		String userid = "ccltmann";
//		String passwort = "12345678";
//		String name = "Alfred Altmann";
//		String email = "ccltmann@gmiks.de";
//		RegistrationState state = RegistrationState.OPEN;
//		String regid = "1";
//
//		User user = EntityFactory.createUser(userid, name, email, passwort, state, regid);
//		
//		services.saveUser(user);
//
//		String registration = services.processRegistration("1", "ccltmann");
//		LoggerFactory.getLogger(getClass()).info(registration);
//		
//		assertThat(registration, containsString("Registrierung erfolgreich"));
//	}
//	
//	@Test
//	public void testProcessRegistrationNoUser(){
//		VBBServices services = new VBBServices(em);
//		assertThat(facadenFactory.getSpielFacade().count(), equalTo(4));
//		assertThat(facadenFactory.getBuchungFacade().count(), equalTo(31));
//		assertThat(facadenFactory.getSeasonFacade().count(), equalTo(3));
//		assertThat(facadenFactory.getSpielerFacade().count(), equalTo(10));
//		assertThat(facadenFactory.getGroupFacade().count(), equalTo(3));
//		assertThat(facadenFactory.getUserFacade().count(), equalTo(2));
//
//		String registration = services.processRegistration("abc", "ss");
//		LoggerFactory.getLogger(getClass()).info(registration);
//		assertThat(registration, containsString("Nicht registriert"));
//	}
//	@Test
//	public void testProcessRegistrationWrongRequest(){
//		VBBServices services = new VBBServices(em);
//		assertThat(facadenFactory.getSpielFacade().count(), equalTo(4));
//		assertThat(facadenFactory.getBuchungFacade().count(), equalTo(31));
//		assertThat(facadenFactory.getSeasonFacade().count(), equalTo(3));
//		assertThat(facadenFactory.getSpielerFacade().count(), equalTo(10));
//		assertThat(facadenFactory.getGroupFacade().count(), equalTo(3));
//		assertThat(facadenFactory.getUserFacade().count(), equalTo(2));
//
//		String registration = services.processRegistration("abc", null);
//		LoggerFactory.getLogger(getClass()).info(registration);
//		assertThat(registration, containsString("Registrierungsfehler"));
//	}
//	@Test
//	public void testProcessRegistrationWrongRegID(){
//		VBBServices services = new VBBServices(em);
//		assertThat(facadenFactory.getSpielFacade().count(), equalTo(4));
//		assertThat(facadenFactory.getBuchungFacade().count(), equalTo(31));
//		assertThat(facadenFactory.getSeasonFacade().count(), equalTo(3));
//		assertThat(facadenFactory.getSpielerFacade().count(), equalTo(10));
//		assertThat(facadenFactory.getGroupFacade().count(), equalTo(3));
//		assertThat(facadenFactory.getUserFacade().count(), equalTo(2));
//
//		String registration = services.processRegistration(null, "aaltmann");
//		LoggerFactory.getLogger(getClass()).info(registration);
//		assertThat(registration, containsString("Falsche ID"));
//	}
//	@Test
//	public void test(){
//		assertThat(new VBBServices(), not(nullValue()));
//	}
}

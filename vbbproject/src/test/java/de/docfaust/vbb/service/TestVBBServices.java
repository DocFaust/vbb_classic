package de.docfaust.vbb.service;

import de.docfaust.vbb.data.util.JpaBaseRolledBackTestCase;

public class TestVBBServices extends JpaBaseRolledBackTestCase {
//	private static final String HANS_HOHLBIRNE = "Hans Hohlbirne";
//	private static final String GRETCHEN_GROEHL = "Gretchen Gröhl";
//	private static final String FRANZ_FROEHLICH = "Franz Fröhlich";
//	private static final String ERICH_EHRLICH = "Erich Ehrlich";
//	private static final String DOREEN_DURSTIG = "Doreen Durstig";
//	private static final String CLAUS_CASPAR = "Claus Caspar";
//	private static final String BERND_BROT = "Bernd Brot";
//	private static final String ALFRED_ALTMANN = "Alfred Altmann";
//	private static final String INES_IGNORANT = "Ines Ignorant";
//	private static final String JOHANN_JOCHBEIN = "Johann Jochbein";
	// FIXME Write new
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
	// FIXME Write new
//	@Test
//	public void testIsSpielSaldoZeroTrue() {
//		VBBServices services = new VBBServices(em);
//		List<Spiel> spiele = services.getSpiele();
//		for (Spiel spiel : spiele) {
//			assertTrue("Falsch bei: " + spiel.getDatum().toString(), services.isSpielSaldoZero(spiel));
//		}
//	}
//
	// FIXME Write new
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
	// FIXME Write new
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
	// FIXME Write new
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
	// FIXME Write new
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
	// FIXME Write new
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
	// FIXME Write new
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
	// FIXME Write new
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
	// FIXME Write new
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
	// FIXME Write new
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
	// FIXME Write new
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
	// FIXME Write new
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
	// FIXME Write new
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
	// FIXME Write new
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
	// FIXME Write new
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
	// FIXME Write new
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
	// FIXME Write new
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
	// FIXME Write new
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
	// FIXME Write new
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
	// FIXME Write new
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
}

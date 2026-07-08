package de.docfaust.vbb.util.templates;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

import de.docfaust.vbb.data.entity.User;

public class VelocityBuilderTest {
	private RegisterTemplates registerTemplates = new VelocityRegisterTemplates();
	private MailTemplates mailTemplates = new VelocityMailTemplates();

	@Test
	public void testRegisterOk() {
		String ok = registerTemplates.getOk("Alex", "XYZ.de");
		assertTrue(ok.contains("Alex"));
		assertTrue(ok.contains("XYZ.de"));
		System.out.println(ok);
	}

	@Test
	public void testRegisterNotRegistered() {

		String notRegistered = registerTemplates.getNotRegistered("Alex");
		assertTrue(notRegistered.contains("Alex"));
		System.out.println(notRegistered);
	}

	@Test
	public void testRegisterWrongID() {
		String wrongID = registerTemplates.getWrongID("Alex");
		assertTrue(wrongID.contains("Alex"));
		System.out.println(wrongID);
	}

	@Test
	public void testRegisterYetRegistered() {
		String yetRegistered = registerTemplates.getYetRegistered("Alex");
		assertTrue(yetRegistered.contains("Alex"));
		System.out.println(yetRegistered);
	}

	@Test
	public void testMailTemplatesRegister() {
		User user = new User();
		user.setUsername("Alex Ander");
		user.setUserid("aander");
		user.setRegid("xyz");
		String domain = "http://localhost:8083/mimi";
		String mail = mailTemplates.getRegisterMail(user, domain);
		LoggerFactory.getLogger(getClass()).info(mail);

		assertThat(mail, containsString("Alex Ander"));
		assertThat(mail, containsString("aander"));
		assertThat(mail, containsString("xyz"));
		assertThat(mail, containsString(domain));

	}
// FIXME Repair
//	@Test
//	public void testSaldoMail() {
//		String name = "Alex Ander1";
//		Spiel spiel = EntityFactory.createSpiel(new Date());
//		Buchung buchung1 = EntityFactory.createBuchung(new Date(), BigDecimal.valueOf(12.34));
//		Spieler spieler = EntityFactory.createSpieler(true, "Alex Ander", "", true);
//		buchung1.setSpieler(spieler);
//
//		Buchung buchung2 = EntityFactory.createBuchung(new Date(), BigDecimal.valueOf(12.34));
//		Spieler spieler2 = EntityFactory.createSpieler(true, "Hugor H�gel", "", true);
//		buchung2.setSpieler(spieler2);
//
//		spiel.addBuchung(buchung1);
//		spiel.addBuchung(buchung2);
//		
//		Map<String, BigDecimal> m = new HashMap<>();
//		m.put("Alex", BigDecimal.valueOf(45.67));
//		m.put("Victarion", BigDecimal.valueOf(67.89));
//		List<Entry<String, BigDecimal>> entries = new ArrayList<Entry<String, BigDecimal>>(m.entrySet());
//		String mail = mailTemplates.getSaldoMail(spiel, name, entries, BigDecimal.ZERO);
//		LoggerFactory.getLogger(getClass()).info(mail);
//
//		assertThat(mail, containsString("Alex Ander1"));
//		assertThat(mail, containsString("Alex Ander"));
//		assertThat(mail, containsString("Hugor H�gel"));
//		assertThat(mail, containsString("Victarion"));
//	}

}

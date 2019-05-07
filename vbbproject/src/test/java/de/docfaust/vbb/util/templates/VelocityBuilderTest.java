package de.docfaust.vbb.util.templates;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Test;
import org.slf4j.LoggerFactory;

import de.docfaust.vbb.data.entity.Buchung;
import de.docfaust.vbb.data.entity.Spiel;
import de.docfaust.vbb.data.entity.Spieler;
import de.docfaust.vbb.data.entity.User;
import de.docfaust.vbb.util.EntityFactory;

public class VelocityBuilderTest {
	private RegisterTemplates registerTemplates = new VelocityRegisterTemplates();
	private MailTemplates mailTemplates = new VelocityMailTemplates();

	@Test
	public void testRegisterOk() {
		String ok = registerTemplates.getOk("Werner", "XYZ.de");
		assertTrue(ok.contains("Werner"));
		assertTrue(ok.contains("XYZ.de"));
		System.out.println(ok);
	}

	@Test
	public void testRegisterNotRegistered() {

		String notRegistered = registerTemplates.getNotRegistered("Werner");
		assertTrue(notRegistered.contains("Werner"));
		System.out.println(notRegistered);
	}

	@Test
	public void testRegisterWrongID() {
		String wrongID = registerTemplates.getWrongID("Werner");
		assertTrue(wrongID.contains("Werner"));
		System.out.println(wrongID);
	}

	@Test
	public void testRegisterYetRegistered() {
		String yetRegistered = registerTemplates.getYetRegistered("Werner");
		assertTrue(yetRegistered.contains("Werner"));
		System.out.println(yetRegistered);
	}

	@Test
	public void testMailTemplatesRegister() {
		User user = new User();
		user.setUsername("Werner Faust");
		user.setUserid("wfaust");
		user.setRegid("xyz");
		String domain = "http://localhost:8083/mimi";
		String mail = mailTemplates.getRegisterMail(user, domain);
		LoggerFactory.getLogger(getClass()).info(mail);

		assertThat(mail, containsString("Werner Faust"));
		assertThat(mail, containsString("wfaust"));
		assertThat(mail, containsString("xyz"));
		assertThat(mail, containsString(domain));

	}

	@Test
	public void testSaldoMail() {
		String name = "Werner Faust1";
		Spiel spiel = EntityFactory.createSpiel(new Date());
		Buchung buchung1 = EntityFactory.createBuchung(new Date(), BigDecimal.valueOf(12.34));
		Spieler spieler = EntityFactory.createSpieler(true, "Werner Faust", "", true);
		buchung1.setSpieler(spieler);

		Buchung buchung2 = EntityFactory.createBuchung(new Date(), BigDecimal.valueOf(12.34));
		Spieler spieler2 = EntityFactory.createSpieler(true, "Hugor Hügel", "", true);
		buchung2.setSpieler(spieler2);

		spiel.addBuchung(buchung1);
		spiel.addBuchung(buchung2);
		
		Map<String, BigDecimal> m = new HashMap<>();
		m.put("Werner", BigDecimal.valueOf(45.67));
		m.put("Victarion", BigDecimal.valueOf(67.89));
		List<Entry<String, BigDecimal>> entries = new ArrayList<Entry<String, BigDecimal>>(m.entrySet());
		String mail = mailTemplates.getSaldoMail(spiel, name, entries, BigDecimal.ZERO);
		LoggerFactory.getLogger(getClass()).info(mail);

		assertThat(mail, containsString("Werner Faust1"));
		assertThat(mail, containsString("Werner Faust"));
		assertThat(mail, containsString("Hugor Hügel"));
		assertThat(mail, containsString("Victarion"));
	}

}

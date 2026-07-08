package de.docfaust.vbb.service;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.docfaust.vbb.ServiceCreator;
import de.docfaust.vbb.data.entity.Mail;
import de.docfaust.vbb.data.entity.Spiel;
import de.docfaust.vbb.data.entity.User;
import de.docfaust.vbb.data.util.JpaBaseRolledBackTestCase;
import de.docfaust.vbb.util.RegistrationState;

public class TestMailService extends JpaBaseRolledBackTestCase {

	private MailService mailService;
	
	@BeforeEach
	public void setUp() {
		ServiceCreator sc = new ServiceCreator(em);
		this.mailService = sc.getMailService();
	}
	
	@Test
	void testInit() {
		assertThat(new MailServiceImpl()).isNotNull();
	}
	
	@Test
	public void testSendRegistrationMail() {
		User user = new User();
		user.setUserid("aander");
		user.setUsername("Alex Ander");
		user.setState(RegistrationState.OPEN);
		user.setEmail("alex.ander@example.invalid");
		user.setRegid("a");
		try {
			mailService.sendRegistrationMail(user);
		} catch (Exception e) {
			logger.error("Fehler", e);
			fail("Fehler beim Verschicken der Mail " + e.getMessage());
		}
	}

	@Test
	public void testSendSpiel() {
		int count = facadenFactory.getMailFacade().count();
		Spiel spiel = facadenFactory.getSpielFacade().find(1);
		
		mailService.sendSpielMail(spiel);
		
		List<Mail> list = facadenFactory.getMailFacade().findAll();
		list.forEach(mail -> logger.info(mail.getText()));
		System.out.println(list);
		assertThat(facadenFactory.getMailFacade().count()).isEqualTo(count + spiel.getBuchungen().size()-1);
	}
	
}

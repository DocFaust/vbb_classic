package de.docfaust.vbb.util.configuration;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.jupiter.api.Test;

import de.docfaust.vbb.data.entity.Config;
import de.docfaust.vbb.data.util.JpaBaseRolledBackTestCase;



public class TestMailConfigurationDB extends JpaBaseRolledBackTestCase {

	@Test
	public void testGetSenderAddress() {
		MailConfiguration config = new MailConfigurationDBImpl(em);
		assertThat(config.getSenderAddress(), equalTo("noreply@volleybuchung.example"));
	}

	@Test
	public void testSetSenderaddress() {
		int count = facadenFactory.getConfigFacade().count();
		MailConfiguration config = new MailConfigurationDBImpl(em);
		String address = "kontakt@volleybuchung.example";
		config.setSenderaddress(address);
		
		assertThat(facadenFactory.getConfigFacade().count(), equalTo(count));
		assertThat(config.getSenderAddress(), equalTo(address));
	}

	@Test
	public void testGetDomain() {
		MailConfiguration config = new MailConfigurationDBImpl(em);
		assertThat(config.getDomain(), equalTo("http://localhost:8080/vbb"));
	}

	@Test
	public void testSetDomain() {
		int count = facadenFactory.getConfigFacade().count();
		MailConfiguration config = new MailConfigurationDBImpl(em);
		String domain = "ufz";
		config.setDomain(domain);
		assertThat(config.getDomain(), equalTo(domain));
		assertThat(facadenFactory.getConfigFacade().count(), equalTo(count));
	}

	@Test
	public void testGetRegistrationSubject() {
		MailConfiguration config = new MailConfigurationDBImpl(em);
		assertThat(config.getRegistrationSubject(), equalTo("Registrierung bei der Volleyballbuchung"));
	}

	@Test
	public void testSetRegistrationSubject() {
		int count = facadenFactory.getConfigFacade().count();
		MailConfiguration config = new MailConfigurationDBImpl(em);
		String subject = "ufz";
		config.setRegistrationSubject(subject);
		assertThat(config.getRegistrationSubject(), equalTo(subject));
		assertThat(facadenFactory.getConfigFacade().count(), equalTo(count));
	}
	@Test
	public void testSetNew() {
		int count = facadenFactory.getConfigFacade().count();
		MailConfiguration config = new MailConfigurationDBImpl(em);
		Config config2 = facadenFactory.getConfigFacade().findByKey("subject");
		facadenFactory.getConfigFacade().remove(config2);
		assertThat(facadenFactory.getConfigFacade().count(), equalTo(count-1));
		
		String subject = "ufz";
		config.setRegistrationSubject(subject);
		assertThat(config.getRegistrationSubject(), equalTo(subject));
		assertThat(facadenFactory.getConfigFacade().count(), equalTo(count));
	}
	
	@Test
	public void test() {
		MailConfiguration config = new MailConfigurationDBImpl();
		assertThat(config, not(nullValue()));
	}
	
}

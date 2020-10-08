package de.docfaust.vbb.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

import de.docfaust.vbb.ServiceCreator;
import de.docfaust.vbb.data.entity.User;
import de.docfaust.vbb.data.util.JpaBaseRolledBackTestCase;
import de.docfaust.vbb.util.EntityFactory;
import de.docfaust.vbb.util.RegistrationState;

class TestRegisterService extends JpaBaseRolledBackTestCase {

	private RegisterService registerService;

	@BeforeEach
	void setUp() throws Exception {
		ServiceCreator sc = new ServiceCreator(em);
		registerService = sc.getRegisterService();
	}

	@Test
	void testInit() {
		assertThat(new RegisterServiceImpl()).isNotNull();
	}

	@Test
	public void testProcessRegistrationYetRegistered() {

		String registration = registerService.processRegistration("a", "aaltmann");
		LoggerFactory.getLogger(getClass()).info(registration);
		assertThat(registration).contains("Bereits registriert");
	}

	@Test
	public void testProcessRegistrationWrongRegid() {
		String registration = registerService.processRegistration("abc", "aaltmann");
		LoggerFactory.getLogger(getClass()).info(registration);
		assertThat(registration).contains("Falsche ID");
	}

	@Test
	public void testProcessRegistrationOk() {
		String userid = "ccltmann";
		String passwort = "12345678";
		String name = "Alfred Altmann";
		String email = "ccltmann@gmiks.de";
		RegistrationState state = RegistrationState.OPEN;
		String regid = "1";

		User user = EntityFactory.createUser(userid, name, email, passwort, state, regid);

		facadenFactory.getUserFacade().create(user);

		String registration = registerService.processRegistration("1", "ccltmann");
		LoggerFactory.getLogger(getClass()).info(registration);

		assertThat(registration).contains("Registrierung erfolgreich");
	}

	@Test
	public void testProcessRegistrationNoUser() {
		String registration = registerService.processRegistration("abc", "ss");
		LoggerFactory.getLogger(getClass()).info(registration);
		assertThat(registration).contains("Nicht registriert");
	}

	@Test
	public void testProcessRegistrationWrongRequest() {
		String registration = registerService.processRegistration("abc", null);
		LoggerFactory.getLogger(getClass()).info(registration);
		assertThat(registration).contains("Registrierungsfehler");
	}

	@Test
	public void testProcessRegistrationWrongRegID() {

		String registration = registerService.processRegistration(null, "aaltmann");
		LoggerFactory.getLogger(getClass()).info(registration);
		assertThat(registration).contains("Falsche ID");
	}

}

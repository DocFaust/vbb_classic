package de.docfaust.vbb.data.facade;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

import de.docfaust.vbb.data.entity.Group;
import de.docfaust.vbb.data.entity.User;
import de.docfaust.vbb.data.facades.UserFacade;
import de.docfaust.vbb.data.util.JpaBaseRolledBackTestCase;
import de.docfaust.vbb.util.EntityFactory;
import de.docfaust.vbb.util.RegistrationState;
import de.docfaust.vbb.util.messages.MessageConstants;
import de.docfaust.vbb.util.statusliste.Statusliste;

public class TestUserFacade extends JpaBaseRolledBackTestCase {

	@Test
	public void test() {
		assertThat(new UserFacade(), not(nullValue()));
	}

	@Test
	public void testFindAll() {
		assertFalse(facadenFactory.getUserFacade().findAll().isEmpty());
		assertEquals(2, facadenFactory.getUserFacade().count());
	}

	@Test
	public void testFindByName() {
		User user = facadenFactory.getUserFacade().findByUserName("aaltmann");
		assertNotNull(user);
		assertEquals("aaltmann", user.getUserid());
		assertEquals("Alfred Altmann", user.getUsername());
		assertEquals("aaltmann@mail.de", user.getEmail());
		assertEquals(RegistrationState.PROOFED, user.getState());
		assertEquals("a", user.getRegid());
	}

	@Test
	public void testFindByNameNotFound() {
		User user = facadenFactory.getUserFacade().findByUserName("xx");
		assertNull(user);
	}

	@Test
	public void testLoginSuccessful() throws Exception {
		UserFacade userFacade = facadenFactory.getUserFacade();
		String userid = "ccltmann";
		String passwort = "12345678";
		String name = "Alfred Altmann";
		String email = "ccltmann@gmiks.de";
		RegistrationState state = RegistrationState.OPEN;
		String regid = "1";

		User user = EntityFactory.createUser(userid, name, email, passwort, state, regid);

		userFacade.create(user);

		User user2 = createUser(userid, passwort);
		User lu = userFacade.login(user2);
		assertNotNull(lu);
		assertEquals(name, lu.getUsername());
		assertEquals(email, lu.getEmail());
		assertEquals(state, lu.getState());
		assertEquals(userid, lu.getUserid());

	}

	@Test
	public void testLoginUnsuccessful() throws Exception {
		UserFacade userFacade = facadenFactory.getUserFacade();
		String userid = "ccltmann";
		String passwort = "12345678";
		String name = "Alfred Altmann";
		String email = "ccltmann@gmiks.de";
		RegistrationState state = RegistrationState.OPEN;
		String regid = "1";

		User user = EntityFactory.createUser(userid, name, email, passwort, state, regid);

		userFacade.create(user);

		User user2 = createUser(userid, "87654321");
		User lu = userFacade.login(user2);
		assertNull(lu);
	}

	@Test
	public void testRegisterSuccessful() throws Exception {
		UserFacade userFacade = facadenFactory.getUserFacade();
		String userid = "ccltmann";
		String passwort = "12345678";
		String name = "Alfred Altmann";
		String email = "ccltmann@gmiks.de";
		RegistrationState state = RegistrationState.OPEN;
		String regid = "1";

		User user = EntityFactory.createUser(userid, name, email, passwort, state, regid);

		Statusliste statusliste = userFacade.register(user);

		assertTrue(statusliste.booleanValue());

		User lu = userFacade.find(user.getId());
		assertNotNull(lu);
		assertEquals(name, lu.getUsername());
		assertEquals(email, lu.getEmail());
		assertEquals(state, lu.getState());
		assertEquals(userid, lu.getUserid());

	}

	@Test
	public void testRegisterUnSuccessful() throws Exception {
		UserFacade userFacade = facadenFactory.getUserFacade();
		String userid = "ccltmann";
		String passwort = "12345678";
		String name = "Alfred Altmann";
		String email = "ccltmann@gmiks.de";
		RegistrationState state = RegistrationState.OPEN;
		String regid = "1";

		User user = EntityFactory.createUser(userid, name, email, passwort, state, regid);

		userFacade.create(user);

		User user2 = EntityFactory.createUser(userid, name, email, passwort, state, regid);

		Statusliste statusliste = userFacade.register(user2);

		assertFalse(statusliste.booleanValue());
		assertTrue(statusliste.hasStatus(MessageConstants.ALREADYREGISTERED));
	}

	@Test
	public void testSetGroup() {
		User user = facadenFactory.getUserFacade().find(2);
		logger.info(user.toString());
		assertThat(user.getGroup().getName(), equalTo("ADMIN"));

		Group group = facadenFactory.getGroupFacade().findByName("USER");

		user.setGroup(group);

		facadenFactory.getUserFacade().edit(user);

		logger.info(user.toString());
		assertThat(facadenFactory.getUserFacade().find(2).getGroup().getName(), equalTo("USER"));
	}

	@Test
	public void testCheckEmailOk() {
		User u = EntityFactory.createUser("FFaaltmann", "", "FFaAlTmAnN@mail.de", "", RegistrationState.OPEN, "");
		Statusliste statusliste = facadenFactory.getUserFacade().checkEmail(u);
		assertTrue(statusliste.booleanValue());
	}

	@Test
	public void testCheckEmail() {
		User u = EntityFactory.createUser("aaltmann", "", "aAlTmAnN@mail.de", "", RegistrationState.OPEN, "");
		Statusliste statusliste = facadenFactory.getUserFacade().checkEmail(u);
		assertFalse(statusliste.booleanValue());
		statusliste.hasStatus(MessageConstants.EMAIL_EXISTS);
	}

	@Test
	public void testCheckUserIDOk() {
		User u = EntityFactory.createUser("FFaaltmann", "", "FFaAlTmAnN@mail.de", "", RegistrationState.OPEN, "");
		Statusliste statusliste = facadenFactory.getUserFacade().checkUserID(u);
		assertTrue(statusliste.booleanValue());
	}

	@Test
	public void testCheckUserID() {
		User u = EntityFactory.createUser("aAlTtMaNn", "", "aAlTmAnN@mail.de", "", RegistrationState.OPEN, "");
		Statusliste statusliste = facadenFactory.getUserFacade().checkEmail(u);
		assertFalse(statusliste.booleanValue());
		statusliste.hasStatus(MessageConstants.USERID_EXISTS);
	}

	private User createUser(final String userid, final String passwort) {
		return EntityFactory.createUser(userid, null, null, passwort, null, null);
	}
}

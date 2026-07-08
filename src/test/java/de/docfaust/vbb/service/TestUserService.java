package de.docfaust.vbb.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.docfaust.vbb.ServiceCreator;
import de.docfaust.vbb.data.entity.User;
import de.docfaust.vbb.data.facades.UserFacade;
import de.docfaust.vbb.data.util.JpaBaseRolledBackTestCase;
import de.docfaust.vbb.util.EntityFactory;
import de.docfaust.vbb.util.PasswordUtil;
import de.docfaust.vbb.util.RegistrationState;
import de.docfaust.vbb.util.messages.MessageConstants;
import de.docfaust.vbb.util.statusliste.Statusliste;

class TestUserService extends JpaBaseRolledBackTestCase {
	private UserService userService;

	@BeforeEach
	void setUp() throws Exception {
		ServiceCreator sc = new ServiceCreator(em);
		this.userService = sc.getUserService();
	}

	@Test
	public void testInit() {

		// Nonsense for Coverage
		assertThat(new UserServiceImpl()).isNotNull();
	}

	@Test
	public void testRegister() {
		assertThat(facadenFactory.getUserFacade().count()).isEqualTo(2);
		String username = "Alfred";
		String userid = "aalfred";
		String email = "a@b.c";
		String password = "asb";
		String regid = "aa";
		RegistrationState state = RegistrationState.OPEN;
		User user = EntityFactory.createUser(userid, username, email, password, state, regid);
		Statusliste statusliste = userService.register(user);
		assertThat(statusliste.booleanValue()).isTrue();
		assertThat(facadenFactory.getUserFacade().count()).isEqualTo(3);
	}

	@Test
	public void testRegisterExisting() {
		assertThat(facadenFactory.getUserFacade().count()).isEqualTo(2);
		String username = "Alex Ander";
		String userid = "aander";
		String email = "alex.ander@example.invalid";
		String password = "wwwwwwww";
		RegistrationState state = RegistrationState.OPEN;
		String regid = "a";
		User user = EntityFactory.createUser(userid, username, email, password, state, regid);
		Statusliste statusliste = userService.register(user);
		assertThat(statusliste.booleanValue()).isFalse();
		assertThat(statusliste.hasStatus(MessageConstants.USERID_EXISTS));
		assertThat(facadenFactory.getUserFacade().count()).isEqualTo(2);
	}

	@Test
	public void testLogin() {
		UserFacade userFacade = facadenFactory.getUserFacade();
		String userid = "ccltmann";
		String passwort = "12345678";
		String name = "Alex Ander";
		String email = "ccltmann@gmiks.de";
		RegistrationState state = RegistrationState.OPEN;
		String regid = "1";

		User user = EntityFactory.createUser(userid, name, email, passwort, state, regid);

		userFacade.create(user);

		User user2 = createUser(userid, passwort);
		User lu = userService.login(user2);
		assertThat(lu).isNotNull().extracting("username", "email", "state").contains(name, email, state);
	}

	@Test
	public void testLoginUnsuccessful() {
		UserFacade userFacade = facadenFactory.getUserFacade();
		String userid = "ccltmann";
		String passwort = "12345678";
		String name = "Alex Ander";
		String email = "ccltmann@gmiks.de";
		RegistrationState state = RegistrationState.OPEN;
		String regid = "1";

		User user = EntityFactory.createUser(userid, name, email, passwort, state, regid);

		userFacade.create(user);

		User user2 = createUser(userid, "falsches PW");
		User lu = userService.login(user2);
		assertThat(lu).isNull();
	}

	@Test
	public void testLogin2() {
		UserFacade userFacade = facadenFactory.getUserFacade();
		String userid = "ccltmann";
		String passwort = "12345678";
		String name = "Alex Ander";
		String email = "ccltmann@gmiks.de";
		RegistrationState state = RegistrationState.OPEN;
		String regid = "1";

		User user = EntityFactory.createUser(userid, name, email, passwort, state, regid);

		userFacade.create(user);

		User lu = userService.login(userid, passwort);
		assertThat(lu).isNotNull().extracting("username", "email", "state").contains(name, email, state);
	}

	@Test
	public void testLogin2Unsuccessful() {
		UserFacade userFacade = facadenFactory.getUserFacade();
		String userid = "ccltmann";
		String passwort = "12345678";
		String name = "Alex Ander";
		String email = "ccltmann@gmiks.de";
		RegistrationState state = RegistrationState.OPEN;
		String regid = "1";

		User user = EntityFactory.createUser(userid, name, email, passwort, state, regid);

		userFacade.create(user);

		User lu = userService.login(userid, "falsches PW");
		assertThat(lu).isNull();
	}

	private User createUser(final String userid, final String passwort) {
		return EntityFactory.createUser(userid, null, null, passwort, null, null);
	}

	@Test
	public void testGetUsers() {
		assertThat(userService.getUsers()).hasSize(facadenFactory.getUserFacade().count());
	}

	@Test
	public void testDeleteUser() {
		int count = facadenFactory.getUserFacade().count();

		User user = facadenFactory.getUserFacade().find(1);
		userService.deleteUser(user);

		assertThat(facadenFactory.getUserFacade().count()).isEqualTo(--count);

	}

	@Test
	public void testSaveUser() {
		int count = facadenFactory.getUserFacade().count();

		UserFacade userFacade = facadenFactory.getUserFacade();
		String userid = "ccltmann";
		String passwort = "12345678";
		String name = "Alex Ander";
		String email = "ccltmann@gmiks.de";
		RegistrationState state = RegistrationState.OPEN;
		String regid = "1";

		User user = EntityFactory.createUser(userid, name, email, passwort, state, regid);

		userService.saveUser(user);

		int id = user.getId();

		assertThat(userFacade.find(id)).isNotNull()
				.extracting("userid", "password", "username", "email", "state", "regid")
				.contains(userid, PasswordUtil.encryptPassword(passwort), name, email, state, regid);

		assertThat(facadenFactory.getUserFacade().count()).isEqualTo(++count);
	}

	@Test
	public void testSaveUserExisting() {
		int count = facadenFactory.getUserFacade().count();

		String userid = "aander";
		String passwort = "12345678";
		String name = "Alex Ander";
		String email = "ccltmann@gmiks.de";
		RegistrationState state = RegistrationState.OPEN;
		String regid = "1";

		User user = EntityFactory.createUser(userid, name, email, passwort, state, regid);

		Statusliste statusliste = userService.saveUser(user);
		assertThat(statusliste.booleanValue()).isFalse();
		assertThat(statusliste.hasStatus(MessageConstants.USERID_EXISTS)).isTrue();

		assertThat(facadenFactory.getUserFacade().count()).isEqualTo(count);
	}

	@Test
	public void testGetLoggedInUser() {

		User user = userService.getLoggedInUser("aander");
		assertThat(user).isNotNull().extracting("userid", "username", "email", "state", "regid").contains("aander",
				"Alex Ander", "alex.ander@example.invalid", RegistrationState.PROOFED, "a");

	}

	@Test
	public void testFindByUserName() {
		assertThat(userService.findByUserName("aander")).isNotNull()
				.extracting("userid", "username", "email", "state", "regid")
				.contains("aander", "Alex Ander", "alex.ander@example.invalid", RegistrationState.PROOFED, "a");
	}

}

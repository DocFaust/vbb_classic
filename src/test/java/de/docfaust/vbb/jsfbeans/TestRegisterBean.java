package de.docfaust.vbb.jsfbeans;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.jupiter.api.Test;

import de.docfaust.vbb.ServiceCreator;
import de.docfaust.vbb.data.entity.User;
import de.docfaust.vbb.data.util.JpaBaseRolledBackTestCase;
import de.docfaust.vbb.util.RegistrationState;
import de.docfaust.vbb.util.UIMessagesTestImpl;

public class TestRegisterBean extends JpaBaseRolledBackTestCase {
	@Test
	public void test() {
		assertThat(new RegisterBean(), not(nullValue()));

	}

	@Test
	public void testRegister() {
		int oldUserCount = facadenFactory.getUserFacade().count();
		RegisterBean bean = initBean();
		
		String email = "jule.jorek@example.invalid";
		String password = "12345678";
		String passwordrepeat = "12345678";
		String userid = "jjorek";
		String username = "Jule Jorek";
		
		bean.setEmail(email);
		bean.setPassword(password);
		bean.setPasswordrepeat(passwordrepeat);
		bean.setUserid(userid);
		bean.setUsername(username);
		
		bean.register();
		
		assertThat(facadenFactory.getUserFacade().count(), equalTo(oldUserCount + 1));
		User user = facadenFactory.getUserFacade().findByUserName(userid);
		
		assertThat(user.getState(), equalTo(RegistrationState.OPEN));
		assertThat(user.getRegid(), not(nullValue()));
	}

	private RegisterBean initBean() {
		ServiceCreator sc = new ServiceCreator(em);
		RegisterBean bean = new RegisterBean(new UIMessagesTestImpl(), sc.getUserService(), sc.getMailService());
		return bean;
	}
	@Test
	public void testRegisterExists() {
		int oldUserCount = facadenFactory.getUserFacade().count();
		RegisterBean bean = initBean();
		
		String email = "jule.jorek@example.invalid";
		String password = "12345678";
		String passwordrepeat = "12345678";
		String userid = "aander";
		String username = "Jule Jorek";
		
		bean.setEmail(email);
		bean.setPassword(password);
		bean.setPasswordrepeat(passwordrepeat);
		bean.setUserid(userid);
		bean.setUsername(username);
		bean.getPasswordrepeat();
		bean.register();
		
		assertThat(facadenFactory.getUserFacade().count(), equalTo(oldUserCount));
	}
}

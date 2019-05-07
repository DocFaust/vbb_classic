package de.docfaust.vbb.jsfbeans;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;

import de.docfaust.vbb.data.entity.User;
import de.docfaust.vbb.data.util.JpaBaseRolledBackTestCase;
import de.docfaust.vbb.service.VBBServices;
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
		RegisterBean bean = new RegisterBean(new VBBServices(em), new UIMessagesTestImpl());
		
		String email = "hhuegel@got.wr";
		String password = "12345678";
		String passwordrepeat = "12345678";
		String userid = "hhuegel";
		String username = "Hugor Hügel";
		
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
	@Test
	public void testRegisterExists() {
		int oldUserCount = facadenFactory.getUserFacade().count();
		RegisterBean bean = new RegisterBean(new VBBServices(em), new UIMessagesTestImpl());
		
		String email = "hhuegel@got.wr";
		String password = "12345678";
		String passwordrepeat = "12345678";
		String userid = "aaltmann";
		String username = "Hugor Hügel";
		
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

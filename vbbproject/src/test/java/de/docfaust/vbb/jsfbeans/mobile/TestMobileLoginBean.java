package de.docfaust.vbb.jsfbeans.mobile;

import static org.junit.Assert.*;

import java.io.UnsupportedEncodingException;

import static org.hamcrest.CoreMatchers.*;
import org.junit.jupiter.api.Test;

import de.docfaust.vbb.data.entity.User;
import de.docfaust.vbb.data.util.JpaBaseRolledBackTestCase;
import de.docfaust.vbb.service.VBBServices;
import de.docfaust.vbb.util.EntityFactory;
import de.docfaust.vbb.util.RegistrationState;
import de.docfaust.vbb.util.UIMessagesTestImpl;

public class TestMobileLoginBean extends JpaBaseRolledBackTestCase {

	@Test
	public void testLoginWithOutcome() throws UnsupportedEncodingException {
		User user = EntityFactory.createUser("xx", "Xaver Xylophon", "xx@yy.zz", "12345678", RegistrationState.PROOFED, "asdf");
		facadenFactory.getUserFacade().create(user);
		MobileLoginBean bean = new MobileLoginBean(new VBBServices(em), new UIMessagesTestImpl());
		bean.setUserid("xx");
		bean.setPassword("12345678");
		logger.info(facadenFactory.getUserFacade().findByUserName("xx").getPassword());
		assertThat(bean.loginWithOutcome(), equalTo("summary"));
		assertThat(bean.getLoginSuccessful(), equalTo(Boolean.TRUE));
		assertThat(bean.getLoggedInUser(), not(nullValue()));
		assertThat(bean.getLoggedInUser(), equalTo(user));
	}

	@Test
	public void testLoginWithOutcomeUnsuccessful() throws UnsupportedEncodingException {
		User user = EntityFactory.createUser("xx", "Xaver Xylophon", "xx@yy.zz", "12345678", RegistrationState.PROOFED, "asdf");
		facadenFactory.getUserFacade().create(user);
		MobileLoginBean bean = new MobileLoginBean(new VBBServices(em), new UIMessagesTestImpl());
		bean.setUserid("xx");
		bean.setPassword("123456789");
		logger.info(facadenFactory.getUserFacade().findByUserName("xx").getPassword());
		assertThat(bean.loginWithOutcome(), equalTo("index2"));
		assertThat(bean.getLoginSuccessful(), equalTo(Boolean.FALSE));
		assertThat(bean.getLoggedInUser(), nullValue());
	}

	@Test
	public void testLogin(){
		User user = EntityFactory.createUser("xx", "Xaver Xylophon", "xx@yy.zz", "12345678", RegistrationState.PROOFED, "asdf");
		facadenFactory.getUserFacade().create(user);
		MobileLoginBean bean = new MobileLoginBean(new VBBServices(em), new UIMessagesTestImpl());
		bean.setUserid("xx");
		bean.setPassword("12345678");
		bean.login();
		assertThat(bean.getLoginSuccessful(), equalTo(Boolean.TRUE));
		assertThat(bean.getLoggedInUser(), not(nullValue()));
		assertThat(bean.getLoggedInUser(), equalTo(user));
	}
	@Test
	public void testLoginNotYet(){
		User user = EntityFactory.createUser("xx", "Xaver Xylophon", "xx@yy.zz", "12345678", RegistrationState.OPEN, "asdf");
		facadenFactory.getUserFacade().create(user);
		MobileLoginBean bean = new MobileLoginBean(new VBBServices(em), new UIMessagesTestImpl());
		bean.setUserid("xx");
		bean.setPassword("12345678");
		bean.login();
		assertThat(bean.getLoginSuccessful(), equalTo(Boolean.FALSE));
		assertThat(bean.getLoggedInUser(), nullValue());
		assertThat(bean.getUserid(), nullValue());
		assertThat(bean.getPassword(), nullValue());
	}
	@Test
	public void testLoginNotRegistered(){
		MobileLoginBean bean = new MobileLoginBean(new VBBServices(em), new UIMessagesTestImpl());
		bean.setUserid("xx");
		bean.setPassword("12345678");
		bean.login();
		assertThat(bean.getLoginSuccessful(), equalTo(Boolean.FALSE));
		assertThat(bean.getLoggedInUser(), nullValue());
		assertThat(bean.getUserid(), nullValue());
		assertThat(bean.getPassword(), nullValue());
	}

	@Test
	public void testLoginNull(){
		MobileLoginBean bean = new MobileLoginBean(new VBBServices(em), new UIMessagesTestImpl());
		bean.login();
		assertThat(bean.getLoginSuccessful(), equalTo(Boolean.FALSE));
		assertThat(bean.getLoggedInUser(), nullValue());
		assertThat(bean.getUserid(), nullValue());
		assertThat(bean.getPassword(), nullValue());
	}
	@Test
	public void testLogout() {
		User user = EntityFactory.createUser("xx", "Xaver Xylophon", "xx@yy.zz", "12345678", RegistrationState.PROOFED, "asdf");
		facadenFactory.getUserFacade().create(user);
		MobileLoginBean bean = new MobileLoginBean(new VBBServices(em), new UIMessagesTestImpl());
		bean.setUserid("xx");
		bean.setPassword("12345678");
		assertThat(bean.loginWithOutcome(), equalTo("summary"));
		assertThat(bean.getLoginSuccessful(), equalTo(Boolean.TRUE));
		assertThat(bean.getLoggedInUser(), not(nullValue()));
		assertThat(bean.getLoggedInUser(), equalTo(user));
		
		assertThat(bean.logoutWithOutcome(), equalTo("index"));
		assertThat(bean.getLoginSuccessful(), equalTo(Boolean.FALSE));
		assertThat(bean.getLoggedInUser(), nullValue());
		assertThat(bean.getUserid(), nullValue());
		assertThat(bean.getPassword(), nullValue());
	}
	
	@Test
	public void test() {
		assertThat(new MobileLoginBean(), not(nullValue()));
	}
	
}

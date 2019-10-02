package de.docfaust.vbb.jsfbeans;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.jupiter.api.Test;

import de.docfaust.vbb.data.entity.User;
import de.docfaust.vbb.data.util.JpaBaseRolledBackTestCase;
import de.docfaust.vbb.util.PasswordUtil;
import de.docfaust.vbb.util.UIMessagesTestImpl;

public class TestEditProfileBean extends JpaBaseRolledBackTestCase {

	@Test
	public void testInit() {
		initBean();
	}

	@Test
	public void testSave() {
		EditProfileBean bean = initBean();
		User user = bean.getUser();
		int id = user.getId();
		user.setUsername("Hugor Hügel");
		bean.save();
		User find = facadenFactory.getUserFacade().find(id);
		assertThat(find.getUsername(), equalTo("Hugor Hügel"));
	}

	@Test
	public void testEditPassword() {
		EditProfileBean bean = initBean();
		User user = bean.getUser();
		int id = user.getId();

		bean.setPassword("hupf");
		bean.setPasswordrepeat("hupf");
		assertThat(bean.getPassword(), equalTo(bean.getPasswordrepeat()));
		
		bean.editPassword();
		
		User find = facadenFactory.getUserFacade().find(id);
		assertThat(find.getPassword(), equalTo(PasswordUtil.encryptPassword("hupf")));

	}
	
	private EditProfileBean initBean() {
		EditProfileBean bean = new EditProfileBean(new UIMessagesTestImpl());
		bean.init();
		
		// User muss gesetzt werden, da keine angemeldet sein kann
		User user = facadenFactory.getUserFacade().find(1);
		bean.setUser(user);
		
		assertThat(bean.getUser(), equalTo(user));
		return bean;
	}
	
	@Test
	public void test() {
		assertThat(new EditProfileBean(), not(nullValue()));

	}
	
}

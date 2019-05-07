package de.docfaust.vbb.jsfbeans;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;

import de.docfaust.vbb.data.entity.User;
import de.docfaust.vbb.data.util.JpaBaseRolledBackTestCase;
import de.docfaust.vbb.service.VBBServices;
import de.docfaust.vbb.util.PasswordUtil;
import de.docfaust.vbb.util.UIMessagesTestImpl;

public class TestEditUserBean extends JpaBaseRolledBackTestCase {
	@Test
	public void test() {
		assertThat(new EditUserBean(), not(nullValue()));

	}

	@Test
	public void testSaveUser() {
		EditUserBean bean = new EditUserBean(new VBBServices(em), new UIMessagesTestImpl());
		List<User> users = bean.getUsers();
		bean.setSelectedUser(users.get(0));
		User user = bean.getSelectedUser();
		user.setUsername("Neuer Name");
		bean.saveUser();
		
		User name = facadenFactory.getUserFacade().findByUserName("aaltmann");
		
		assertThat(name.getUsername(), equalTo("Neuer Name"));
	}

	@Test
	public void testDeleteUser() {
		int userCount = facadenFactory.getUserFacade().count();
		EditUserBean bean = new EditUserBean(new VBBServices(em), new UIMessagesTestImpl());
		List<User> users = bean.getUsers();
		bean.setSelectedUser(users.get(0));
		bean.delete();
		
		assertThat(facadenFactory.getUserFacade().count(), equalTo(userCount -1 ));
	}

	@Test
	public void testAddUser() {
		int userCount = facadenFactory.getUserFacade().count();
		EditUserBean bean = new EditUserBean(new VBBServices(em), new UIMessagesTestImpl());
		bean.addUser();
		
		User user = bean.getSelectedUser();
		assertThat(user, not(nullValue()));
		
		String email = "hhuegel@got.wr";
		String password = "12345678";
		String userid = "hhuegel";
		String username = "Hugor Hügel";
		
		user.setEmail(email);
		user.setPassword(PasswordUtil.encryptPassword(password));
		user.setUserid(userid);
		user.setUsername(username);

		bean.saveUser();

		assertThat(facadenFactory.getUserFacade().count(), equalTo(userCount +1 ));
		
	}

}

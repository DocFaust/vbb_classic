package de.docfaust.vbb.servlet;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import de.docfaust.vbb.data.entity.Group;
import de.docfaust.vbb.data.entity.User;
import de.docfaust.vbb.data.util.JpaBaseRolledBackTestCase;
import de.docfaust.vbb.service.VBBServices;
import de.docfaust.vbb.util.PasswordUtil;
import de.docfaust.vbb.util.RegistrationState;

public class TestRegisterServlet extends JpaBaseRolledBackTestCase {

	@Test
	public void test(){
		assertThat(new RegisterServlet(), not(nullValue()));
	}
	
	@Test
	public void testRegisterOk() throws Exception {
		User user = new User();
		user.setUserid("renrew");
		user.setEmail("irgendwas@gmiks.de");
		user.setRegid("1");
		user.setPassword(PasswordUtil.encryptPassword("renate"));
		user.setUsername("Renrew Tsuaf");
		user.setState(RegistrationState.OPEN);

		
		facadenFactory.getUserFacade().create(user);
		

		VBBServices services = new VBBServices(em);
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpServletResponse response = mock(HttpServletResponse.class);

		when(request.getParameter("userid")).thenReturn("renrew");
		when(request.getParameter("regid")).thenReturn("1");
		PrintWriter writer = new PrintWriter("somefile.txt");
		when(response.getWriter()).thenReturn(writer);

		new RegisterServlet(services).doGet(request, response);

		verify(request, atLeast(1)).getParameter("userid");
		verify(request, atLeast(1)).getParameter("regid");
		assertEquals(380, FileUtils.readFileToString(new File("somefile.txt"), "UTF-8").length());
		assertTrue(FileUtils.readFileToString(new File("somefile.txt"), "UTF-8").contains("Registrierung erfolgreich"));

		new File("somefile.txt").delete();

		User user2 = facadenFactory.getUserFacade().findByUserName("renrew");
		Group userGroup = facadenFactory.getGroupFacade().findByName("READER");

		assertEquals(RegistrationState.PROOFED, user2.getState());
		assertThat(user2.getGroup().getName(), equalTo(userGroup.getName()));
	}

	@Test
	public void testRegisterWrongId() throws Exception {
		User user = new User();
		user.setUserid("renrew");
		user.setEmail("irgendwas@gmiks.de");
		user.setRegid("1");
		user.setPassword(PasswordUtil.encryptPassword("renate"));
		user.setUsername("Renrew Tsuaf");
		user.setState(RegistrationState.OPEN);

		
		facadenFactory.getUserFacade().create(user);
		

		VBBServices services = new VBBServices(em);
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpServletResponse response = mock(HttpServletResponse.class);

		when(request.getParameter("userid")).thenReturn("renrew");
		when(request.getParameter("regid")).thenReturn("2");
		PrintWriter writer = new PrintWriter("somefile.txt");
		when(response.getWriter()).thenReturn(writer);

		new RegisterServlet(services).doGet(request, response);

		verify(request, atLeast(1)).getParameter("userid");
		verify(request, atLeast(1)).getParameter("regid");
		assertEquals(212, FileUtils.readFileToString(new File("somefile.txt"), "UTF-8").length());
		assertTrue(FileUtils.readFileToString(new File("somefile.txt"), "UTF-8").contains("Falsche ID"));

		new File("somefile.txt").delete();

		User user2 = facadenFactory.getUserFacade().findByUserName("renrew");
		//Group userGroup = facadenFactory.getGroupFacade().findByName("USER");

		assertEquals(RegistrationState.OPEN, user2.getState());
	}

	@Test
	public void testRegisterUserNotFound() throws Exception {
		VBBServices services = new VBBServices(em);
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpServletResponse response = mock(HttpServletResponse.class);

		when(request.getParameter("userid")).thenReturn("renrew");
		when(request.getParameter("regid")).thenReturn("1");
		PrintWriter writer = new PrintWriter("somefile.txt");
		when(response.getWriter()).thenReturn(writer);

		new RegisterServlet(services).doGet(request, response);

		verify(request, atLeast(1)).getParameter("userid");
		verify(request, atLeast(1)).getParameter("regid");
		assertEquals(235, FileUtils.readFileToString(new File("somefile.txt"), "UTF-8").length());
		assertTrue(FileUtils.readFileToString(new File("somefile.txt"), "UTF-8").contains("Nicht registriert"));

		new File("somefile.txt").delete();
		assertNull(facadenFactory.getUserFacade().findByUserName("renrew"));
	}

	@Test
	public void testRegisterUserBereitsRegistriert() throws Exception {
		VBBServices services = new VBBServices(em);
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpServletResponse response = mock(HttpServletResponse.class);

		when(request.getParameter("userid")).thenReturn("aaltmann");
		when(request.getParameter("regid")).thenReturn("a");
		PrintWriter writer = new PrintWriter("somefile.txt");
		when(response.getWriter()).thenReturn(writer);

		new RegisterServlet(services).doGet(request, response);

		verify(request, atLeast(1)).getParameter("userid");
		verify(request, atLeast(1)).getParameter("regid");
		assertEquals(224, FileUtils.readFileToString(new File("somefile.txt"), "UTF-8").length());
		assertTrue(FileUtils.readFileToString(new File("somefile.txt"), "UTF-8").contains("Bereits registriert"));
		assertTrue(FileUtils.readFileToString(new File("somefile.txt"), "UTF-8").contains("aaltmann"));

		new File("somefile.txt").delete();
		assertNull(facadenFactory.getUserFacade().findByUserName("renrew"));
	}

	@Test
	public void testRegisterUserNoRegid() throws Exception {
		VBBServices services = new VBBServices(em);
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpServletResponse response = mock(HttpServletResponse.class);

		when(request.getParameter("userid")).thenReturn("aaltmann");
		when(request.getParameter("regid")).thenReturn(null);
		PrintWriter writer = new PrintWriter("somefile.txt");
		when(response.getWriter()).thenReturn(writer);

		new RegisterServlet(services).doGet(request, response);

		verify(request, atLeast(1)).getParameter("userid");
		verify(request, atLeast(1)).getParameter("regid");
		assertEquals(214, FileUtils.readFileToString(new File("somefile.txt"), "UTF-8").length());
		assertTrue(FileUtils.readFileToString(new File("somefile.txt"), "UTF-8").contains("Falsche ID"));
		assertTrue(FileUtils.readFileToString(new File("somefile.txt"), "UTF-8").contains("aaltmann"));

		new File("somefile.txt").delete();
		assertNull(facadenFactory.getUserFacade().findByUserName("renrew"));
	}

	@Test
	public void testRegisterUserNoUser() throws Exception {
		VBBServices services = new VBBServices(em);
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpServletResponse response = mock(HttpServletResponse.class);

		when(request.getParameter("userid")).thenReturn(null);
		when(request.getParameter("regid")).thenReturn("1");
		PrintWriter writer = new PrintWriter("somefile.txt");
		when(response.getWriter()).thenReturn(writer);

		new RegisterServlet(services).doGet(request, response);

		verify(request, atLeast(1)).getParameter("userid");
		verify(request, atLeast(1)).getParameter("regid");
		assertEquals(261, FileUtils.readFileToString(new File("somefile.txt"), "UTF-8").length());
		assertTrue(FileUtils.readFileToString(new File("somefile.txt"), "UTF-8").contains("Registrierungsfehler"));

		new File("somefile.txt").delete();
		assertNull(facadenFactory.getUserFacade().findByUserName("renrew"));
	}
}

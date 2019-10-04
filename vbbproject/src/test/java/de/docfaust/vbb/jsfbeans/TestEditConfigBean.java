package de.docfaust.vbb.jsfbeans;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.jupiter.api.Test;

import de.docfaust.vbb.ServiceCreator;
import de.docfaust.vbb.data.util.JpaBaseRolledBackTestCase;
import de.docfaust.vbb.util.UIMessagesTestImpl;

public class TestEditConfigBean extends JpaBaseRolledBackTestCase {
	
	@Test
	public void test() {
		assertThat(new EditConfigBean(), not(nullValue()));

	}

	@Test
	public void testInit() {
		initBean();
	}

	@Test
	public void testSetValues() {
		EditConfigBean bean = initBean();
		assertThat(facadenFactory.getConfigFacade().count(), equalTo(6));
		
		bean.setDomain("domain");
		bean.setFrom("vom");
		bean.setSubject("Subjekt");

		assertThat(facadenFactory.getConfigFacade().count(), equalTo(6));
		
		assertThat(bean.getDomain(), equalTo("domain"));
		assertThat(bean.getFrom(), equalTo("vom"));
		assertThat(bean.getSubject(), equalTo("Subjekt"));

		bean.save();
		
		assertThat(facadenFactory.getConfigFacade().count(), equalTo(6));

		assertThat(bean.getDomain(), equalTo("domain"));
		assertThat(bean.getFrom(), equalTo("vom"));
		assertThat(bean.getSubject(), equalTo("Subjekt"));

		bean.reset();
		
		assertThat(facadenFactory.getConfigFacade().count(), equalTo(6));
	}


	private EditConfigBean initBean() {
		ServiceCreator sc = new ServiceCreator(em);
		EditConfigBean bean = new EditConfigBean(new UIMessagesTestImpl(),sc.getConfigService());
		bean.init();
		assertThat(bean.getDomain(), equalTo("http://localhost:8080/vbb"));
		assertThat(bean.getFrom(), equalTo("wfaust@localhost.net"));
		assertThat(bean.getSubject(), equalTo("Registrierung bei der Volleyballbuchung"));
		return bean;
	}
}

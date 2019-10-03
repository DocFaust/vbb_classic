package de.docfaust.vbb.jsfbeans;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.jupiter.api.Test;

import de.docfaust.vbb.ServiceCreator;
import de.docfaust.vbb.data.util.JpaBaseRolledBackTestCase;
import de.docfaust.vbb.util.UIMessagesTestImpl;

public class TestEditSpielerBean extends JpaBaseRolledBackTestCase {
	@Test
	public void test() {
		assertThat(new EditSpielerBean(), not(nullValue()));

	}
	@Test
	public void testInit() {
		EditSpielerBean bean = initBean();
		assertThat(bean.getSpieler().size(), equalTo(10));
		assertThat(bean.getSelectedSpieler().getName(), equalTo("Alfred Altmann"));
	}
	private EditSpielerBean initBean() {
		ServiceCreator sc = new ServiceCreator(em);
		EditSpielerBean bean = new EditSpielerBean(new UIMessagesTestImpl(), sc.getSpielerService());
		bean.init();
		return bean;
	}

	@Test
	public void testAddSpieler() {
		EditSpielerBean bean = initBean();
		assertThat(bean.getSpieler().size(), equalTo(10));
		assertThat(bean.getSelectedSpieler().getName(), equalTo("Alfred Altmann"));
		
		bean.addSpieler();

		assertThat(bean.getSpieler().size(), equalTo(10));
		assertThat(bean.getSelectedSpieler().getName(), nullValue());
		assertThat(facadenFactory.getSpielerFacade().count(), equalTo(10));
	}

	@Test
	public void testSaveSpieler() {
		EditSpielerBean bean = initBean();
		assertThat(bean.getSpieler().size(), equalTo(10));
		assertThat(bean.getSelectedSpieler().getName(), equalTo("Alfred Altmann"));
		
		bean.addSpieler();
		
		assertThat(bean.getSelectedSpieler().getName(), nullValue());
		String name = "Zenzi Zuagroast";
		bean.getSelectedSpieler().setName(name);
		String email = "zzuagroast@hh.by";
		bean.getSelectedSpieler().setEmail(email);
		
		bean.saveSpieler();
		
		assertThat(bean.getSpieler().size(), equalTo(11));
		assertThat(bean.getSelectedSpieler().getName(), not(nullValue()));
		assertThat(bean.getSelectedSpieler().getName(), equalTo(name));
		assertThat(bean.getSelectedSpieler().getEmail(), equalTo(email));
		assertThat(facadenFactory.getSpielerFacade().count(), equalTo(11));
	}

	@Test
	public void testDeleteSpieler() {
		EditSpielerBean bean = initBean();
		assertThat(bean.getSpieler().size(), equalTo(10));
		assertThat(bean.getSelectedSpieler().getName(), equalTo("Alfred Altmann"));
		
		bean.addSpieler();
		
		assertThat(bean.getSelectedSpieler().getName(), nullValue());
		String name = "Zenzi Zuagroast";
		bean.getSelectedSpieler().setName(name);
		String email = "zzuagroast@hh.by";
		bean.getSelectedSpieler().setEmail(email);
		
		bean.saveSpieler();
		
		assertThat(bean.getSpieler().size(), equalTo(11));
		assertThat(bean.getSelectedSpieler().getName(), not(nullValue()));
		assertThat(bean.getSelectedSpieler().getName(), equalTo(name));
		assertThat(bean.getSelectedSpieler().getEmail(), equalTo(email));
		assertThat(facadenFactory.getSpielerFacade().count(), equalTo(11));
		
		bean.deleteSpieler();
		
		assertThat(bean.getSelectedSpieler().getName(), not(nullValue()));
		assertThat(bean.getSpieler().size(), equalTo(10));
		assertThat(facadenFactory.getSpielerFacade().count(), equalTo(10));
	}
	@Test
	public void testDeleteSpielerError() {
		EditSpielerBean bean = initBean();
		assertThat(bean.getSpieler().size(), equalTo(10));
		assertThat(bean.getSelectedSpieler().getName(), equalTo("Alfred Altmann"));
		
		
		
		bean.deleteSpieler();
		
		assertThat(bean.getSelectedSpieler().getName(), not(nullValue()));
		assertThat(bean.getSpieler().size(), equalTo(10));
		assertThat(facadenFactory.getSpielerFacade().count(), equalTo(10));
	}
	@Test
	public void testDeleteSpielerNoneSelected() {
		EditSpielerBean bean = initBean();
		assertThat(bean.getSpieler().size(), equalTo(10));
		assertThat(bean.getSelectedSpieler().getName(), equalTo("Alfred Altmann"));
		
		bean.setSelectedSpieler(null);
		
		bean.deleteSpieler();
		assertThat(bean.getSpieler().size(), equalTo(10));
		assertThat(facadenFactory.getSpielerFacade().count(), equalTo(10));
	}
}

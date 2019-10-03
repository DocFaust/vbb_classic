package de.docfaust.vbb.jsfbeans;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.Date;

import org.junit.jupiter.api.Test;

import de.docfaust.vbb.ServiceCreator;
import de.docfaust.vbb.data.util.JpaBaseRolledBackTestCase;
import de.docfaust.vbb.util.UIMessagesTestImpl;

public class TestReorgDatabaseBean extends JpaBaseRolledBackTestCase {
	@Test
	public void test() {
		assertThat(new ReorgDatabaseBean(), not(nullValue()));

	}

	@Test
	public void testStarteBuchungsschnitt() {
		ReorgDatabaseBean bean = initBean();
		Date selectedDatum = new Date();
		bean.setSelectedDatum(selectedDatum);
		assertThat(bean.getSelectedDatum(), equalTo(selectedDatum));
		bean.starteBuchungsschnitt();
		
		assertThat(facadenFactory.getSeasonFacade().count(), equalTo(1));
		assertThat(facadenFactory.getSpielFacade().count(), equalTo(1));
		assertThat(facadenFactory.getBuchungFacade().count(), equalTo(facadenFactory.getSpielerFacade().count()));
		
	}

	private ReorgDatabaseBean initBean() {
		ServiceCreator sc = new ServiceCreator(em);
		ReorgDatabaseBean bean = new ReorgDatabaseBean(new UIMessagesTestImpl(), sc.getCutOffService());
		return bean;
	}
	
	@Test
	public void testStarteBuchungsschnittNOK() {
		ReorgDatabaseBean bean = initBean();
		Date selectedDatum = null;
		bean.setSelectedDatum(selectedDatum);
		assertThat(bean.getSelectedDatum(), equalTo(selectedDatum));
		bean.starteBuchungsschnitt();
		
		assertThat(facadenFactory.getSeasonFacade().count(), equalTo(3));
		assertThat(facadenFactory.getSpielFacade().count(), equalTo(4));
		
	}

}

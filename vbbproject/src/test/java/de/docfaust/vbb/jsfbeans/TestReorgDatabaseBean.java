package de.docfaust.vbb.jsfbeans;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.util.Date;

import org.junit.jupiter.api.Test;

import de.docfaust.vbb.data.util.JpaBaseRolledBackTestCase;
import de.docfaust.vbb.service.VBBServices;
import de.docfaust.vbb.util.UIMessagesTestImpl;

public class TestReorgDatabaseBean extends JpaBaseRolledBackTestCase {
	@Test
	public void test() {
		assertThat(new ReorgDatabaseBean(), not(nullValue()));

	}

	@Test
	public void testStarteBuchungsschnitt() {
		ReorgDatabaseBean bean = new ReorgDatabaseBean(new VBBServices(em), new UIMessagesTestImpl());
		Date selectedDatum = new Date();
		bean.setSelectedDatum(selectedDatum);
		assertThat(bean.getSelectedDatum(), equalTo(selectedDatum));
		bean.starteBuchungsschnitt();
		
		assertThat(facadenFactory.getSeasonFacade().count(), equalTo(1));
		assertThat(facadenFactory.getSpielFacade().count(), equalTo(1));
		assertThat(facadenFactory.getBuchungFacade().count(), equalTo(facadenFactory.getSpielerFacade().count()));
		
	}
	
	@Test
	public void testStarteBuchungsschnittNOK() {
		ReorgDatabaseBean bean = new ReorgDatabaseBean(new VBBServices(em), new UIMessagesTestImpl());
		Date selectedDatum = null;
		bean.setSelectedDatum(selectedDatum);
		assertThat(bean.getSelectedDatum(), equalTo(selectedDatum));
		bean.starteBuchungsschnitt();
		
		assertThat(facadenFactory.getSeasonFacade().count(), equalTo(3));
		assertThat(facadenFactory.getSpielFacade().count(), equalTo(4));
		
	}

}

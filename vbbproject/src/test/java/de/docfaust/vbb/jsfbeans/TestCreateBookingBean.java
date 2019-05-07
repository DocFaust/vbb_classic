package de.docfaust.vbb.jsfbeans;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import de.docfaust.vbb.data.entity.Season;
import de.docfaust.vbb.data.entity.Spieler;
import de.docfaust.vbb.data.util.JpaBaseRolledBackTestCase;
import de.docfaust.vbb.service.VBBServices;
import de.docfaust.vbb.util.EntityFactory;
import de.docfaust.vbb.util.UIMessagesTestImpl;

public class TestCreateBookingBean extends JpaBaseRolledBackTestCase {
	
	@Test
	public void test() {
		assertThat(new CreateIndividualBookingBean(), not(nullValue()));

	}

	@Test
	public void testInit() {
		initBean();
	}

	@Test
	public void testAddBuchung() {
		CreateIndividualBookingBean bean = initBean();
		
		bean.addBuchung();

		assertThat(bean.getSelectedBuchung().getDatum(), equalTo(bean.getSelectedSpiel().getDatum()));
		assertThat(bean.getSelectedBuchung().getSpiel(), equalTo(bean.getSelectedSpiel()));
		assertThat(facadenFactory.getBuchungFacade().count(), equalTo(31));
		assertThat(facadenFactory.getSpielFacade().count(), equalTo(4));
	}

	@Test
	public void testSaveBuchungOk() {
		Season season = EntityFactory.createSeason(EntityFactory.getActualDateAddDays(0), EntityFactory.getActualDateAddDays(1), BigDecimal.TEN, "Fake");
		facadenFactory.getSeasonFacade().create(season);
		
		CreateIndividualBookingBean bean = initBean();
		
		bean.addBuchung();

		assertThat(bean.getSelectedBuchung().getDatum(), equalTo(bean.getSelectedSpiel().getDatum()));
		assertThat(bean.getSelectedBuchung().getSpiel(), equalTo(bean.getSelectedSpiel()));
		assertThat(facadenFactory.getBuchungFacade().count(), equalTo(31));
		assertThat(facadenFactory.getSpielFacade().count(), equalTo(4));
		
		String description = "Buchung Test";
		BigDecimal price = BigDecimal.valueOf(56);
		Spieler spieler = facadenFactory.getSpielerFacade().find(1);
		
		bean.getSelectedBuchung().setDescription(description);
		bean.getSelectedBuchung().setPrice(price);
		bean.getSelectedBuchung().setSpieler(spieler);
		
		bean.saveBuchungen();
		
		assertThat(facadenFactory.getSpielFacade().count(), equalTo(5));
		assertThat(facadenFactory.getBuchungFacade().count(), equalTo(32));
		assertThat(bean.getSelectedBuchung().getDescription(), not(nullValue()));
		assertThat(bean.getSelectedBuchung().getDescription(), equalTo(description));
		assertThat(bean.getSelectedBuchung().getPrice(), equalTo(price));
		assertThat(bean.getSelectedBuchung().getSpieler(), equalTo(spieler));
		assertThat(bean.getSelectedBuchung().getSpiel(), equalTo(bean.getSelectedSpiel()));
	}
	
	
	@Test
	public void testGetSaldo(){
		Season season = EntityFactory.createSeason(EntityFactory.getActualDateAddDays(0), EntityFactory.getActualDateAddDays(1), BigDecimal.TEN, "Fake");
		facadenFactory.getSeasonFacade().create(season);
		CreateIndividualBookingBean bean = initBean();
		assertThat(bean.getSaldo().doubleValue(), equalTo(0D));
		assertThat(bean.getCompleteSaldo().doubleValue(), equalTo(0D));
		
		bean.addBuchung();
		assertThat(bean.getSelectedBuchung().getDatum(), equalTo(bean.getSelectedSpiel().getDatum()));
		assertThat(bean.getSelectedBuchung().getSpiel(), equalTo(bean.getSelectedSpiel()));
		assertThat(facadenFactory.getBuchungFacade().count(), equalTo(31));
		assertThat(facadenFactory.getSpielFacade().count(), equalTo(4));
		
		String description = "Buchung Test";
		BigDecimal price = BigDecimal.valueOf(56);
		Spieler spieler = facadenFactory.getSpielerFacade().find(1);
		
		bean.getSelectedBuchung().setDescription(description);
		bean.getSelectedBuchung().setPrice(price);
		bean.getSelectedBuchung().setSpieler(spieler);

		assertThat(bean.getSaldo().doubleValue(), equalTo(56D));
		assertThat(bean.getCompleteSaldo().doubleValue(), equalTo(0D));
		
		bean.saveBuchungen();

		assertThat(bean.getSaldo().doubleValue(), equalTo(56D));
		assertThat(bean.getCompleteSaldo().doubleValue(), equalTo(56D));
		
		
		bean.addBuchung();
		
		assertThat(bean.getSelectedBuchung().getDatum(), equalTo(bean.getSelectedSpiel().getDatum()));
		assertThat(bean.getSelectedBuchung().getSpiel(), equalTo(bean.getSelectedSpiel()));
		assertThat(facadenFactory.getBuchungFacade().count(), equalTo(32));
		assertThat(facadenFactory.getSpielFacade().count(), equalTo(5));
		
		description = "GegenBuchung Test";
		price = BigDecimal.valueOf(-56);
		spieler = facadenFactory.getSpielerFacade().find(1);
		
		bean.getSelectedBuchung().setDescription(description);
		bean.getSelectedBuchung().setPrice(price);
		bean.getSelectedBuchung().setSpieler(spieler);

		assertThat(bean.getSaldo().doubleValue(), equalTo(0D));
		assertThat(bean.getCompleteSaldo().doubleValue(), equalTo(56D));
		
		bean.saveBuchungen();

		assertThat(facadenFactory.getSpielFacade().count(), equalTo(5));
		assertThat(facadenFactory.getBuchungFacade().count(), equalTo(33));
		assertThat(bean.getSelectedBuchung().getDescription(), not(nullValue()));
		assertThat(bean.getSelectedBuchung().getDescription(), equalTo(description));
		assertThat(bean.getSelectedBuchung().getPrice(), equalTo(price));
		assertThat(bean.getSelectedBuchung().getSpieler(), equalTo(spieler));
		assertThat(bean.getSelectedBuchung().getSpiel(), equalTo(bean.getSelectedSpiel()));

	}

	@Test
	public void testCallSetters() {
		CreateIndividualBookingBean bean = new CreateIndividualBookingBean(new VBBServices(em), new UIMessagesTestImpl());
		bean.init();
//		bean.setSelectedBuchung(selectedBuchung);
//		bean.setSelectedDate(selectedDate);
//		bean.setSelectedSpiel(selectedSpiel);
		
	}

	private CreateIndividualBookingBean initBean() {
		CreateIndividualBookingBean bean = new CreateIndividualBookingBean(new VBBServices(em), new UIMessagesTestImpl());
		bean.init();
		assertThat(bean.getAlleSpieler().size(), equalTo(10));
		assertThat(bean.getSelectedSpiel().getId(), equalTo(0));
		assertThat(bean.getCompleteSaldo().doubleValue(), equalTo(0D));
		assertThat(bean.getSaldo().doubleValue(), equalTo(0D));
		assertThat(bean.getSelectedSpiel().getDatum(), equalTo(bean.getSelectedDate()));
		return bean;
	}
}

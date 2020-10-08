package de.docfaust.vbb.jsfbeans;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import de.docfaust.vbb.ServiceCreator;
import de.docfaust.vbb.data.entity.Spiel;
import de.docfaust.vbb.data.entity.Spieler;
import de.docfaust.vbb.data.util.JpaBaseRolledBackTestCase;
import de.docfaust.vbb.util.UIMessagesTestImpl;

public class TestSearchSpielBean extends JpaBaseRolledBackTestCase {
	@Test
	public void test() {
		assertThat(new SearchSpielBean(), not(nullValue()));

	}

	@Test
	public void testInit() {
		initBean();
	}

	@Test
	public void testAddBuchung() {
		SearchSpielBean bean = initBean();
		
		bean.addBuchung();

		assertThat(bean.getSelectedBuchung().getDatum(), equalTo(bean.getSelectedSpiel().getDatum()));
		assertThat(bean.getSelectedBuchung().getSpiel(), equalTo(bean.getSelectedSpiel()));
		assertThat(facadenFactory.getBuchungFacade().count(), equalTo(31));
		assertThat(facadenFactory.getSpielFacade().count(), equalTo(4));
	}

	@Test
	public void testSaveBuchungOk() {
		SearchSpielBean bean = initBean();
		
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
		
		bean.saveBuchung();
		
		assertThat(bean.getSpiele().size(), equalTo(4));
		assertThat(facadenFactory.getSpielFacade().count(), equalTo(4));
		assertThat(facadenFactory.getBuchungFacade().count(), equalTo(32));
		assertThat(bean.getSelectedBuchung().getDescription(), not(nullValue()));
		assertThat(bean.getSelectedBuchung().getDescription(), equalTo(description));
		assertThat(bean.getSelectedBuchung().getPrice(), equalTo(price));
		assertThat(bean.getSelectedBuchung().getSpieler(), equalTo(spieler));
		assertThat(bean.getSelectedBuchung().getSpiel(), equalTo(bean.getSelectedSpiel()));
	}
	
	@Test
	public void testSaveSeasonNoDescription() {
		SearchSpielBean bean = initBean();
		
		bean.addBuchung();

		assertThat(bean.getSelectedBuchung().getDatum(), equalTo(bean.getSelectedSpiel().getDatum()));
		assertThat(bean.getSelectedBuchung().getSpiel(), equalTo(bean.getSelectedSpiel()));
		assertThat(facadenFactory.getBuchungFacade().count(), equalTo(31));
		assertThat(facadenFactory.getSpielFacade().count(), equalTo(4));
		
		String description = null;
		BigDecimal price = BigDecimal.valueOf(56);
		Spieler spieler = facadenFactory.getSpielerFacade().find(1);
		
		bean.getSelectedBuchung().setDescription(description);
		bean.getSelectedBuchung().setPrice(price);
		bean.getSelectedBuchung().setSpieler(spieler);
		
		bean.saveBuchung();
		
		assertThat(bean.getSpiele().size(), equalTo(4));
		assertThat(facadenFactory.getSpielFacade().count(), equalTo(4));
		assertThat(facadenFactory.getBuchungFacade().count(), equalTo(31));
		assertThat(bean.getSelectedBuchung().getDescription(), equalTo(description));
		assertThat(bean.getSelectedBuchung().getPrice(), equalTo(price));
		assertThat(bean.getSelectedBuchung().getSpieler(), equalTo(spieler));
		assertThat(bean.getSelectedBuchung().getSpiel(), equalTo(bean.getSelectedSpiel()));
	}
	
	@Test
	public void testSaveSeasonNoSpieler() {
		SearchSpielBean bean = initBean();
		
		bean.addBuchung();

		assertThat(bean.getSelectedBuchung().getDatum(), equalTo(bean.getSelectedSpiel().getDatum()));
		assertThat(bean.getSelectedBuchung().getSpiel(), equalTo(bean.getSelectedSpiel()));
		assertThat(facadenFactory.getBuchungFacade().count(), equalTo(31));
		assertThat(facadenFactory.getSpielFacade().count(), equalTo(4));
		
		String description = "Buchung Test";
		BigDecimal price = BigDecimal.valueOf(56);
		Spieler spieler = null;
		
		bean.getSelectedBuchung().setDescription(description);
		bean.getSelectedBuchung().setPrice(price);
		bean.getSelectedBuchung().setSpieler(spieler);
		
		bean.saveBuchung();
		
		assertThat(bean.getSpiele().size(), equalTo(4));
		assertThat(facadenFactory.getSpielFacade().count(), equalTo(4));
		assertThat(facadenFactory.getBuchungFacade().count(), equalTo(31));
		assertThat(bean.getSelectedBuchung().getDescription(), equalTo(description));
		assertThat(bean.getSelectedBuchung().getPrice(), equalTo(price));
		assertThat(bean.getSelectedBuchung().getSpieler(), equalTo(spieler));
		assertThat(bean.getSelectedBuchung().getSpiel(), equalTo(bean.getSelectedSpiel()));
	}
	
	@Test
	public void testSaveSeasonNoPrice() {
		SearchSpielBean bean = initBean();
		
		bean.addBuchung();

		assertThat(bean.getSelectedBuchung().getDatum(), equalTo(bean.getSelectedSpiel().getDatum()));
		assertThat(bean.getSelectedBuchung().getSpiel(), equalTo(bean.getSelectedSpiel()));
		assertThat(facadenFactory.getBuchungFacade().count(), equalTo(31));
		assertThat(facadenFactory.getSpielFacade().count(), equalTo(4));
		
		String description = "Buchung Test";
		BigDecimal price = null;
		Spieler spieler = facadenFactory.getSpielerFacade().find(1);
		
		bean.getSelectedBuchung().setDescription(description);
		bean.getSelectedBuchung().setPrice(price);
		bean.getSelectedBuchung().setSpieler(spieler);
		
		bean.saveBuchung();
		
		assertThat(bean.getSpiele().size(), equalTo(4));
		assertThat(facadenFactory.getSpielFacade().count(), equalTo(4));
		assertThat(facadenFactory.getBuchungFacade().count(), equalTo(31));
		assertThat(bean.getSelectedBuchung().getDescription(), equalTo(description));
		assertThat(bean.getSelectedBuchung().getPrice(), equalTo(price));
		assertThat(bean.getSelectedBuchung().getSpieler(), equalTo(spieler));
		assertThat(bean.getSelectedBuchung().getSpiel(), equalTo(bean.getSelectedSpiel()));

	}
	@Test
	public void testDeleteBuchung() {
		SearchSpielBean bean = initBean();
		
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
		
		bean.saveBuchung();
		
		assertThat(bean.getSpiele().size(), equalTo(4));
		assertThat(facadenFactory.getSpielFacade().count(), equalTo(4));
		assertThat(facadenFactory.getBuchungFacade().count(), equalTo(32));
		assertThat(bean.getSelectedBuchung().getDescription(), not(nullValue()));
		assertThat(bean.getSelectedBuchung().getDescription(), equalTo(description));
		assertThat(bean.getSelectedBuchung().getPrice(), equalTo(price));
		assertThat(bean.getSelectedBuchung().getSpieler(), equalTo(spieler));
		assertThat(bean.getSelectedBuchung().getSpiel(), equalTo(bean.getSelectedSpiel()));

		
		bean.deleteBuchung();
		
		assertThat(bean.getSpiele().size(), equalTo(4));
		assertThat(facadenFactory.getSpielFacade().count(), equalTo(4));
		assertThat(facadenFactory.getBuchungFacade().count(), equalTo(31));
		assertThat(bean.getSelectedBuchung().getDescription(), not(nullValue()));
	}
	
	@Test
	public void testDeleteSeasonNoneSelected() {
		SearchSpielBean bean = initBean();
		
		bean.setSelectedBuchung(null);
		
		bean.deleteBuchung();
		
		assertThat(bean.getSpiele().size(), equalTo(4));
		assertThat(facadenFactory.getSpielFacade().count(), equalTo(4));
		assertThat(facadenFactory.getBuchungFacade().count(), equalTo(31));
	}
	
	@Test
	public void testDeleteSpiel(){
		SearchSpielBean bean = initBean();
		
		bean.deleteSpiel();
		
		assertThat(bean.getSpiele().size(), equalTo(3));
		assertThat(facadenFactory.getSpielFacade().count(), equalTo(3));
		assertThat(facadenFactory.getBuchungFacade().count(), equalTo(27));
		assertThat(bean.getSelectedBuchung().getDescription(), not(nullValue()));
	}
	
	@Test
	public void testDeleteSpielNoneSelected(){
		SearchSpielBean bean = initBean();
		bean.setSelectedSpiel(null);
		bean.deleteSpiel();
		
		assertThat(bean.getSpiele().size(), equalTo(4));
		assertThat(facadenFactory.getSpielFacade().count(), equalTo(4));
		assertThat(facadenFactory.getBuchungFacade().count(), equalTo(31));
	}
	
	@Test
	public void testDeleteSpielNotFound(){
		SearchSpielBean bean = initBean();
		Spiel spiel = new Spiel();
		spiel.setId(404);
		bean.setSelectedSpiel(spiel);
		bean.deleteSpiel();
		
		assertThat(bean.getSpiele().size(), equalTo(4));
		assertThat(facadenFactory.getSpielFacade().count(), equalTo(4));
		assertThat(facadenFactory.getBuchungFacade().count(), equalTo(31));
	}
	@Test
	public void testGetSaldo(){
		SearchSpielBean bean = initBean();
		
		assertThat(bean.getSaldo().doubleValue(), equalTo(0D));
	}

	@Test
	public void testGetSaldoNoSelection(){
		SearchSpielBean bean = initBean();
		bean.setSelectedSpiel(null);
		assertThat(bean.getSaldo().doubleValue(), equalTo(0D));
	}
	
	@Test
	public void testDisable(){
		SearchSpielBean bean = initBean();
		bean.setSelectedSpiel(null);
		bean.setNewGameDisabled(false);
		assertThat(bean.isNewGameDisabled(), equalTo(true));
	}

	private SearchSpielBean initBean() {
		ServiceCreator sc = new ServiceCreator(em);
		SearchSpielBean bean = new SearchSpielBean(new UIMessagesTestImpl(), sc.getSpielerService(), sc.getSpielService(), sc.getBuchungService());
		bean.init();
		assertThat(bean.getSpiele().size(), equalTo(4));
		assertThat(bean.getSelectedSpiel().getId(), equalTo(1));
		assertThat(bean.getSpieler().size(), equalTo(10));
		assertThat(bean.getSelectedBuchung().getId(), equalTo(1));

		return bean;
	}
}

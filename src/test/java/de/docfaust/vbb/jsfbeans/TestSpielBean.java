package de.docfaust.vbb.jsfbeans;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.junit.jupiter.api.Test;

import de.docfaust.vbb.ServiceCreator;
import de.docfaust.vbb.data.entity.Spieler;
import de.docfaust.vbb.data.util.JpaBaseRolledBackTestCase;
import de.docfaust.vbb.util.UIMessagesTestImpl;

public class TestSpielBean extends JpaBaseRolledBackTestCase {
	@Test
	public void test() {
		assertThat(new SpielBean(), not(nullValue()));

	}

	@Test
	public void testGetSpielerList() {
		SpielBean bean = initBean();
		List<Spieler> spielerList = bean.getSpielerList();
		assertThat(spielerList.size(), equalTo(facadenFactory.getSpielerFacade().count()));
	}

	private SpielBean initBean() {
		ServiceCreator sc = new ServiceCreator(em);
		SpielBean bean = new SpielBean(new UIMessagesTestImpl(), sc.getSpielerService(), sc.getSpielService());
		bean.init();
		return bean;
	}

	@Test
	public void testSaveSpiel() throws ParseException {
		assertThat(facadenFactory.getSpielFacade().count(), equalTo(4));
		assertThat(facadenFactory.getBuchungFacade().count(), equalTo(31));
		SpielBean bean = initBean();
		List<Spieler> list = bean.getSpielerList();
		
		list.get(1).setAnwesend(true);
		list.get(1).setBezahlt(true);
		list.get(3).setAnwesend(true);
		list.get(5).setAnwesend(true);
		list.get(6).setAnwesend(true);
		bean.setDatum(new SimpleDateFormat("dd.MM.yyyy").parse("07.01.2014"));
		bean.saveSpiel();
		
		assertThat(facadenFactory.getSpielFacade().count(), equalTo(5));
		assertThat(facadenFactory.getBuchungFacade().count(), equalTo(36));
	}

	@Test
	public void testSaveSpielSpielExists() throws ParseException {
		assertThat(facadenFactory.getSpielFacade().count(), equalTo(4));
		assertThat(facadenFactory.getBuchungFacade().count(), equalTo(31));
		SpielBean bean = initBean();
		List<Spieler> list = bean.getSpielerList();
		
		list.get(1).setAnwesend(true);
		list.get(1).setBezahlt(true);
		list.get(3).setAnwesend(true);
		list.get(5).setAnwesend(true);
		list.get(6).setAnwesend(true);
		bean.setDatum(new SimpleDateFormat("dd.MM.yyyy").parse("01.01.2014"));
		
		bean.saveSpiel();
		
		assertThat(facadenFactory.getSpielFacade().count(), equalTo(4));
		assertThat(facadenFactory.getBuchungFacade().count(), equalTo(31));
	}
	
	@Test
	public void testSaveSpielNoSeason() throws ParseException {
		assertThat(facadenFactory.getSpielFacade().count(), equalTo(4));
		assertThat(facadenFactory.getBuchungFacade().count(), equalTo(31));
		SpielBean bean = initBean();
		List<Spieler> list = bean.getSpielerList();
		
		list.get(1).setAnwesend(true);
		list.get(1).setBezahlt(true);
		list.get(3).setAnwesend(true);
		list.get(5).setAnwesend(true);
		list.get(6).setAnwesend(true);
		//bean.setDatum(new SimpleDateFormat("dd.MM.yyyy").parse("01.01.2014"));
		
		bean.saveSpiel();
		
		assertThat(facadenFactory.getSpielFacade().count(), equalTo(4));
		assertThat(facadenFactory.getBuchungFacade().count(), equalTo(31));
	}

	@Test
	public void testSaveSpielNotPayed() throws ParseException {
		assertThat(facadenFactory.getSpielFacade().count(), equalTo(4));
		assertThat(facadenFactory.getBuchungFacade().count(), equalTo(31));
		SpielBean bean = initBean();
		List<Spieler> list = bean.getSpielerList();
		
		list.get(1).setAnwesend(true);
		//list.get(1).setBezahlt(true);
		list.get(3).setAnwesend(true);
		list.get(5).setAnwesend(true);
		list.get(6).setAnwesend(true);
		bean.setDatum(new SimpleDateFormat("dd.MM.yyyy").parse("07.01.2014"));
		
		bean.saveSpiel();
		
		assertThat(facadenFactory.getSpielFacade().count(), equalTo(4));
		assertThat(facadenFactory.getBuchungFacade().count(), equalTo(31));
	}
	@Test
	public void testSaveSpielMoreThanOnePayer() throws ParseException {
		assertThat(facadenFactory.getSpielFacade().count(), equalTo(4));
		assertThat(facadenFactory.getBuchungFacade().count(), equalTo(31));
		SpielBean bean = initBean();
		List<Spieler> list = bean.getSpielerList();
		
		list.get(1).setAnwesend(true);
		list.get(1).setBezahlt(true);
		list.get(3).setAnwesend(true);
		list.get(3).setBezahlt(true);
		list.get(5).setAnwesend(true);
		list.get(6).setAnwesend(true);
		bean.setDatum(new SimpleDateFormat("dd.MM.yyyy").parse("07.01.2014"));
		
		bean.saveSpiel();
		
		assertThat(facadenFactory.getSpielFacade().count(), equalTo(4));
		assertThat(facadenFactory.getBuchungFacade().count(), equalTo(31));
	}
//	@Test
//	public void testGetSaldo() {
//		@SuppressWarnings("serial")
//		Map<String, BigDecimal> exp = new Hashtable<String, BigDecimal>() {
//			{
//				put("Alfred Altmann", new BigDecimal(-7.50));
//				put("Ines Ignorant", new BigDecimal(7.00));
//				put("Doreen Durstig", new BigDecimal(-4.50));
//				put("Hans Hohlbirne", new BigDecimal(-2.00));
//				put("Franz Fröhlich", new BigDecimal(-2.00));
//				put("Gretchen Gröhl", new BigDecimal(-2.00));
//				put("Claus Caspar", new BigDecimal(1.50));
//				put("Bernd Brot", new BigDecimal(-7.50));
//				put("Johann Jochbein", new BigDecimal(9.00));
//				put("Erich Ehrlich", new BigDecimal(8.00));
//			}
//		};
//
//		SpielBean bean = initBean();
//		List<Entry<String, BigDecimal>> list = bean.getSaldo();
//		assertThat(list.size(), equalTo(facadenFactory.getSpielerFacade().count()));
//		for (Entry<String, BigDecimal> entry : list) {
//			assertThat(entry.getValue().doubleValue(), equalTo(exp.get(entry.getKey()).doubleValue()));
//		}
//	}

}

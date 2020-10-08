package de.docfaust.vbb.jsfbeans;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.jupiter.api.Test;

import de.docfaust.vbb.ServiceCreator;
import de.docfaust.vbb.data.util.JpaBaseRolledBackTestCase;
import de.docfaust.vbb.util.UIMessagesTestImpl;

public class TestEditSeasonBean extends JpaBaseRolledBackTestCase {
	@Test
	public void test() {
		assertThat(new EditSeasonBean(), not(nullValue()));

	}

	@Test
	public void testInit() {
		initBean();
	}

	@Test
	public void testAddSeason() {
		EditSeasonBean bean = initBean();
		
		bean.addSeason();

		assertThat(bean.getSeasons().size(), equalTo(4));
		assertThat(bean.getSelectedSeason().getDescription(), nullValue());
		assertThat(facadenFactory.getSeasonFacade().count(), equalTo(3));
	}

	@Test
	public void testSaveSeasonOk() {
		EditSeasonBean bean = initBean();
		
		bean.addSeason();
		
		assertThat(bean.getSelectedSeason().getDescription(), nullValue());
		String description = "Season 4";
		BigDecimal price = BigDecimal.valueOf(56);
		Date startdate = new Date();
		Date enddate = new Date();
		
		bean.getSelectedSeason().setDescription(description);
		bean.getSelectedSeason().setPrice(price);
		bean.getSelectedSeason().setStartdate(startdate);
		bean.getSelectedSeason().setEnddate(enddate);
		
		bean.saveSeason();
		
		assertThat(bean.getSeasons().size(), equalTo(4));
		assertThat(facadenFactory.getSeasonFacade().count(), equalTo(4));
		assertThat(bean.getSelectedSeason().getDescription(), not(nullValue()));
		assertThat(bean.getSelectedSeason().getDescription(), equalTo(description));
		assertThat(bean.getSelectedSeason().getPrice(), equalTo(price));
		assertThat(bean.getSelectedSeason().getStartdate(), equalTo(startdate));
		assertThat(bean.getSelectedSeason().getEnddate(), equalTo(enddate));
	}
	@Test
	public void testSaveSeasonNoPrice() {
		EditSeasonBean bean = initBean();
		
		bean.addSeason();
		
		assertThat(bean.getSelectedSeason().getDescription(), nullValue());
		String description = "Season 4";
		BigDecimal price = null;
		Date startdate = new Date();
		Date enddate = new Date();
		
		bean.getSelectedSeason().setDescription(description);
		bean.getSelectedSeason().setPrice(price);
		bean.getSelectedSeason().setStartdate(startdate);
		bean.getSelectedSeason().setEnddate(enddate);
		
		bean.saveSeason();
		
		assertThat(bean.getSeasons().size(), equalTo(3));
		assertThat(facadenFactory.getSeasonFacade().count(), equalTo(3));
		assertThat(bean.getSelectedSeason().getDescription(), not(nullValue()));
	}
	@Test
	public void testSaveSeasonNoStartDate() {
		EditSeasonBean bean = initBean();
		
		bean.addSeason();
		
		assertThat(bean.getSelectedSeason().getDescription(), nullValue());
		String description = "Season 4";
		BigDecimal price = BigDecimal.valueOf(56);
		Date startdate = null;
		Date enddate = new Date();
		
		bean.getSelectedSeason().setDescription(description);
		bean.getSelectedSeason().setPrice(price);
		bean.getSelectedSeason().setStartdate(startdate);
		bean.getSelectedSeason().setEnddate(enddate);
		
		bean.saveSeason();
		
		assertThat(bean.getSeasons().size(), equalTo(3));
		assertThat(facadenFactory.getSeasonFacade().count(), equalTo(3));
		assertThat(bean.getSelectedSeason().getDescription(), not(nullValue()));
	}
	@Test
	public void testSaveSeasonNoEndDate() {
		EditSeasonBean bean = initBean();
		
		bean.addSeason();
		
		assertThat(bean.getSelectedSeason().getDescription(), nullValue());
		String description = "Season 4";
		BigDecimal price = BigDecimal.valueOf(56);
		Date startdate = new Date();
		Date enddate = null;
		
		bean.getSelectedSeason().setDescription(description);
		bean.getSelectedSeason().setPrice(price);
		bean.getSelectedSeason().setStartdate(startdate);
		bean.getSelectedSeason().setEnddate(enddate);
		
		bean.saveSeason();
		
		assertThat(bean.getSeasons().size(), equalTo(3));
		assertThat(facadenFactory.getSeasonFacade().count(), equalTo(3));
		assertThat(bean.getSelectedSeason().getDescription(), not(nullValue()));
	}
	@Test
	public void testDeleteSeason() {
		EditSeasonBean bean = initBean();
		
		bean.addSeason();
		
		assertThat(bean.getSelectedSeason().getDescription(), nullValue());
		String description = "Season 4";
		BigDecimal price = BigDecimal.valueOf(56);
		Date startdate = new Date();
		Date enddate = new Date();
		
		bean.getSelectedSeason().setDescription(description);
		bean.getSelectedSeason().setPrice(price);
		bean.getSelectedSeason().setStartdate(startdate);
		bean.getSelectedSeason().setEnddate(enddate);
		
		bean.saveSeason();
		
		assertThat(bean.getSeasons().size(), equalTo(4));
		assertThat(facadenFactory.getSeasonFacade().count(), equalTo(4));
		assertThat(bean.getSelectedSeason().getDescription(), not(nullValue()));
		assertThat(bean.getSelectedSeason().getDescription(), equalTo(description));
		assertThat(bean.getSelectedSeason().getPrice(), equalTo(price));
		assertThat(bean.getSelectedSeason().getStartdate(), equalTo(startdate));
		assertThat(bean.getSelectedSeason().getEnddate(), equalTo(enddate));
		
		bean.delete();
		
		assertThat(bean.getSelectedSeason().getDescription(), not(nullValue()));
		assertThat(bean.getSeasons().size(), equalTo(3));
		assertThat(facadenFactory.getSeasonFacade().count(), equalTo(3));
	}
	
	@Test
	public void testDeleteSeasonError() {
		EditSeasonBean bean = initBean();
		
		bean.delete();
		
		assertThat(bean.getSelectedSeason().getDescription(), not(nullValue()));
		assertThat(bean.getSeasons().size(), equalTo(3));
		assertThat(facadenFactory.getSeasonFacade().count(), equalTo(3));
	}
	
	@Test
	public void testDeleteSeasonNoneSelected() {
		EditSeasonBean bean = initBean();
		
		bean.setSelectedSeason(null);
		
		bean.delete();
		assertThat(bean.getSeasons().size(), equalTo(3));
		assertThat(facadenFactory.getSeasonFacade().count(), equalTo(3));
	}

	
	private EditSeasonBean initBean() {
		ServiceCreator sc = new ServiceCreator(em);
		EditSeasonBean bean = new EditSeasonBean(new UIMessagesTestImpl(), sc.getSeasonService());
		bean.init();
		assertThat(bean.getSeasons().size(), equalTo(3));
		assertThat(bean.getSelectedSeason().getDescription(), equalTo("Saison 1"));
		return bean;
	}
}

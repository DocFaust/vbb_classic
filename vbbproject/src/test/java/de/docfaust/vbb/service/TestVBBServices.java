package de.docfaust.vbb.service;

import de.docfaust.vbb.data.util.JpaBaseRolledBackTestCase;

public class TestVBBServices extends JpaBaseRolledBackTestCase {
//	private static final String HANS_HOHLBIRNE = "Hans Hohlbirne";
//	private static final String GRETCHEN_GROEHL = "Gretchen Gröhl";
//	private static final String FRANZ_FROEHLICH = "Franz Fröhlich";
//	private static final String ERICH_EHRLICH = "Erich Ehrlich";
//	private static final String DOREEN_DURSTIG = "Doreen Durstig";
//	private static final String CLAUS_CASPAR = "Claus Caspar";
//	private static final String BERND_BROT = "Bernd Brot";
//	private static final String ALFRED_ALTMANN = "Alfred Altmann";
//	private static final String INES_IGNORANT = "Ines Ignorant";
//	private static final String JOHANN_JOCHBEIN = "Johann Jochbein";
	// FIXME Write new
////	@Test
////	public void testDeleteSaison()
////	{
////		VBBServices services = new VBBServices(em);
////		assertThat(facadenFactory.getSeasonFacade().count(), equalTo(3));
////		Season season = facadenFactory.getSeasonFacade().find(3);
////		Statusliste statusliste = services.deleteSaison(season);
////		assertTrue(statusliste.booleanValue());
////		assertThat(facadenFactory.getSeasonFacade().count(), equalTo(2));
////	}
//	
	// FIXME Write new
////	@Test
////	public void testSaveSpieler(){
////		VBBServices services = new VBBServices(em);
////		assertThat(facadenFactory.getSpielFacade().count(), equalTo(4));
////		assertThat(facadenFactory.getBuchungFacade().count(), equalTo(31));
////		assertThat(facadenFactory.getSeasonFacade().count(), equalTo(3));
////		assertThat(facadenFactory.getSpielerFacade().count(), equalTo(10));
////		
////		Spieler spieler = EntityFactory.createSpieler(false, "Theodor Test", "ttest@test.de", false);
////		
////		services.saveSpieler(spieler);
////		
////		assertThat(facadenFactory.getSpielFacade().count(), equalTo(4));
////		assertThat(facadenFactory.getBuchungFacade().count(), equalTo(31));
////		assertThat(facadenFactory.getSeasonFacade().count(), equalTo(3));
////		assertThat(facadenFactory.getSpielerFacade().count(), equalTo(11));
////	}
////	
	// FIXME Write new
////	@Test
////	public void testDeleteSpielerNichtMöglich(){
////		VBBServices services = new VBBServices(em);
////		assertThat(facadenFactory.getSpielFacade().count(), equalTo(4));
////		assertThat(facadenFactory.getBuchungFacade().count(), equalTo(31));
////		assertThat(facadenFactory.getSeasonFacade().count(), equalTo(3));
////		assertThat(facadenFactory.getSpielerFacade().count(), equalTo(10));
////		
////		Spieler spieler = facadenFactory.getSpielerFacade().find(1);
////		
////		Statusliste liste = services.deleteSpieler(spieler);
////		
////		assertFalse(liste.booleanValue());
////		assertThat(facadenFactory.getSpielFacade().count(), equalTo(4));
////		assertThat(facadenFactory.getBuchungFacade().count(), equalTo(31));
////		assertThat(facadenFactory.getSeasonFacade().count(), equalTo(3));
////		assertThat(facadenFactory.getSpielerFacade().count(), equalTo(10));
////	}
////	
	// FIXME Write new
////	@Test
////	public void testDeleteSpielerOhneBuchungen(){
////		VBBServices services = new VBBServices(em);
////		assertThat(facadenFactory.getSpielFacade().count(), equalTo(4));
////		assertThat(facadenFactory.getBuchungFacade().count(), equalTo(31));
////		assertThat(facadenFactory.getSeasonFacade().count(), equalTo(3));
////		assertThat(facadenFactory.getSpielerFacade().count(), equalTo(10));
////		
////		Spieler spieler = facadenFactory.getSpielerFacade().find(1);
////		
////		spieler.getBuchungen().forEach(buchung -> facadenFactory.getBuchungFacade().remove(buchung));
////		spieler.getBuchungen().clear();
////		
////		Statusliste liste = services.deleteSpieler(spieler);
////		
////		assertTrue(liste.booleanValue());
////		assertThat(facadenFactory.getSpielFacade().count(), equalTo(4));
////		assertThat(facadenFactory.getBuchungFacade().count(), equalTo(27));
////		assertThat(facadenFactory.getSeasonFacade().count(), equalTo(3));
////		assertThat(facadenFactory.getSpielerFacade().count(), equalTo(9));
////	}
////
//	
	// FIXME Write new
//	@Test
//	public void testDeleteBuchung(){
//		VBBServices services = new VBBServices(em);
//		assertThat(facadenFactory.getSpielFacade().count(), equalTo(4));
//		assertThat(facadenFactory.getBuchungFacade().count(), equalTo(31));
//		assertThat(facadenFactory.getSeasonFacade().count(), equalTo(3));
//		assertThat(facadenFactory.getSpielerFacade().count(), equalTo(10));
//		Buchung buchung = facadenFactory.getBuchungFacade().find(1);
//		
//		services.deleteBuchung(buchung);
//		
//		assertThat(facadenFactory.getSpielFacade().count(), equalTo(4));
//		assertThat(facadenFactory.getBuchungFacade().count(), equalTo(30));
//		assertThat(facadenFactory.getSeasonFacade().count(), equalTo(3));
//		assertThat(facadenFactory.getSpielerFacade().count(), equalTo(10));
//		
//	}
//	
	// FIXME Write new
//	@Test
//	public void testGetGroups(){
//		VBBServices services = new VBBServices(em);
//		assertThat(facadenFactory.getSpielFacade().count(), equalTo(4));
//		assertThat(facadenFactory.getBuchungFacade().count(), equalTo(31));
//		assertThat(facadenFactory.getSeasonFacade().count(), equalTo(3));
//		assertThat(facadenFactory.getSpielerFacade().count(), equalTo(10));
//		assertThat(facadenFactory.getGroupFacade().count(), equalTo(3));
//		List<Group> groups = services.getGroups();
//		
//		assertThat(groups.size(), equalTo(facadenFactory.getGroupFacade().count()));
//	}
//	
}

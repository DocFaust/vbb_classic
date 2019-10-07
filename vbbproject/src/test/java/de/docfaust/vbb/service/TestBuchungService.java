package de.docfaust.vbb.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.docfaust.vbb.ServiceCreator;
import de.docfaust.vbb.data.entity.Buchung;
import de.docfaust.vbb.data.util.JpaBaseRolledBackTestCase;

public class TestBuchungService extends JpaBaseRolledBackTestCase {
	
	private BuchungService services;
	
	@BeforeEach
	public void setUp() {
		services = new ServiceCreator(em).getBuchungService();
	}

	@Test
	public void testDeleteBuchung(){
		int count = facadenFactory.getBuchungFacade().count();
		Buchung buchung = facadenFactory.getBuchungFacade().find(1);
		
		services.deleteBuchung(buchung);
		
		assertThat(facadenFactory.getBuchungFacade().count()).isEqualTo(--count);
	}
	
	@Test
	void testGetBuchungen() {
		assertThat(services.getBuchungen()).isNotNull().hasSize(facadenFactory.getBuchungFacade().count());
	}
	@Test
	void testSaveBuchung() {
		
	}
	@Test
	void testRemoveOldBuchungenBuchung() {
		
	}
}

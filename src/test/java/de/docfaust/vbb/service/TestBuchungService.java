package de.docfaust.vbb.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.docfaust.vbb.ServiceCreator;
import de.docfaust.vbb.data.entity.Buchung;
import de.docfaust.vbb.data.util.JpaBaseRolledBackTestCase;
import de.docfaust.vbb.util.EntityFactory;

public class TestBuchungService extends JpaBaseRolledBackTestCase {
	
	private BuchungService services;
	
	@BeforeEach
	public void setUp() {
		services = new ServiceCreator(em).getBuchungService();
	}

	@Test
	void testInit() {
		assertThat(new BuchungServiceImpl()).isNotNull();
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
		int count = facadenFactory.getBuchungFacade().count();
		Date datum = new Date();
		BigDecimal price = BigDecimal.ONE;
		Buchung buchung = EntityFactory.createBuchung(datum, price);
		services.saveBuchung(buchung);
		assertThat(facadenFactory.getBuchungFacade().count()).isEqualTo(++count);
		assertThat(facadenFactory.getBuchungFacade().find(buchung.getId())).extracting("datum", "price").contains(datum,price);
	}
	
	@Test
	void testRemoveOldBuchungenBuchung() {
		Date datum = new Date();
		services.removeOldBuchungen(datum);
		assertThat(facadenFactory.getBuchungFacade().count()).isEqualTo(0);
	}
	@Test
	void testRemoveOldBuchungenBuchungLeaveOne() {
		Date datum = new Date();
		BigDecimal price = BigDecimal.ONE;
		Buchung buchung = EntityFactory.createBuchung(datum, price);
		services.saveBuchung(buchung);
		services.removeOldBuchungen(datum);
		assertThat(facadenFactory.getBuchungFacade().count()).isEqualTo(1);
	}
}

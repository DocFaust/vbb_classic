package de.docfaust.vbb.validation;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import de.docfaust.vbb.data.entity.Season;
import de.docfaust.vbb.util.messages.MessageConstants;
import de.docfaust.vbb.util.statusliste.Statusliste;

public class TestSeasonValidator {

	@Test
	public void testValidateNull() {
		Statusliste statusliste = ValidationFactory.create().validate(null);
		assertTrue(statusliste.hasStatus(MessageConstants.SEASON_NOT_FOUND));
	}

	@Test
	public void testValidateNoPrice() {
		Season season = new Season();
		Statusliste statusliste = ValidationFactory.create().validate(season);
		assertTrue(statusliste.hasStatus(MessageConstants.SEASON_NO_PRICE));
	}

	@Test
	public void testValidateIsBuchungsschnitt() {
		Season season = new Season();
		season.setDescription("Buchungsschnitt");
		Statusliste statusliste = ValidationFactory.create().validate(season);
		assertTrue(statusliste.hasStatus(MessageConstants.SEASON_IS_BUCHUNGSSCHNITT));
	}
	
	@Test
	public void testValidateNoDescription() {
		Season season = new Season();
		season.setDescription(null);
		season.setPrice(BigDecimal.TEN);
		Statusliste statusliste = ValidationFactory.create().validate(season);
		assertThat(statusliste.booleanValue(), equalTo(Boolean.TRUE));
	}

	@Test
	public void testValidateOk() {
		Season season = new Season();
		season.setPrice(BigDecimal.TEN);
		Statusliste statusliste = ValidationFactory.create().validate(season);
		assertThat(statusliste.booleanValue(), equalTo(Boolean.TRUE));
		
	}
}

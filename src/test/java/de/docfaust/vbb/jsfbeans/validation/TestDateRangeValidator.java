package de.docfaust.vbb.jsfbeans.validation;

import static org.junit.Assert.fail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.faces.component.UIComponent;
import javax.faces.validator.ValidatorException;

import org.junit.jupiter.api.Test;
import org.primefaces.component.celleditor.CellEditor;

public class TestDateRangeValidator {
	@Test
	public void testDateRangeOk() throws ParseException {
		DateRangeValidator val = new DateRangeValidator();
		UIComponent component = new CellEditor();

		Date startDate = new SimpleDateFormat("dd.MM.yyyy").parse("01.01.1900");
		Date endDate = new SimpleDateFormat("dd.MM.yyyy").parse("01.01.1901");

		component.getAttributes().put("startDate", startDate);

		try {
			val.validate(null, component, endDate);
		} catch (ValidatorException e) {
			fail("Hier sollte eigentlich keine Exception fliegen");
		}
	}

	@Test
	public void testDateRangeEndBeforeStart() throws ParseException {
		DateRangeValidator val = new DateRangeValidator();
		UIComponent component = new CellEditor();

		Date startDate = new SimpleDateFormat("dd.MM.yyyy").parse("01.01.1901");
		Date endDate = new SimpleDateFormat("dd.MM.yyyy").parse("01.01.1900");

		component.getAttributes().put("startDate", startDate);
		try {
			val.validate(null, component, endDate);
			fail("Hier sollte eigentlich keine Exception fliegen");
		} catch (ValidatorException e) {
			// Alles Gut
		}
	}


	@Test
	public void testDateRangeEndNull() throws ParseException {
		DateRangeValidator val = new DateRangeValidator();
		UIComponent component = new CellEditor();

		Date startDate = new SimpleDateFormat("dd.MM.yyyy").parse("01.01.1900");
		Date endDate = null;

		component.getAttributes().put("startDate", startDate);

		try {
			val.validate(null, component, endDate);
		} catch (ValidatorException e) {
			fail("Hier sollte eigentlich keine Exception fliegen");
		}
	}
}

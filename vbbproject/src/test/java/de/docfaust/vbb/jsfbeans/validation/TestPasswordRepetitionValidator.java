package de.docfaust.vbb.jsfbeans.validation;

import static org.junit.Assert.*;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import org.junit.jupiter.api.Test;
import org.primefaces.component.celleditor.CellEditor;

public class TestPasswordRepetitionValidator {

	@Test
	public void testOk() {
		PasswordRepetitionValidator val = new PasswordRepetitionValidator();
		FacesContext context = FacesContext.getCurrentInstance();
		String password = "12345678";
		String passwordRepetition = "12345678";
		UIComponent component = new CellEditor();
		component.getAttributes().put("confirm", password);
		try {
			val.validate(context, component, passwordRepetition);
		} catch (ValidatorException e) {
			fail("Sollte Ok sein");
		}
	}

	@Test
	public void testNOk() {
		PasswordRepetitionValidator val = new PasswordRepetitionValidator();
		FacesContext context = FacesContext.getCurrentInstance();
		String password = "123456789";
		String passwordRepetition = "12345678";
		UIComponent component = new CellEditor();
		component.getAttributes().put("confirm", password);
		try {
			val.validate(context, component, passwordRepetition);
			fail("Sollte NOk sein");
		} catch (ValidatorException e) {
		}
	}

	@Test
	public void testRepetitionNull() {
		PasswordRepetitionValidator val = new PasswordRepetitionValidator();
		FacesContext context = FacesContext.getCurrentInstance();
		String password = "12345678";
		String passwordRepetition = null;
		UIComponent component = new CellEditor();
		component.getAttributes().put("confirm", password);
		try {
			val.validate(context, component, passwordRepetition);
		} catch (ValidatorException e) {
			fail("Sollte Ok sein");
		}
	}
}

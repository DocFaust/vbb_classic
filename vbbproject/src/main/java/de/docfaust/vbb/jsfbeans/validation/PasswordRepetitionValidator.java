package de.docfaust.vbb.jsfbeans.validation;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.slf4j.LoggerFactory;

/**
 * Validator für die Passwortwiederholung. Hier wird überprüft, ob die
 * Passwortwiederholung gleich ist, mit dem ursprünglichen Passwort
 * 
 * @author xhu1011
 *
 */
@FacesValidator("passwordRepetitionValidator")
public class PasswordRepetitionValidator implements Validator {
	@Override
	public void validate(final FacesContext context, final UIComponent component, final Object value) {
		String password = (String) value;
		String confirm = (String) component.getAttributes().get("confirm");

		if (password == null || confirm == null) {
			return; // Just ignore and let required="true" do its job.
		}
		LoggerFactory.getLogger(getClass()).info("IM here");
		if (!password.equals(confirm)) {
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ungültiges Passwort", "Passwörter passen nicht zusammen"));
		}
	}
}
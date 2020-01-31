package de.docfaust.vbb.jsfbeans.validation;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 * Validator für das Passwort.
 * 
 * @author xhu1011
 *
 */
@FacesValidator("passwordValidator")
public class PasswordValidator implements Validator<String> {

	/**
	 * Minimale Passwort Länge.
	 */
	private static final int MIN_PASSWORD_LENGTH = 8;

	@Override
	public void validate(final FacesContext context, final UIComponent component, final String password) {
		if (password == null) {
			return; // Let required="true" handle.
		}


		if (password.length() < MIN_PASSWORD_LENGTH) {
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ungültiges Passwort", "Passwort muss länger als acht Stellen sein!"));
		}
	}
}
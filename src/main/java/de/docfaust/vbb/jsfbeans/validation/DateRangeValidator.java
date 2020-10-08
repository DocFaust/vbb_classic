package de.docfaust.vbb.jsfbeans.validation;

import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 * Validator für das Datum.
 * 
 * Es wird überprüft, ob das Anfangsdatum vor dem Endedatum liegt.
 * 
 * @author xhu1011
 *
 */
@FacesValidator("dateRangeValidator")
public class DateRangeValidator implements Validator<Date> {

	@Override
	public void validate(final FacesContext context, final UIComponent component, final Date value) {
		if (value == null) {
			return; // Let required="true" handle.
		}

		Object startDateObject = component.getAttributes().get("startDate");
		if (startDateObject == null) {
			return; // Let required="true" handle.
		}

		System.out.println(startDateObject.toString());
		Date startDate = (Date) startDateObject;

		if (startDate.after(value)) {
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Datum falsch", "Endedatum darf nicht vor dem Anfangsdatum sein"));
		}
	}
}
package de.docfaust.vbb.validation;

import de.docfaust.vbb.data.entity.IEntity;
import de.docfaust.vbb.util.statusliste.Statusliste;

/**
 * Validatoreninterface. Muss von Entityvalidatoren implementiert werden.
 * 
 * @author xhu1011
 *
 * @param <T>
 *            zu validierende Entität
 */
public interface Validator<T extends IEntity> {
	/**
	 * Validiert eine Entität.
	 * 
	 * @param validatedClass
	 *            zu validierende Klasse
	 * @return Statusliste
	 */
	Statusliste validate(T validatedClass);
}

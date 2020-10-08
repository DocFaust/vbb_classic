package de.docfaust.vbb.jsfbeans.convert;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.docfaust.vbb.data.entity.Group;
import de.docfaust.vbb.data.facades.GroupFacade;

/**
 * Converter für die Gruppen. Wandelt Objekte in IDs und umgekehrt.
 * 
 * @author xhu1011
 *
 */
//@FacesConverter("groupConverter")
@Named
@RequestScoped
public class GroupConverter implements Converter<Group> {

	@EJB
	private GroupFacade groupFacade;

	/**
	 * Konstruktor für Aufruf mit EJB Context.
	 */
	public GroupConverter() {
		super();
	}
	/**
	 * Konstruktor für Aufruf ohne EJB Context.
	 * @param groupFacade GrupperFacade
	 */
	public GroupConverter(final GroupFacade groupFacade) {
		super();
		this.groupFacade = groupFacade;
	}

	private Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * Macht aus der ID ein Objekt.
	 * 
	 * @param fc
	 *            Faces Context.
	 * @param uic
	 *            Komponente
	 * @param value
	 *            ID als String
	 * @return Group objekt
	 */
	@Override
	public Group getAsObject(final FacesContext fc, final UIComponent uic, final String value) {
		if (value != null && value.trim().length() > 0) {
			try {
				Group group = groupFacade.find(Integer.parseInt(value));
				logger.debug("Konvertiere ID: " + value + " in Gruppe: " + group.getDescription());
				return group;
			} catch (NumberFormatException e) {
				throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid group."));
			}
		} else {
			return null;
		}
	}

	/**
	 * Gibt die ID als String zurück.
	 * 
	 * @param fc
	 *            Faces Context.
	 * @param uic
	 *            Komponente
	 * @param group
	 *            Gruppenobjekt
	 * @return ID als String
	 */
	public String getAsString(final FacesContext fc, final UIComponent uic, final Group group) {
		if (group != null) {
			String id = String.valueOf(group.getId());
			logger.debug("Konvertiere Gruppe: " + group.getDescription() + " in ID: " + id);
			return id;
		} else {
			return null;
		}
	}
}
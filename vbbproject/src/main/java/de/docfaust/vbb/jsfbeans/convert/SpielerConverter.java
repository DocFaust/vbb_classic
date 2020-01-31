package de.docfaust.vbb.jsfbeans.convert;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.docfaust.vbb.data.entity.Spieler;
import de.docfaust.vbb.data.facades.SpielerFacade;

/**
 * Converter für die Spieler. Wandelt Objekte in IDs und umgekehrt.
 * 
 * @author xhu1011
 *
 */
@Named
public class SpielerConverter implements Converter<Spieler> {

	@EJB
	private SpielerFacade spielerFacade;
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * Konstruktor für Aufruf ohne EJB Context.
	 * @param spielerFacade SpielerFacade
	 */
	public SpielerConverter(final SpielerFacade spielerFacade) {
		super();
		this.spielerFacade = spielerFacade;
	}

	/**
	 * Konstruktor mit EJB Kontext.
	 * 
	 */
	public SpielerConverter() {
		super();
	}
	/**
	 * Macht aus der ID ein Objekt.
	 * 
	 * @param fc
	 *            Faces Context.
	 * @param uic
	 *            Komponente
	 * @param value
	 *            ID als String
	 * @return Spieler objekt
	 */
	@Override
	public Spieler getAsObject(final FacesContext fc, final UIComponent uic, final String value) {
		if (value != null && value.trim().length() > 0) {
			try {
				Spieler spieler = spielerFacade.find(Integer.parseInt(value));
				logger.info("Conversion To Object: " + spieler.getName());
				return spieler;
			} catch (NumberFormatException e) {
				throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid Spieler."));
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
	 * @param object
	 *            Spielerobjekt
	 * @return ID als String
	 */
	public String getAsString(final FacesContext fc, final UIComponent uic, final Spieler object) {
		if (object != null && object instanceof Spieler) {
			Spieler spieler = (Spieler) object;
			String id = String.valueOf(spieler.getId());
			logger.info("Conversion To ID: " + id);
			return id;
		} else {
			return null;
		}
	}
}
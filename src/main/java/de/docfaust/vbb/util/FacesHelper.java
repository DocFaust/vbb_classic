package de.docfaust.vbb.util;

import javax.faces.context.FacesContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Helper class for JSFfunctions.
 * @author wfa339
 *
 */
public final class FacesHelper {
	private static Logger logger = LoggerFactory.getLogger(FacesHelper.class);
	private FacesHelper() {
		
	}
	
	/**
	 * Retrieves the User ID from Facescontext.
	 * @return UserID or null if there is no facescontext
	 */
	public static String getCurrentUserID() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		if (facesContext == null) {
			logger.warn("Kein FacesContext verfügbar, Der angemeldete User kann nicht abgerufen werden.");
		} else {
			String userid = facesContext.getExternalContext().getUserPrincipal().getName();
			logger.info("Angemeldete UserID: " + userid);
			return userid;
		}
		return null;
	}


}

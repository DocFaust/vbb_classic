package de.docfaust.vbb.rest;

import java.util.Collection;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import de.docfaust.vbb.rest.model.RestSpiel;
import de.docfaust.vbb.rest.model.RestSpieler;

/**
 * Rest Endpoints.
 * @author wfa339
 *
 */
@Path("/library")
@Consumes({ "application/json" })
@Produces({ "application/json" })
public interface VBBRestInterface {
	
	/**
	 * GetSpieler.
	 * @return List of Spieler
	 */
	@GET
	@Path("/getspieler")
	@Produces("application/json")
	Collection<RestSpieler> getSpieler();

	/**
	 * GetSaldo.
	 * @return Salden
	 */
	@GET
	@Path("/getsaldo")
	@Produces("application/json")
	Collection<RestSpieler> getSaldo();

	/**
	 * Create Spiel.
	 * @param spiel Spiel
	 * @return Response Status
	 */
	@PUT
	@Path("/createspiel")
	Response createSpiel(RestSpiel spiel);
}
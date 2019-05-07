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

@Path("/library")
@Consumes({ "application/json" })
@Produces({ "application/json" })
public interface VBBRestInterface {
	
	@GET
	@Path("/getspieler")
	@Produces("application/json")
	Collection<RestSpieler> getSpieler();

	@GET
	@Path("/getsaldo")
	@Produces("application/json")
	Collection<RestSpieler> getSaldo();

	@PUT
	@Path("/createspiel")
	Response createSpiel(RestSpiel spiel);
}
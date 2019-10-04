package de.docfaust.vbb.rest;

import java.util.Collection;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

import de.docfaust.vbb.rest.model.RestSpiel;
import de.docfaust.vbb.rest.model.RestSpieler;
import de.docfaust.vbb.service.SpielerService;

/**
 * Rest Implementation.
 * @author wfa339
 *
 */
public class VBBRestImpl implements VBBRestInterface {
	@Inject
	private SpielerService spielerService;
	
	@Override
	public Collection<RestSpieler> getSpieler() {
		return spielerService.getSpieler().stream().map(p -> new RestSpieler(p.getName())).collect(Collectors.toList());
	}

	@Override
	public Collection<RestSpieler> getSaldo() {
		return null;
	}

	@Override
	public Response createSpiel(final RestSpiel spiel) {
		return null;
	}
	
}
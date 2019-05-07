package de.docfaust.vbb.rest;

import java.util.Collection;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

import de.docfaust.vbb.rest.model.RestSpiel;
import de.docfaust.vbb.rest.model.RestSpieler;
import de.docfaust.vbb.service.VBBServices;

public class VBBRestImpl implements VBBRestInterface {
 
	@Inject
	private VBBServices services;

	@Override
	public Collection<RestSpieler> getSpieler() {
		return services.getSpieler().stream().map(p -> new RestSpieler(p.getName())).collect(Collectors.toList());
	}

	@Override
	public Collection<RestSpieler> getSaldo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response createSpiel(RestSpiel spiel) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
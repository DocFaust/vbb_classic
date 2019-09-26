package de.docfaust.vbb.service;

import java.util.List;
import java.util.Optional;

import javax.ejb.EJB;
import javax.inject.Inject;

import org.slf4j.Logger;

import de.docfaust.vbb.data.entity.Token;
import de.docfaust.vbb.data.entity.ValidityState;
import de.docfaust.vbb.data.facades.TokenFacade;

public class TokenServiceImpl {
	/**
	 * DB-Zugriff für Tokens.
	 */
	@EJB
	private TokenFacade tokenFacade;
	/**
	 * Logger.
	 */
	@Inject
	private Logger logger;
	
	public boolean validateToken(String token) {
		Optional<Token> o = tokenFacade.findByToken(token);
		return o.isPresent() && o.get().getState() == ValidityState.VALID; 
	}
	
	public List<Token> getTokens() {
		return tokenFacade.findAll();
	}
}

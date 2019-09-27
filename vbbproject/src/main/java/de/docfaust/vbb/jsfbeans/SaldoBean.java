package de.docfaust.vbb.jsfbeans;

import java.io.Serializable;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;

import de.docfaust.vbb.model.SaldoModel;
import de.docfaust.vbb.service.SaldoService;
import de.docfaust.vbb.service.TokenService;
import lombok.Getter;
import lombok.Setter;

@ViewScoped
@Named
public class SaldoBean implements Serializable {

	private static final String PARAM_TOKEN = "token";

	@Inject
	private SaldoService saldoService;

	@Inject
	private TokenService tokenService;

	@Inject
	private Logger logger;

	@Getter
	@Setter
	private SaldoModel saldoModel;
	
	@Getter
	@Setter
	private boolean tokenValid;

	@PostConstruct
	public void init() {
		saldoModel = saldoService.getSaldo();
		logger.debug("Saldomodel: {}", saldoModel.toString());

		String token = getToken();
		tokenValid = tokenService.validateToken(token);
		logger.info("isTokenValid: ", String.valueOf(tokenValid));
	}

	private String getToken() {
		FacesContext fc = FacesContext.getCurrentInstance();
		Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
		String tokenParam = params.get(PARAM_TOKEN);
		logger.debug("Token: {}", tokenParam);
		return tokenParam;
	}
}

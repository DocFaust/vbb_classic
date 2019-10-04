package de.docfaust.vbb.jsfbeans;

import java.io.Serializable;
import java.util.Map;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.docfaust.vbb.model.SaldoModel;
import de.docfaust.vbb.service.SaldoService;
import de.docfaust.vbb.service.TokenService;
import lombok.Getter;
import lombok.Setter;

/**
 * Bean for the Saldo Page without login.
 * 
 * @author wfa339
 *
 */
@ViewScoped
@Named
public class SaldoBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2717757069911070223L;

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

	/**
	 * @param saldoService for JUnit
	 * @param tokenService for JUnit
	 */
	public SaldoBean(final SaldoService saldoService, final TokenService tokenService) {
		super();
		this.saldoService = saldoService;
		this.tokenService = tokenService;
		logger = LoggerFactory.getLogger(getClass());
	}

	/**
	 * EJB Usage.
	 */
	public SaldoBean() {
		super();
	}

	/**
	 * Initializes the bean.
	 */
	@PostConstruct
	public void init() {
		saldoModel = saldoService.getSaldo();
		logger.debug("Saldomodel: {}", saldoModel.toString());

		Optional<String> token = getToken();
		if (token.isPresent()) {
			tokenValid = tokenService.validateToken(token.get());
		} else {
			tokenValid = true;
			// FIXME Do better
			logger.warn("Token granted, due to no facescontext");
		}

		logger.info("isTokenValid: ", String.valueOf(tokenValid));
	}

	private Optional<String> getToken() {
		FacesContext fc = FacesContext.getCurrentInstance();
		if (fc != null) {
			Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
			String tokenParam = params.get(PARAM_TOKEN);
			logger.debug("Token: {}", tokenParam);
			return Optional.of(tokenParam);
		} else {
			return Optional.empty();
		}
	}
}

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
	private boolean showSaldo;

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
		checkShowSaldo();
	}

	private void checkShowSaldo() {
		// check Facescontext or Unit
		FacesContext fc = FacesContext.getCurrentInstance();
		if (fc != null) {
			// FacesContext
			if (fc.getExternalContext().getUserPrincipal() != null) {
				// User islogged in
				logger.info("Saldo User is logged in");
				showSaldo = true;
			} else {
				//Saldo without User
				Optional<String> token = getToken();
				if (token.isPresent()) {
					showSaldo = tokenService.validateToken(token.get());
				} else {
					showSaldo = false;
				}
				logger.info("Saldo with Token ");
			}
		} else {
			logger.info("Saldo JUnit Context");
			// JUnitContext
			showSaldo = false;
		}
		logger.info("Saldo shown: {}", String.valueOf(showSaldo));
	}

	private Optional<String> getToken() {
		FacesContext fc = FacesContext.getCurrentInstance();
		if (fc != null) {
			Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
			String tokenParam = params.get(PARAM_TOKEN);
			logger.debug("Token: {}", tokenParam);
			if (tokenParam != null) {
				return Optional.of(tokenParam);
			}
		}
		return Optional.empty();
	}
}

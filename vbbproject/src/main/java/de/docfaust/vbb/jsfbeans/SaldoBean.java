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
import lombok.Getter;
import lombok.Setter;

@ViewScoped
@Named
public class SaldoBean implements Serializable {

	@Inject
	private SaldoService saldoService;

	@Inject
	private Logger logger;

	@Getter
	@Setter
	private SaldoModel saldoModel;
	@Getter
	@Setter
	private boolean key;

	@PostConstruct
	public void init() {
		saldoModel = saldoService.getSaldo();
		logger.debug("Saldomodel: {}", saldoModel.toString());

		FacesContext fc = FacesContext.getCurrentInstance();
		Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
		String keyParam = params.get("key");
		logger.info("!" + keyParam + "!");
		key = keyParam.equals("1");
		logger.info(String.valueOf(key));
	}
}

package de.docfaust.vbb.jsfbeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.extensions.event.ClipboardErrorEvent;
import org.primefaces.extensions.event.ClipboardSuccessEvent;
import org.slf4j.Logger;

import de.docfaust.vbb.data.entity.Token;
import de.docfaust.vbb.service.TokenService;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@ViewScoped
@Named
public class TokenBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5242230780176843842L;

	@Inject
	private TokenService tokenService;

	@Inject
	private Logger logger;

	@Getter
	@Setter
	private List<Token> tokenList = new ArrayList<>();

	@Getter
	@Setter
	private Token selectedToken;

	@Getter
	@Setter
	private String name;

	@Getter
	@Setter
	private String clipboardURL;

	@PostConstruct
	public void init() {
		tokenList = tokenService.getTokens();
		if (tokenList.size() > 0) {
			selectedToken = tokenList.get(0);
		}
	}

	public void generateClipBoardURL() {
		if (selectedToken != null) {
			clipboardURL = tokenService.generateTokenURL(selectedToken.getToken());
		} else {
			clipboardURL = null;
		}
	}

	public void createToken() {
		selectedToken = tokenService.createToken(name);
		init();

	}

	public void invalidateToken() {
		tokenService.invalidateToken(selectedToken);
		init();
	}

	public void deleteToken() {
		tokenService.deleteToken(selectedToken);
		init();
	}

}

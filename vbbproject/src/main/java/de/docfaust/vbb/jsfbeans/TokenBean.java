package de.docfaust.vbb.jsfbeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;

import de.docfaust.vbb.data.entity.Token;
import de.docfaust.vbb.service.TokenService;
import lombok.Getter;
import lombok.Setter;

/**
 * JSF Bean for Token administration.
 * @author wfa339
 *
 */
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

	/**
	 * Initializes the Bean.
	 */
	@PostConstruct
	public void init() {
		tokenList = tokenService.getTokens();
		if (tokenList.size() > 0) {
			selectedToken = tokenList.get(0);
		}
	}

	/**
	 * generates the Saldo URL for the clipboard.
	 */
	public void generateClipBoardURL() {
		if (selectedToken != null) {
			clipboardURL = tokenService.generateTokenURL(selectedToken.getToken());
		} else {
			clipboardURL = null;
		}
		logger.info("URL generated: {}", clipboardURL);
	}

	/**
	 * Creates a new Token.
	 */
	public void createToken() {
		selectedToken = tokenService.createToken(name);
		init();
		logger.info("Token created: {}", selectedToken.toString());
	}

	/**
	 * Sets the state of the selected Token to invalid.
	 */
	public void invalidateToken() {
		tokenService.invalidateToken(selectedToken);
		init();
		logger.info("Token invalidated: {}", selectedToken.toString());
	}

	/**
	 * Deletes a Token.
	 */
	public void deleteToken() {
		tokenService.deleteToken(selectedToken);
		init();
		logger.info("Token deleted: {}", selectedToken.toString());
	}
}

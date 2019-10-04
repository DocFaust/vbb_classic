package de.docfaust.vbb.service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import javax.ejb.EJB;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.docfaust.vbb.data.entity.Token;
import de.docfaust.vbb.data.entity.ValidityState;
import de.docfaust.vbb.data.facades.TokenFacade;

/**
 * Implementation of the Token Service.
 * @author wfa339
 *
 */
@Dependent
public class TokenServiceImpl implements TokenService {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6119422102311375556L;
	private static final String DOMAIN = "domain";
	private static final String SALDO_PATH = "/faces/saldo.xhtml?token=";
	private static final String CHARMAP = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
	private static final short TOKEN_SIZE = 12;

	/**
	 * DB-Zugriff für Tokens.
	 */
	@EJB
	private TokenFacade tokenFacade;
	/**
	 * DB-Zugriff für Tokens.
	 */
	@EJB
	private ConfigService configService;

	/**
	 * Logger.
	 */
	@Inject
	private Logger logger;

	/**
	 * Constructor w/o parameters for CDI usage.
	 */
	public TokenServiceImpl() {
		
	}
	
	/**
	 * Constructor with a given Tokenfacade for JUnit usage.
	 * @param tf Token facade
	 * @param configService configService
	 */
	public TokenServiceImpl(final TokenFacade tf, final ConfigService configService) {
		this.tokenFacade = tf; 
		this.configService = configService;
		logger = LoggerFactory.getLogger(getClass());

	}
	
	@Override
	public boolean validateToken(final String token) {
		Optional<Token> o = tokenFacade.findByToken(token);
		boolean valid = o.isPresent() && o.get().getState() == ValidityState.VALID;
		logger.info("Token {} is valid: {}", token, valid);
		return valid;
	}

	@Override
	public List<Token> getTokens() {
		return tokenFacade.findAll();
	}

	@Override
	public void invalidateToken(final Token selectedToken) {
		selectedToken.setState(ValidityState.INVALID);
		tokenFacade.edit(selectedToken);
	}

	@Override
	public void deleteToken(final Token selectedToken) {
		tokenFacade.remove(selectedToken);
	}

	@Override
	public Token createToken(final String name) {
		Token token = Token.of(name, generateToken(), ValidityState.VALID);
		tokenFacade.create(token);
		return token;
	}

	private String generateToken() {
		Random random = new Random();
		StringBuffer tokenBuf = new StringBuffer();
		for (int i = 0; i < TOKEN_SIZE; i++) {
			tokenBuf.append(CHARMAP.charAt(random.nextInt(CHARMAP.length())));
		}
		return tokenBuf.toString();
	}
	
	@Override
	public String generateTokenURL(final String token) {
		String domain = configService.getMailConfig().getDomain();
		StringBuffer buf = new StringBuffer();
		buf.append(domain);
		buf.append(SALDO_PATH).append(token);
		String url = buf.toString();
		return url;
	}
}

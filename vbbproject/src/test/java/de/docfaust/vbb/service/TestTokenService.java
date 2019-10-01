package de.docfaust.vbb.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;

import de.docfaust.vbb.data.entity.Token;
import de.docfaust.vbb.data.entity.ValidityState;
import de.docfaust.vbb.data.facades.ConfigFacade;
import de.docfaust.vbb.data.facades.TokenFacade;
import de.docfaust.vbb.data.util.JpaBaseRolledBackTestCase;

class TestTokenService extends JpaBaseRolledBackTestCase {

	@Test
	void testCreateTokenService() {
		TokenService ts = new TokenServiceImpl();
		assertThat(ts).isNotNull();
	}
	
	@Test
	void testCreateToken() {
		TokenFacade tf = new TokenFacade(em);
		ConfigFacade cf = new ConfigFacade(em);
		TokenService ts = new TokenServiceImpl(tf, cf);
		boolean aFound = false;
		boolean nineFound = false;
		for (int i = 0; i < 100; i++) {
			Token createToken = ts.createToken("test");
			String token = createToken.getToken();
			if (token.contains("A")) {
				aFound = true;
			}
			if (token.contains("9")) {
				nineFound = true;
			}
			logger.info(token);
		}
		assertThat(ts.getTokens()).hasSize(102);
		assertThat(aFound).isTrue();
		assertThat(nineFound).isTrue();
	}
	
	@Test
	void testInvalidateToken() {
		TokenFacade tf = new TokenFacade(em);
		ConfigFacade cf = new ConfigFacade(em);
		TokenService ts = new TokenServiceImpl(tf, cf);
		List<Token> tokens = ts.getTokens();
		assertThat(tokens).isNotNull();
		assertThat(tokens).isNotEmpty();
		Token token = tokens.get(0);
		assertThat(token.getState()).isEqualTo(ValidityState.VALID);
		ts.invalidateToken(token);
		assertThat(token.getState()).isEqualTo(ValidityState.INVALID);
		assertThat(tf.find(token.getId()).getState()).isEqualTo(ValidityState.INVALID);
	}
	
	@Test
	void testValidateValidToken() {
		TokenFacade tf = new TokenFacade(em);
		ConfigFacade cf = new ConfigFacade(em);
		TokenService ts = new TokenServiceImpl(tf, cf);
		List<Token> tokens = ts.getTokens();
		assertThat(tokens).isNotNull();
		assertThat(tokens).isNotEmpty();
		Token token = tokens.get(0);
		assertThat(token.getState()).isEqualTo(ValidityState.VALID);
		assertThat(ts.validateToken(token.getToken())).isTrue();
	}
	
	@Test
	void testValidateInvalidToken() {
		TokenFacade tf = new TokenFacade(em);
		ConfigFacade cf = new ConfigFacade(em);
		TokenService ts = new TokenServiceImpl(tf, cf);
		List<Token> tokens = ts.getTokens();
		assertThat(tokens).isNotNull();
		assertThat(tokens).isNotEmpty();
		assertThat(tokens).hasSize(2);
		Token token = tokens.get(1);
		assertThat(token.getState()).isEqualTo(ValidityState.INVALID);
		assertThat(ts.validateToken(token.getToken())).isFalse();
	}
	
	@Test
	void testDeleteToken() {
		TokenFacade tf = new TokenFacade(em);
		ConfigFacade cf = new ConfigFacade(em);
		TokenService ts = new TokenServiceImpl(tf, cf);
		List<Token> tokens = ts.getTokens();
		assertThat(tokens).isNotNull();
		assertThat(tokens).isNotEmpty();
		assertThat(tokens).hasSize(2);
		Token token = tokens.get(0);
		ts.deleteToken(token);
		assertThat(tf.count()).isEqualTo(1);
	}
	
	@Test
	void testGenerateTokenURL() {
		TokenFacade tf = new TokenFacade(em);
		ConfigFacade cf = new ConfigFacade(em);
		TokenService ts = new TokenServiceImpl(tf, cf);
		String url = ts.generateTokenURL("1");
		assertThat(url).isEqualTo("http://localhost:8080/vbb/faces/saldo.xhtml?token=1");		
	}
}

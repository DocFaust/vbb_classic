package de.docfaust.vbb.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import de.docfaust.vbb.data.entity.Token;
import de.docfaust.vbb.data.facades.TokenFacade;
import de.docfaust.vbb.data.util.JpaBaseRolledBackTestCase;

class TestTokenService extends JpaBaseRolledBackTestCase {

	@Test
	void testCreateToken() {
		TokenService ts = new TokenServiceImpl(new TokenFacade(em));
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
			System.out.println(token);
		}
		assertThat(ts.getTokens()).hasSize(101);
		assertThat(aFound).isTrue();
		assertThat(nineFound).isTrue();
	}

}

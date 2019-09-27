package de.docfaust.vbb.service;

import java.io.Serializable;
import java.util.List;

import de.docfaust.vbb.data.entity.Token;

public interface TokenService extends Serializable {

	boolean validateToken(String token);

	List<Token> getTokens();

	void invalidateToken(Token selectedToken);

	void deleteToken(Token selectedToken);

	Token createToken(String string);

	String generateTokenURL(String token);

}
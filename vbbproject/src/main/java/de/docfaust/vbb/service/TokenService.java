package de.docfaust.vbb.service;

import java.io.Serializable;
import java.util.List;

import de.docfaust.vbb.data.entity.Token;

/**
 * Service for the Token handling.
 * @author wfa339
 *
 */
public interface TokenService extends Serializable {

	/**
	 * Validates a Token.
	 * @param token Token to check.
	 * @return true, if token is available and valid.
	 */
	boolean validateToken(String token);

	/**
	 * Gets a List of all Tokens available.
	 * @return List of tokens
	 */
	List<Token> getTokens();

	/**
	 * Sets the state of a given token to invalid.
	 * @param selectedToken token to invalidate
	 */
	void invalidateToken(Token selectedToken);

	/**
	 * deletes a token.
	 * @param selectedToken token to delete
	 */
	void deleteToken(Token selectedToken);

	/**
	 * creates a token with the given name.
	 * @param name Name of the token.
	 * @return created Token Object
	 */
	Token createToken(String name);

	/**
	 * Generates the URL of a Token.
	 * @param token Token String
	 * @return URL
	 */
	String generateTokenURL(String token);

}
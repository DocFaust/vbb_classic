package de.docfaust.vbb.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

/**
 * Filter für die Sicherstellung, dass die Requests auch UTF-8 kodiert sind.
 * 
 * @author xhu1011
 *
 */
@WebFilter("/*")
public class CharacterEncodingFilter implements Filter {

	/**
	 * Filtermethode. Setzt das Encoding auf UTF-8
	 * 
	 * @param request
	 *            Requestobjekt.
	 * @param response
	 *            Responseobjekt
	 * @param chain
	 *            FilterChain
	 * @throws ServletException
	 *             ServletException
	 * @throws IOException
	 *             IOException
	 */
	@Override
	public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		chain.doFilter(request, response);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void destroy() {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void init(final FilterConfig filterConfig) throws ServletException {
	}

}
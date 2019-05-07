package de.docfaust.vbb.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.handinteractive.mobile.UAgentInfo;

/**
 * Filter für die Sicherstellung, dass die Requests auch UTF-8 kodiert sind.
 * 
 * @author xhu1011
 *
 */
@WebFilter("/*")
public class BrowserDetectionFilter implements Filter {

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
		String userAgent = ((HttpServletRequest) request).getHeader("user-agent");
		String accept = ((HttpServletRequest) request).getHeader("Accept");
		if (userAgent != null && accept != null) {
			UAgentInfo agent = new UAgentInfo(userAgent, accept);
			if (agent.isIphone || agent.isAndroidPhone || agent.isTierTablet || agent.isTierIphone || agent.isTierRichCss || agent.isTierGenericMobile) {
				if (!((HttpServletRequest) request).getRequestURI().contains("m/")) {
					((HttpServletResponse) response).sendRedirect("m/index.xhtml");
					return;
				}
			}
		}
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
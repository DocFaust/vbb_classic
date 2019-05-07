package de.docfaust.vbb.filter;

import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.Test;

public class TestBrowserDetectionFilter {

	@Test
	public void testDoFilterMobile() throws IOException, ServletException {
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpServletResponse response = mock(HttpServletResponse.class);
		FilterChain chain = mock(FilterChain.class);

		when(request.getHeader("user-agent")).thenReturn("mobile");
		when(request.getHeader("Accept")).thenReturn("k.a.");
		when(request.getRequestURI()).thenReturn("irgendwas");
		BrowserDetectionFilter f = new BrowserDetectionFilter();

		f.doFilter(request, response, chain);

		verify(response, atLeast(1)).sendRedirect("m/index.xhtml");
		verify(request, atLeast(1)).getHeader("user-agent");
		verify(request, atLeast(1)).getHeader("Accept");
		// verify(chain, atLeast(1)).doFilter(request, response);

	}

	@Test
	public void testDoFilterMobileWantsMobile() throws IOException, ServletException {
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpServletResponse response = mock(HttpServletResponse.class);
		FilterChain chain = mock(FilterChain.class);

		when(request.getHeader("user-agent")).thenReturn("mobile");
		when(request.getHeader("Accept")).thenReturn("k.a.");
		when(request.getRequestURI()).thenReturn("m/");
		BrowserDetectionFilter f = new BrowserDetectionFilter();

		f.doFilter(request, response, chain);

		// verify(response, atLeast(1)).sendRedirect("m/index.xhtml");
		verify(request, atLeast(1)).getHeader("user-agent");
		verify(request, atLeast(1)).getHeader("Accept");
		verify(chain, atLeast(1)).doFilter(request, response);

	}
	@Test
	public void testDoFilterAgentNull() throws IOException, ServletException {
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpServletResponse response = mock(HttpServletResponse.class);
		FilterChain chain = mock(FilterChain.class);

		when(request.getHeader("user-agent")).thenReturn(null);
		when(request.getHeader("Accept")).thenReturn("k.a.");
		when(request.getRequestURI()).thenReturn("m/");
		BrowserDetectionFilter f = new BrowserDetectionFilter();

		f.doFilter(request, response, chain);

		// verify(response, atLeast(1)).sendRedirect("m/index.xhtml");
		verify(request, atLeast(1)).getHeader("user-agent");
		verify(request, atLeast(1)).getHeader("Accept");
		verify(chain, atLeast(1)).doFilter(request, response);

	}
	@Test
	public void testDoFilterAcceptNull() throws IOException, ServletException {
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpServletResponse response = mock(HttpServletResponse.class);
		FilterChain chain = mock(FilterChain.class);

		when(request.getHeader("user-agent")).thenReturn("mobile");
		when(request.getHeader("Accept")).thenReturn(null);
		when(request.getRequestURI()).thenReturn("m/");
		BrowserDetectionFilter f = new BrowserDetectionFilter();

		f.doFilter(request, response, chain);

		// verify(response, atLeast(1)).sendRedirect("m/index.xhtml");
		verify(request, atLeast(1)).getHeader("user-agent");
		verify(request, atLeast(1)).getHeader("Accept");
		verify(chain, atLeast(1)).doFilter(request, response);

	}
	@Test
	public void testDoFilterBothNull() throws IOException, ServletException {
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpServletResponse response = mock(HttpServletResponse.class);
		FilterChain chain = mock(FilterChain.class);

		when(request.getHeader("user-agent")).thenReturn(null);
		when(request.getHeader("Accept")).thenReturn(null);
		when(request.getRequestURI()).thenReturn("m/");
		BrowserDetectionFilter f = new BrowserDetectionFilter();

		f.doFilter(request, response, chain);

		// verify(response, atLeast(1)).sendRedirect("m/index.xhtml");
		verify(request, atLeast(1)).getHeader("user-agent");
		verify(request, atLeast(1)).getHeader("Accept");
		verify(chain, atLeast(1)).doFilter(request, response);

	}

	@Test
	public void testDoFilterNotMobile() throws IOException, ServletException {
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpServletResponse response = mock(HttpServletResponse.class);
		FilterChain chain = mock(FilterChain.class);

		when(request.getHeader("user-agent")).thenReturn("Windows 10");
		when(request.getHeader("Accept")).thenReturn("k.a.");

		BrowserDetectionFilter f = new BrowserDetectionFilter();

		f.doFilter(request, response, chain);

		// verify(response, atLeast(1)).sendRedirect("m/index.xhtml");
		verify(request, atLeast(1)).getHeader("user-agent");
		verify(request, atLeast(1)).getHeader("Accept");
		verify(chain, atLeast(1)).doFilter(request, response);

	}

	@Test
	public void testDestroy() {
		BrowserDetectionFilter f = new BrowserDetectionFilter();
		f.destroy();
	}

	@Test
	public void testInit() throws ServletException {
		FilterConfig filterConfig = mock(FilterConfig.class);
		BrowserDetectionFilter f = new BrowserDetectionFilter();
		f.init(filterConfig);
	}

}

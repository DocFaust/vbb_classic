package de.docfaust.vbb.filter;

import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.Test;

public class TestCharacterEncodingFilter {

	@Test
	public void testDoFilter() throws Exception {
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpServletResponse response = mock(HttpServletResponse.class);
		FilterChain chain = mock(FilterChain.class);

		CharacterEncodingFilter f = new CharacterEncodingFilter();
		f.doFilter(request, response, chain);
		verify(request, atLeast(1)).setCharacterEncoding("UTF-8");
		verify(chain, atLeast(1)).doFilter(request, response);
	}

	@Test
	public void testDestroy() {
		CharacterEncodingFilter f = new CharacterEncodingFilter();
		f.destroy();
	}

	@Test
	public void testInit() throws ServletException {
		FilterConfig filterConfig = mock(FilterConfig.class);
		CharacterEncodingFilter f = new CharacterEncodingFilter();
		f.init(filterConfig);
	}

}

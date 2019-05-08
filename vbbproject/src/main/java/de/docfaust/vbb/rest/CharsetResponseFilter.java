package de.docfaust.vbb.rest;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;

import javax.ws.rs.core.MediaType;

/**
 * Filter to correct Charsets.
 * @author wfa339
 *
 */
public class CharsetResponseFilter implements ContainerResponseFilter {
	@Override
	public void filter(final ContainerRequestContext request, final ContainerResponseContext response) {
		MediaType type = response.getMediaType();
		if (type != null) {
			String contentType = type.toString();
			if (!contentType.contains("charset")) {
				contentType = contentType + ";charset=UTF-8";
				response.getHeaders().putSingle("Content-Type", contentType);
			}
		}

	}
}
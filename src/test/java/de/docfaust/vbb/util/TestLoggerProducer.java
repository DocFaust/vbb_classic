package de.docfaust.vbb.util;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


import javax.enterprise.inject.spi.InjectionPoint;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;

public class TestLoggerProducer {

	@Test
	public void testProduceLogger() throws NoSuchMethodException, SecurityException {
		InjectionPoint injectionPoint = mock(InjectionPoint.class);
		
		
		when(injectionPoint.getMember()).thenReturn(this.getClass().getMethod("testProduceLogger"));
		LoggerProducer pr = new LoggerProducer();
		Logger logger = pr.produceLogger(injectionPoint);
		assertThat(logger, not(nullValue()));
		verify(injectionPoint, atLeast(1)).getMember();
	}

}

package de.docfaust.vbb.util;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Producer um den Logger per CDI zuinstanziieren.
 * 
 * @author xhu1011
 *
 */
@Dependent
public class LoggerProducer {
	/**
	 * Produziert den Logger.
	 * 
	 * @param injectionPoint
	 *            Einstiegspunkt
	 * @return Logger
	 */
	@Produces
	public Logger produceLogger(final InjectionPoint injectionPoint) {
		return LoggerFactory.getLogger(injectionPoint.getMember().getDeclaringClass().getName());
	}
}
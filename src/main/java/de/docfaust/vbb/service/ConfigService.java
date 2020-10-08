package de.docfaust.vbb.service;

import java.io.Serializable;

import de.docfaust.vbb.util.configuration.MailConfiguration;

/**
 * Service for the Config Entity.
 * @author wfa339
 *
 */
public interface ConfigService extends Serializable {

	/**
	 * Gets the MailConfig Bean.
	 * @return Bean
	 */
	MailConfiguration getMailConfig();

}
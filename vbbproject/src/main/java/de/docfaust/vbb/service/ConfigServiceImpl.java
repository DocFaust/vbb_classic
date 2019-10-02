package de.docfaust.vbb.service;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.docfaust.vbb.util.configuration.MailConfiguration;
import de.docfaust.vbb.util.configuration.MailConfigurationDB;

@Dependent
public class ConfigServiceImpl implements ConfigService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1926531297964133465L;

	@Inject
	@MailConfigurationDB
	private MailConfiguration mailConfig;

	@Inject
	private Logger logger;
	
	public ConfigServiceImpl(final MailConfiguration mailConfig) {
		super();
		this.mailConfig = mailConfig;
		this.logger = LoggerFactory.getLogger(getClass());
	}

	public ConfigServiceImpl() {
		super();
	}

	@Override
	public MailConfiguration getMailConfig() {
		return mailConfig;
	}
}

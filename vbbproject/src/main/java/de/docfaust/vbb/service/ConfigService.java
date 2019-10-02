package de.docfaust.vbb.service;

import java.io.Serializable;

import de.docfaust.vbb.util.configuration.MailConfiguration;

public interface ConfigService extends Serializable {

	MailConfiguration getMailConfig();

}
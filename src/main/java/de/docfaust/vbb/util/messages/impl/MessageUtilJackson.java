package de.docfaust.vbb.util.messages.impl;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import de.docfaust.vbb.util.messages.annotations.JacksonMessages;
import de.docfaust.vbb.util.messages.model.Message;
import de.docfaust.vbb.util.messages.model.Messages;

/**
 * 
 * Konkrete Implementierung zum Auslesen der messages.xml vial JAXB.
 * 
 * 
 * 
 * @author xhu1011
 *
 * 
 * 
 */
@Dependent
@JacksonMessages
public class MessageUtilJackson extends AbstractMessageUtil implements Serializable {
	private String messagesxml = "/messages.xml";
	private static final long serialVersionUID = -1084998740440093507L;

	@Inject
	private Logger logger;

	/**
	 * 
	 * Leerer Konstruktor.
	 * 
	 */
	public MessageUtilJackson() {
		if (logger == null) {
			logger = LoggerFactory.getLogger(getClass());
		}
	}

	/**
	 * 
	 * Konstruktor zum Setzen von XML und XSD die nicht dem Standard entsprechen.
	 * 
	 * 
	 * 
	 * @param messagesxml XML Datei
	 * 
	 * @param messagesxsd XSD Datei
	 * 
	 */
	public MessageUtilJackson(final String messagesxml, final String messagesxsd) {
		this.messagesxml = messagesxml;
		logger = LoggerFactory.getLogger(getClass());
	}

	/**
	 * 
	 * Implementierung der Leseroutine via JAXB.
	 * 
	 * 
	 * 
	 * @return Map der Messagedaten
	 * 
	 */
	@Override
	protected Map<String, MessageData> readMessages() {
		Map<String, MessageData> data = null;

		try {
			XmlMapper xmlMapper = new XmlMapper();
			Messages messages = xmlMapper.readValue(MessageUtilJackson.class.getClassLoader().getResourceAsStream(messagesxml),	Messages.class);

			List<Message> message = messages.getMessage();
			data = message.stream().map(m -> convert2MessageData(m)).collect(Collectors.toMap(m -> m.getCode(), m -> m));
		} catch (IOException e) {
			logger.error("Unable to read XML Messages", e);
		}

		return data;
	}

	private MessageData convert2MessageData(final Message message2) {
		MessageData mData = new MessageData();
		mData.setCode(message2.getCode());
		mData.setSummary(message2.getSummary());
		mData.setDetail(message2.getDetail());
		mData.setSeverity(message2.getSeverity());
		return mData;
	}

	@Override
	protected Logger getLogger() {
		return logger;
	}
}

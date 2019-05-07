package de.docfaust.vbb.util.messages.impl;

import java.io.Serializable;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.docfaust.vbb.util.messages.annotations.JAXBMessages;
import de.docfaust.vbb.util.xml.Message;
import de.docfaust.vbb.util.xml.Messages;

/**
 * Konkrete Implementierung zum Auslesen der messages.xml vial JAXB.
 * 
 * @author xhu1011
 *
 */
@Dependent
@JAXBMessages
public class MessageUtilJAXB extends AbstractMessageUtil implements Serializable {
	private String messagesxml = "/messages.xml";
	private String messagesxsd = "/messages.xsd";
	private static final long serialVersionUID = -1084998740440093507L;
	@Inject
	private Logger logger;

	/**
	 * Leerer Konstruktor.
	 */
	public MessageUtilJAXB() {
		if (logger == null) {
			logger = LoggerFactory.getLogger(getClass());
		}
	}

	/**
	 * Konstruktor zum Setzen von XML und XSD die nicht dem Standard
	 * entsprechen.
	 * 
	 * @param messagesxml
	 *            XML Datei
	 * @param messagesxsd
	 *            XSD Datei
	 */
	public MessageUtilJAXB(final String messagesxml, final String messagesxsd) {
		this.messagesxml = messagesxml;
		this.messagesxsd = messagesxsd;
		logger = LoggerFactory.getLogger(getClass());
	}

	/**
	 * Implementierung der Leseroutine via JAXB.
	 * 
	 * @return Map der Messagedaten
	 */
	@Override
	protected Map<String, MessageData> readMessages() {
		Map<String, MessageData> data = new Hashtable<String, MessageData>();
		try {
			SchemaFactory schemaFactory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
			Schema schema;
			if (messagesxsd == null || messagesxsd.trim().length() == 0) {
				schema = null;
			} else {
				schema = schemaFactory.newSchema(
						new StreamSource(MessageUtilJAXB.class.getClassLoader().getResourceAsStream(messagesxsd)));
			}
			JAXBContext jaxbContext = JAXBContext.newInstance(Messages.class.getPackage().getName());
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			unmarshaller.setSchema(schema);
			Messages messages = (Messages) unmarshaller.unmarshal(
					new StreamSource(MessageUtilJAXB.class.getClassLoader().getResourceAsStream(messagesxml)));

			List<Message> message = messages.getMessage();
			for (Message message2 : message) {
				MessageData mData = new MessageData();
				mData.setCode(message2.getCode());
				mData.setSummary(message2.getSummary());
				mData.setDetail(message2.getDetail());
				mData.setSeverity(message2.getSeverity());
				data.put(mData.getCode(), mData);
			}
		} catch (Exception e1) {
			logger.error("Fehler beim Parsen von " + messagesxml, e1);
		}
		return data;
	}

	@Override
	protected Logger getLogger() {
		return logger;
	}
}

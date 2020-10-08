package de.docfaust.vbb.data.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the MAILS database table.
 * 
 */
@Entity
@Table(name = "MAILS")
@NamedQuery(name = "Mail.findAll", query = "SELECT m FROM Mail m")
public class Mail extends AbstractEntity {
	private static final int MAX_LENGTH_EMAILTEXT = 4096;

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String recipient;

	private String subject;

	@Column(length = MAX_LENGTH_EMAILTEXT)
	private String text;

	private int attempt;

	/**
	 * Leerer Konstruktor.
	 */
	public Mail() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(final int id) {
		this.id = id;
	}

	public String getRecipient() {
		return this.recipient;
	}

	public void setRecipient(final String recipient) {
		this.recipient = recipient;
	}

	public String getSubject() {
		return this.subject;
	}

	public void setSubject(final String subject) {
		this.subject = subject;
	}

	public String getText() {
		return this.text;
	}

	public void setText(final String text) {
		this.text = text;
	}

	public int getAttempt() {
		return attempt;
	}

	public void setAttempt(final int attempt) {
		this.attempt = attempt;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(this.getClass().getSimpleName()).append(": [");
		builder.append("id=").append(id).append(", ");
		builder.append("recipient=").append(recipient).append(", ");
		builder.append("subject=").append(subject).append(", ");
		builder.append("text=").append(text).append(", ");
		builder.append("attempt=").append(attempt).append("]");
		return builder.toString();
	}

}
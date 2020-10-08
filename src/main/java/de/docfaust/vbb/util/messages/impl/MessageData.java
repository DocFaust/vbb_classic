package de.docfaust.vbb.util.messages.impl;

/**
 * Daten einer UI Message.
 */
public class MessageData {

	/**
	 * Code der Message.
	 */
	private String code;

	/**
	 * Schweregrad.
	 */
	private String severity;

	/**
	 * Zusammenfassung.
	 */
	private String summary;

	/**
	 * Detailnachricht.
	 */
	private String detail;

	public String getCode() {
		return code;
	}

	public void setCode(final String code) {
		this.code = code;
	}

	public String getSeverity() {
		return severity;
	}

	public void setSeverity(final String severity) {
		this.severity = severity;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(final String summary) {
		this.summary = summary;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(final String detail) {
		this.detail = detail;
	}
}

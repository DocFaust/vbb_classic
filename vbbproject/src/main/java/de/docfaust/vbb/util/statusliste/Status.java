package de.docfaust.vbb.util.statusliste;

import de.docfaust.vbb.util.messages.MessageConstants;

/**
 * Status für die Statusliste.
 * 
 * @author xhu1011
 *
 */
public class Status {
	private MessageConstants code;
	private Object[] zusatzInfo;

	public MessageConstants getCode() {
		return code;
	}

	public void setCode(final MessageConstants code) {
		this.code = code;
	}

	public Object[] getZusatzInfo() {
		return zusatzInfo;
	}

	public void setZusatzInfo(final Object[] zusatzInfo) {
		this.zusatzInfo = zusatzInfo;
	}

	boolean booleanValue() {
		return code.isPositive();
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(getClass().getSimpleName()).append(":[");
		builder.append("code=").append(code.toString()).append(",");
		builder.append("isPositive=").append(code.isPositive()).append(",");
		builder.append("zusatzinfo=[");
		for (Object object : zusatzInfo) {
			builder.append(object.toString()).append(",");
		}
		builder.append("]]");
		return builder.toString();
	}
}

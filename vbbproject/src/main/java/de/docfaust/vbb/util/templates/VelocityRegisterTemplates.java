/**
 * 
 */
package de.docfaust.vbb.util.templates;

import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.Dependent;

/**
 * @author xhu1011
 *
 */
@Dependent
@VelocityRegisterTemplate
public class VelocityRegisterTemplates implements RegisterTemplates {

	private static final String REGISTER_OK = "registerok.vm";
	private static final String YET_REGISTERED = "yetregistered.vm";
	private static final String WRONG_ID = "wrongid.vm";
	private static final String NOT_REGISTERED = "notregistered.vm";
	private static final String WRONG_REQUEST = "wrongrequest.vm";

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.docfaust.vbb.util.templates.RegisterTemplates#getOk(java.lang.String)
	 */
	@Override
	public String getOk(final String userid, final String domain) {
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("userid", userid);
		values.put("domain", domain);
		return VelocityBuilder.getInstance().buildMessage(REGISTER_OK, values);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.docfaust.vbb.util.templates.RegisterTemplates#getYetRegistered(java
	 * .lang.String)
	 */
	@Override
	public String getYetRegistered(final String userid) {
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("userid", userid);
		return VelocityBuilder.getInstance().buildMessage(YET_REGISTERED, values);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.docfaust.vbb.util.templates.RegisterTemplates#getWrongID(java.lang
	 * .String)
	 */
	@Override
	public String getWrongID(final String userid) {
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("userid", userid);
		return VelocityBuilder.getInstance().buildMessage(WRONG_ID, values);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.docfaust.vbb.util.templates.RegisterTemplates#getNotRegistered(java
	 * .lang.String)
	 */
	@Override
	public String getNotRegistered(final String userid) {
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("userid", userid);
		return VelocityBuilder.getInstance().buildMessage(NOT_REGISTERED, values);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.docfaust.vbb.util.templates.RegisterTemplates#getWrongRequest()
	 */
	@Override
	public String getWrongRequest() {
		return VelocityBuilder.getInstance().buildMessage(WRONG_REQUEST, new HashMap<String, Object>());
	}

}

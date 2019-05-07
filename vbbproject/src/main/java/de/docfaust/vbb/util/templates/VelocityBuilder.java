package de.docfaust.vbb.util.templates;

import java.io.StringWriter;
import java.util.Map;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

/**
 * Singleton für die Velocityengine.
 * 
 * @author xhu1011
 *
 */
public final class VelocityBuilder {
	private static final String TEMPLATEPATH = "templates";
	private static final String ENCODING = "ISO-8859-1";
	private static final VelocityBuilder INSTANCE = new VelocityBuilder();
	private VelocityEngine ve = new VelocityEngine();

	private VelocityBuilder() {
		ve.setProperty(VelocityEngine.RUNTIME_LOG_LOGSYSTEM, new Slf4jLogChute());
		ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
		ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
		ve.init();
	}

	/**
	 * Gibt die Singletoninstanz zurück.
	 * 
	 * @return Instanz
	 */
	public static VelocityBuilder getInstance() {
		return INSTANCE;
	}

	/**
	 * Baut eine Message zusammen.
	 * 
	 * @param templatename
	 *            Name des Templates.
	 * @param values
	 *            Werte aus dem Template
	 * @return befüllte Nachricht.
	 */
	public String buildMessage(final String templatename, final Map<String, Object> values) {
		VelocityContext context = new VelocityContext(values);

		StringWriter w = new StringWriter();
		Template template = ve.getTemplate(TEMPLATEPATH + "/" + templatename, ENCODING);
		template.merge(context, w);
		return w.toString();
	}
}

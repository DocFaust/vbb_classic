package de.docfaust.vbb.util.templates;

import org.apache.velocity.runtime.RuntimeInstance;
import org.apache.velocity.runtime.log.LogChute;
import org.junit.Test;

/**
 * Nur zur Erhöhung der Abdeckung.
 * 
 * @author xhu1011
 *
 */
public class Slf4jLogChuteTest {

	@Test
	public void test() {
		Throwable t = new NullPointerException("Zum Testen");
		Slf4jLogChute chute = new Slf4jLogChute();
		chute.init(new RuntimeInstance());
		chute.log(LogChute.WARN_ID, "Warning");
		chute.log(LogChute.INFO_ID, "Info");
		chute.log(LogChute.TRACE_ID, "Trace");
		chute.log(LogChute.ERROR_ID, "Error");
		chute.log(LogChute.DEBUG_ID, "Debug");
		chute.log(25, "Default");
		chute.log(LogChute.WARN_ID, "Warning", t);
		chute.log(LogChute.INFO_ID, "Info", t);
		chute.log(LogChute.TRACE_ID, "Trace", t);
		chute.log(LogChute.ERROR_ID, "Error", t);
		chute.log(LogChute.DEBUG_ID, "Debug", t);
		chute.log(25, "Default", t);
		
		chute.isLevelEnabled(LogChute.WARN_ID);
		chute.isLevelEnabled(LogChute.INFO_ID);
		chute.isLevelEnabled(LogChute.TRACE_ID);
		chute.isLevelEnabled(LogChute.ERROR_ID);
		chute.isLevelEnabled(LogChute.DEBUG_ID);
		chute.isLevelEnabled(25);
	}

}

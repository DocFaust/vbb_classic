package de.docfaust.vbb.util;

import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.runner.RunWith;

import de.docfaust.vbb.util.configuration.TestMailConfigurationDB;
import de.docfaust.vbb.util.journal.TestJournal;
import de.docfaust.vbb.util.statusliste.TestStatusliste;
import de.docfaust.vbb.util.templates.Slf4jLogChuteTest;
import de.docfaust.vbb.util.templates.VelocityBuilderTest;

@RunWith(JUnitPlatform.class)
@SelectClasses({ TestUIMessages.class, TestStatusliste.class, VelocityBuilderTest.class, Slf4jLogChuteTest.class,
		TestRegistrationState.class, TestMailConfigurationDB.class, TestLoggerProducer.class, TestJournal.class })
public class UtilTests {

}

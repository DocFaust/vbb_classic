package de.docfaust.vbb.util;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import de.docfaust.vbb.util.configuration.TestMailConfigurationDB;
import de.docfaust.vbb.util.journal.TestJournal;
import de.docfaust.vbb.util.statusliste.TestStatusliste;
import de.docfaust.vbb.util.templates.Slf4jLogChuteTest;
import de.docfaust.vbb.util.templates.VelocityBuilderTest;

@RunWith(Suite.class)
@SuiteClasses({ TestUIMessages.class, TestStatusliste.class, VelocityBuilderTest.class, Slf4jLogChuteTest.class,
		TestRegistrationState.class, TestMailConfigurationDB.class, TestLoggerProducer.class, TestMD5Util.class, TestJournal.class })
public class UtilTests {

}

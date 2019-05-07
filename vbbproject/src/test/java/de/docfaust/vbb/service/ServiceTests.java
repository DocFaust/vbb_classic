package de.docfaust.vbb.service;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ TestVBBServices.class, MailSenderTest.class })
public class ServiceTests {

}

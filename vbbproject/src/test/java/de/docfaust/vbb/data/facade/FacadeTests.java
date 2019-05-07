package de.docfaust.vbb.data.facade;

import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.SuiteDisplayName;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
@SuiteDisplayName("Facade Tests")
@SelectClasses({ TestBuchungFacade.class, TestSeasonFacade.class, TestSpielFacade.class, TestSpielerFacade.class, TestConfigFacade.class, TestUserFacade.class,
		TestGroupFacade.class, TestMailFacade.class })
public class FacadeTests {

}

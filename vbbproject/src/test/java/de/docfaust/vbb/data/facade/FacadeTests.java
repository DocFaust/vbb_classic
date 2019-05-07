package de.docfaust.vbb.data.facade;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ TestBuchungFacade.class, TestSeasonFacade.class, TestSpielFacade.class, TestSpielerFacade.class, TestConfigFacade.class, TestUserFacade.class,
		TestGroupFacade.class, TestMailFacade.class })
public class FacadeTests {

}

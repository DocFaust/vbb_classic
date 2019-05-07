package de.docfaust.vbb;


import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.SuiteDisplayName;
import org.junit.runner.RunWith;

import de.docfaust.vbb.data.facade.FacadeTests;

@RunWith(JUnitPlatform.class)
//@SelectClasses({ FacadeTests.class, UtilTests.class, ServiceTests.class, ValidationTests.class, ServletTests.class,
//		AllValidatorTests.class, AllConverterTests.class, AllJSFTests.class, AllFilterTests.class, AllEntityTests.class })
@SelectPackages({ "de.docfaust.vbb.data.facade" })
@SuiteDisplayName("All Tests")
public class AllTests {

}

package de.docfaust.vbb;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import de.docfaust.vbb.data.entity.AllEntityTests;
import de.docfaust.vbb.data.facade.FacadeTests;
import de.docfaust.vbb.filter.AllFilterTests;
import de.docfaust.vbb.jsfbeans.AllJSFTests;
import de.docfaust.vbb.jsfbeans.convert.AllConverterTests;
import de.docfaust.vbb.jsfbeans.validation.AllValidatorTests;
import de.docfaust.vbb.service.ServiceTests;
import de.docfaust.vbb.servlet.ServletTests;
import de.docfaust.vbb.util.UtilTests;
import de.docfaust.vbb.validation.ValidationTests;

@RunWith(Suite.class)
@SuiteClasses({ FacadeTests.class, UtilTests.class, ServiceTests.class, ValidationTests.class, ServletTests.class,
		AllValidatorTests.class, AllConverterTests.class, AllJSFTests.class, AllFilterTests.class, AllEntityTests.class })
public class AllTests {

}

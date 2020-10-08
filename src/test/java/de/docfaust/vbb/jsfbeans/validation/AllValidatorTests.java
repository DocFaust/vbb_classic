package de.docfaust.vbb.jsfbeans.validation;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ TestDateRangeValidator.class, TestPasswordRepetitionValidator.class, TestPasswordValidator.class })
public class AllValidatorTests {

}

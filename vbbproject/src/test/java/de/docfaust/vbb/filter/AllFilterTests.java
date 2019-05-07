package de.docfaust.vbb.filter;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ TestBrowserDetectionFilter.class, TestCharacterEncodingFilter.class })
public class AllFilterTests {

}

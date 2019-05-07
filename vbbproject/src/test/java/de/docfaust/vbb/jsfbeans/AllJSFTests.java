package de.docfaust.vbb.jsfbeans;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import de.docfaust.vbb.jsfbeans.mobile.TestMobileLoginBean;

@RunWith(Suite.class)
@SuiteClasses({ TestEditSpielerBean.class, TestEditSeasonBean.class, TestReorgDatabaseBean.class,
		TestSearchSpielBean.class, TestMobileLoginBean.class, TestEditProfileBean.class, TestSpielBean.class,
		TestEditUserBean.class, TestRegisterBean.class, TestCreateBookingBean.class, TestEditConfigBean.class })
public class AllJSFTests {

}

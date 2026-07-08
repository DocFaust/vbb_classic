package de.docfaust.vbb.servlet;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.jupiter.api.Test;

import de.docfaust.vbb.data.util.JpaBaseRolledBackTestCase;

public class TestRegisterServlet extends JpaBaseRolledBackTestCase {

	@Test
	public void test(){
		assertThat(new RegisterServlet(), not(nullValue()));
	}
}

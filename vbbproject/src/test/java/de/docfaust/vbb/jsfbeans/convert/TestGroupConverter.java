package de.docfaust.vbb.jsfbeans.convert;

import static org.junit.Assert.*;

import javax.faces.convert.ConverterException;

import static org.hamcrest.CoreMatchers.*;

import org.junit.Test;

import de.docfaust.vbb.data.util.JpaBaseRolledBackTestCase;

public class TestGroupConverter extends JpaBaseRolledBackTestCase {

	@Test
	public void testGetAsObject() {
		GroupConverter con = new GroupConverter(facadenFactory.getGroupFacade());
		assertThat(con.getAsObject(null, null, "1"), equalTo(facadenFactory.getGroupFacade().find(1)));
	}
	@Test
	public void testGetAsObjectNaN() {
		GroupConverter con = new GroupConverter(facadenFactory.getGroupFacade());
		try {
			assertThat(con.getAsObject(null, null, "abc"), nullValue());
			fail("Exception erwartet");
		} catch (ConverterException e) {
			// Alles gut
		}
	}
	@Test
	public void testGetAsObjectEmpty() {
		GroupConverter con = new GroupConverter(facadenFactory.getGroupFacade());
		assertThat(con.getAsObject(null, null, ""), nullValue());
	}
	@Test
	public void testGetAsObjectNullValue() {
		GroupConverter con = new GroupConverter(facadenFactory.getGroupFacade());
		assertThat(con.getAsObject(null, null, null), nullValue());
	}

	@Test
	public void testGetAsString() {
		GroupConverter con = new GroupConverter(facadenFactory.getGroupFacade());
		assertThat(con.getAsString(null, null, facadenFactory.getGroupFacade().find(1)), equalTo("1"));
	}

	@Test
	public void testGetAsStringNull() {
		GroupConverter con = new GroupConverter(facadenFactory.getGroupFacade());
		assertThat(con.getAsString(null, null, null), nullValue());
	}
	@Test
	public void testGetAsStringWrongObject() {
		GroupConverter con = new GroupConverter(facadenFactory.getGroupFacade());
		assertThat(con.getAsString(null, null, "Bifzibafzi"), nullValue());
	}
}

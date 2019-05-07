package de.docfaust.vbb.jsfbeans.convert;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import javax.faces.convert.ConverterException;

import org.junit.jupiter.api.Test;

import de.docfaust.vbb.data.util.JpaBaseRolledBackTestCase;

public class TestSpielerConverter extends JpaBaseRolledBackTestCase {

	@Test
	public void testGetAsObject() {
		SpielerConverter con = new SpielerConverter(facadenFactory.getSpielerFacade());
		assertThat(con.getAsObject(null, null, "1"), equalTo(facadenFactory.getSpielerFacade().find(1)));
	}
	
	@Test
	public void testGetAsObjectNullVallue() {
		SpielerConverter con = new SpielerConverter(facadenFactory.getSpielerFacade());
		assertThat(con.getAsObject(null, null, null), nullValue());
	}
	
	@Test
	public void testGetAsObjectEmpty() {
		SpielerConverter con = new SpielerConverter(facadenFactory.getSpielerFacade());
		assertThat(con.getAsObject(null, null, ""), nullValue());
	}

	@Test
	public void testGetAsObjectNaN() {
		SpielerConverter con = new SpielerConverter(facadenFactory.getSpielerFacade());
		try {
			assertThat(con.getAsObject(null, null, "abc"), equalTo(nullValue()));
			fail("Hier sollte eine Exception fliegen");
		} catch (ConverterException e) {
			// Alles Gut
		}
	}
 	@Test
	public void testGetAsString() {
		SpielerConverter con = new SpielerConverter(facadenFactory.getSpielerFacade());
		assertThat(con.getAsString(null, null, facadenFactory.getSpielerFacade().find(1)), equalTo("1"));
	}
 	@Test
	public void testGetAsStringWrongObject() {
		SpielerConverter con = new SpielerConverter(facadenFactory.getSpielerFacade());
		assertThat(con.getAsString(null, null, "Hulabasti"), nullValue());
	}
 	@Test
	public void testGetAsStringNull() {
		SpielerConverter con = new SpielerConverter(facadenFactory.getSpielerFacade());
		assertThat(con.getAsString(null, null, null), nullValue());
	}

}

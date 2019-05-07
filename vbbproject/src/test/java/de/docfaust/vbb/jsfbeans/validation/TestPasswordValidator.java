package de.docfaust.vbb.jsfbeans.validation;

import static org.junit.Assert.fail;

import org.junit.Test;

public class TestPasswordValidator {

	@Test
	public void testValidateOk() {
	
		PasswordValidator val = new PasswordValidator();
		try {
			val.validate(null, null, "12345678");
			
		} catch (Exception e) {
			fail("Sollte Ok sein");
		}
	}
	@Test
	public void testValidateNOk() {
	
		PasswordValidator val = new PasswordValidator();
		try {
			val.validate(null, null, "1234567");
			fail("Sollte NOk sein");
		
		} catch (Exception e) {
			// Alles gut
		}
	}
	@Test
	public void testValidateNull() {
	
		PasswordValidator val = new PasswordValidator();
		try {
			val.validate(null, null, null);
			
		} catch (Exception e) {
			fail("Sollte Ok sein");
		}
	}

}

package de.docfaust.vbb.util;

import org.junit.Test;

public class TestRegistrationState {

	@Test
	public void test() {
		RegistrationState.valueOf(RegistrationState.OPEN.toString());
	}

}

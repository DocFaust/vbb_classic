package de.docfaust.vbb.util.statusliste;

import static org.junit.Assert.*;

import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

import de.docfaust.vbb.util.messages.MessageConstants;

public class TestStatusliste {

	@Test
	public void testAddStatusStatus() {
		Statusliste statusliste = new Statusliste();
		Status status = new Status();
		status.setCode(MessageConstants.ALREADYREGISTERED);
		status.setZusatzInfo(new String[] { "Muh", "Mäh" });
		statusliste.addStatus(status);

		assertTrue(statusliste.hasStatus(MessageConstants.ALREADYREGISTERED));
		assertFalse(statusliste.booleanValue());

		LoggerFactory.getLogger(getClass()).debug(statusliste.toString());
	}

	@Test
	public void testAddStatusStatusPositive() {
		Statusliste statusliste = new Statusliste();
		Status status = new Status();
		status.setCode(MessageConstants.SUCCESSFUL);
		status.setZusatzInfo(new String[] { "Muh", "Mäh" });
		statusliste.addStatus(status);

		assertTrue(statusliste.hasStatus(MessageConstants.SUCCESSFUL));
		assertTrue(statusliste.booleanValue());

		LoggerFactory.getLogger(getClass()).debug(statusliste.toString());
	}

	@Test
	public void testAddStatusMessageConstantsObjectArray() {
		Statusliste statusliste = new Statusliste();
		statusliste.addStatus(MessageConstants.ALREADYREGISTERED, "Muh", "Mäh");

		assertTrue(statusliste.hasStatus(MessageConstants.ALREADYREGISTERED));
		assertFalse(statusliste.booleanValue());

		LoggerFactory.getLogger(getClass()).debug(statusliste.toString());
	}

	@Test
	public void testAddStatusMessageConstantsObjectArrayPositive() {
		Statusliste statusliste = new Statusliste();
		statusliste.addStatus(MessageConstants.SUCCESSFUL, "Muh", "Mäh");

		assertTrue(statusliste.hasStatus(MessageConstants.SUCCESSFUL));
		assertTrue(statusliste.booleanValue());

		LoggerFactory.getLogger(getClass()).debug(statusliste.toString());
	}

	@Test
	public void testEmptyStatusliste() {
		Statusliste statusliste = new Statusliste();
		assertFalse(statusliste.hasStatus(MessageConstants.SUCCESSFUL));
		assertTrue(statusliste.booleanValue());

		LoggerFactory.getLogger(getClass()).debug(statusliste.toString());
	}

	@Test
	public void testAddStatusMessageConstantsObjectArrayMore() {
		Statusliste statusliste = new Statusliste();
		statusliste.addStatus(MessageConstants.ALREADYREGISTERED, "Muh", "Mäh");
		statusliste.addStatus(MessageConstants.SUCCESSFUL);

		assertTrue(statusliste.hasStatus(MessageConstants.ALREADYREGISTERED));
		assertTrue(statusliste.hasStatus(MessageConstants.SUCCESSFUL));
		assertFalse(statusliste.booleanValue());

		LoggerFactory.getLogger(getClass()).debug(statusliste.toString());
	}

	@Test
	public void testAddStatusliste() {
		Statusliste statusliste1 = new Statusliste();
		Statusliste statusliste2 = new Statusliste();
		statusliste1.addStatus(MessageConstants.ALREADYREGISTERED, "Muh", "Mäh");
		statusliste2.addStatus(MessageConstants.SUCCESSFUL);

		statusliste1.addStatusliste(statusliste2);

		assertTrue(statusliste1.hasStatus(MessageConstants.ALREADYREGISTERED));
		assertTrue(statusliste1.hasStatus(MessageConstants.SUCCESSFUL));
		assertFalse(statusliste1.booleanValue());
	}
}

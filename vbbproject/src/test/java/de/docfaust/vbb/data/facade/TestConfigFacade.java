package de.docfaust.vbb.data.facade;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import de.docfaust.vbb.data.entity.Config;
import de.docfaust.vbb.data.facades.ConfigFacade;
import de.docfaust.vbb.data.util.JpaBaseRolledBackTestCase;

public class TestConfigFacade extends JpaBaseRolledBackTestCase {

	@Test
	public void test() {
		assertThat(new ConfigFacade(), not(nullValue()));
		Config config = new Config();
		config.setId(1);
		assertThat(config.getId(), equalTo(1));

	}

	@Test
	public void testFindAll() {
		assertFalse(facadenFactory.getConfigFacade().findAll().isEmpty());
	}

	@Test
	public void testGetKey() throws Exception {
		ConfigFacade configFacade = facadenFactory.getConfigFacade();

		assertEquals("b", configFacade.getValue("a"));
		assertEquals("c", configFacade.getValue("b"));
	}

	@Test
	public void testGetKeyNotFound() throws Exception {
		ConfigFacade configFacade = facadenFactory.getConfigFacade();

		assertNull(configFacade.getValue("x"));
	}

	@Test
	public void testGetKeyNotFoundDefault() throws Exception {
		ConfigFacade configFacade = facadenFactory.getConfigFacade();

		String value = configFacade.getValue("x", "y");
		assertNotNull(value);
		assertEquals("y", value);
	}

	@Test
	public void testGetDefault() throws Exception {
		ConfigFacade configFacade = facadenFactory.getConfigFacade();

		String value = configFacade.getValue("a", "y");
		assertNotNull(value);
		assertEquals("b", value);

		value = configFacade.getValue("b", "y");
		assertNotNull(value);
		assertEquals("c", value);
	}

	@Test
	public void testGetConfig() {
		ConfigFacade configFacade = facadenFactory.getConfigFacade();
		assertEquals(6, configFacade.count());
		for (Config config : configFacade.findAll()) {
			logger.info(config.toString());
			assertNotNull(config.getId());
			assertNotNull(config.getConfigkey());
			assertNotNull(config.getConfigvalue());
		}

	}

	@Test
	public void testSaveConfig() {
		Config config = new Config();
		config.setConfigkey("my");
		config.setConfigvalue("oh my");

		facadenFactory.getConfigFacade().create(config);

		logger.info(config.toString());
		assertEquals(7, facadenFactory.getConfigFacade().count());
		assertEquals(7, config.getId());
	}
	
	@Test
	public void testDeleteConfig(){
		int count = facadenFactory.getConfigFacade().count();
		Config config = new Config();
		config.setId(1);
		facadenFactory.getConfigFacade().remove(config);
		assertThat(facadenFactory.getConfigFacade().count(), equalTo(count - 1));
	}
	
	@Test
	public void testDeleteConfigNotFound(){
		int count = facadenFactory.getConfigFacade().count();
		Config config = new Config();
		config.setId(404);
		config.setConfigkey("");
		config.setConfigvalue("");
		facadenFactory.getConfigFacade().remove(config);
		assertThat(facadenFactory.getConfigFacade().count(), equalTo(count));
	}

}

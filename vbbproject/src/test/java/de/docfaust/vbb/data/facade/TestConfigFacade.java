package de.docfaust.vbb.data.facade;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import de.docfaust.vbb.data.entity.Config;
import de.docfaust.vbb.data.facades.ConfigFacade;
import de.docfaust.vbb.data.util.JpaBaseRolledBackTestCase;

public class TestConfigFacade extends JpaBaseRolledBackTestCase {

	@Test
	@DisplayName("Test Config Facade ")
	public void test() {
		assertThat(new ConfigFacade()).isNotNull();
		Config config = new Config();
		config.setId(1);
		assertThat(config.getId()).isEqualTo(1);

	}

	@Test
	@DisplayName("Test Find All ")
	public void testFindAll() {
		assertThat(facadenFactory.getConfigFacade().findAll()).isNotEmpty();
	}

	@Test
	@DisplayName("Test getKey ")
	public void testGetKey() throws Exception {
		ConfigFacade configFacade = facadenFactory.getConfigFacade();
		assertThat(configFacade.getValue("a")).isEqualTo("b");
		assertThat(configFacade.getValue("b")).isEqualTo("c");
	}

	@Test
	public void testGetKeyNotFound() throws Exception {
		ConfigFacade configFacade = facadenFactory.getConfigFacade();

		assertThat(configFacade.getValue("x")).isNull();
	}

	@Test
	public void testGetKeyNotFoundDefault() throws Exception {
		ConfigFacade configFacade = facadenFactory.getConfigFacade();

		String value = configFacade.getValue("x", "y");
		assertThat(value).isNotNull().isEqualTo("y");
	}

	@Test
	public void testGetDefault() throws Exception {
		ConfigFacade configFacade = facadenFactory.getConfigFacade();

		String value = configFacade.getValue("a", "y");
		assertThat(value).isNotNull().isEqualTo("b");

		value = configFacade.getValue("b", "y");
		assertThat(value).isNotNull().isEqualTo("c");
		
		value = configFacade.getValue("r", "l");
		assertThat(value).isNotNull().isEqualTo("l");
	}

	@Test
	public void testGetConfig() {
		ConfigFacade configFacade = facadenFactory.getConfigFacade();
		assertThat(configFacade.count()).isEqualTo(6);
		assertThat(configFacade.findAll()).extracting("id", "configkey", "configvalue").isNotNull();
	}

	@Test
	public void testSaveConfig() {
		Config config = new Config();
		config.setConfigkey("my");
		config.setConfigvalue("oh my");

		facadenFactory.getConfigFacade().create(config);

		logger.info(config.toString());
		assertThat(facadenFactory.getConfigFacade().count()).isEqualTo(7);
		assertThat(config.getId()).isEqualTo(7);
	}

	@Test
	public void testDeleteConfig() {
		int count = facadenFactory.getConfigFacade().count();
		Config config = new Config();
		config.setId(1);
		facadenFactory.getConfigFacade().remove(config);
		assertThat(facadenFactory.getConfigFacade().count()).isEqualTo(count - 1);
	}

	@Test
	public void testDeleteConfigNotFound() {
		int count = facadenFactory.getConfigFacade().count();
		Config config = new Config();
		config.setId(404);
		config.setConfigkey("");
		config.setConfigvalue("");
		facadenFactory.getConfigFacade().remove(config);
		assertThat(facadenFactory.getConfigFacade().count()).isEqualTo(count);
	}

}

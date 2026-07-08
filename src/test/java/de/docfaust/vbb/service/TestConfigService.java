package de.docfaust.vbb.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.docfaust.vbb.ServiceCreator;
import de.docfaust.vbb.data.util.JpaBaseRolledBackTestCase;

public class TestConfigService extends JpaBaseRolledBackTestCase {

	private ConfigService configService;

	@BeforeEach
	public void setUp() {
		configService = new ServiceCreator(em).getConfigService();
	}

	@Test
	void testInit() {
		assertThat(new ConfigServiceImpl()).isNotNull();
	}

	@Test
	void testGetMailConfig() {
		assertThat(configService.getMailConfig()).isNotNull()
				.extracting("domain", "senderAddress", "registrationSubject").contains("http://localhost:8080/vbb",
						"noreply@volleybuchung.example", "Registrierung bei der Volleyballbuchung");
	}
}

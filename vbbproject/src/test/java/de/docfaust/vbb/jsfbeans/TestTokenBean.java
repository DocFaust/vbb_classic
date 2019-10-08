package de.docfaust.vbb.jsfbeans;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.docfaust.vbb.ServiceCreator;
import de.docfaust.vbb.data.entity.ValidityState;
import de.docfaust.vbb.data.util.JpaBaseRolledBackTestCase;

class TestTokenBean extends JpaBaseRolledBackTestCase{

	private TokenBean bean;

	@BeforeEach
	void setUp() throws Exception {
		ServiceCreator sc = new ServiceCreator(em);
		bean = new TokenBean(sc.getTokenService());
	}

	@Test
	void testEJBInit() {
		assertThat(new TokenBean()).isNotNull();
	}
	
	@Test
	void testInit() {
		assertThat(bean).isNotNull();
		bean.init();
		assertThat(bean.getTokenList()).isNotNull().isNotEmpty().hasSize(facadenFactory.getTokenFacade().count());
		
	}
	
	@Test
	void testInitEmpty() {
		assertThat(bean).isNotNull();
		bean.init();
		assertThat(bean.getTokenList()).isNotNull().isNotEmpty().hasSize(facadenFactory.getTokenFacade().count());
		
		bean.getTokenList().stream().forEach(token -> facadenFactory.getTokenFacade().remove(token));
		
		bean.init();
		
		assertThat(bean.getSelectedToken()).isNull();
		assertThat(bean.getTokenList()).isNotNull().isEmpty();
		
	}

	@Test
	void testGenerateClipBoardURL() {
		assertThat(bean).isNotNull();
		bean.init();
		assertThat(bean.getClipboardURL()).isNull();
		bean.generateClipBoardURL();
		assertThat(bean.getClipboardURL()).isNotNull().isEqualTo("http://localhost:8080/vbb/faces/saldo.xhtml?token=1");
		
		bean.setSelectedToken(bean.getTokenList().get(1));
		bean.generateClipBoardURL();
		assertThat(bean.getClipboardURL()).isNotNull().isEqualTo("http://localhost:8080/vbb/faces/saldo.xhtml?token=2");
		
		bean.setSelectedToken(null);
		bean.generateClipBoardURL();
		assertThat(bean.getClipboardURL()).isNull();
	}
	@Test
	void testGenerateClipBoardURL2() {
		assertThat(bean).isNotNull();
		bean.init();
		assertThat(bean.getClipboardURL()).isNull();
		bean.setSelectedToken(bean.getTokenList().get(1));
		bean.generateClipBoardURL();
		assertThat(bean.getClipboardURL()).isNotNull().isEqualTo("http://localhost:8080/vbb/faces/saldo.xhtml?token=2");
	}
	@Test
	void testGenerateClipBoardURLNull() {
		assertThat(bean).isNotNull();
		bean.init();
		assertThat(bean.getClipboardURL()).isNull();
		
		bean.setSelectedToken(null);
		bean.generateClipBoardURL();
		assertThat(bean.getClipboardURL()).isNull();
	}

	@Test
	void testCreateToken() {
		assertThat(bean).isNotNull();
		bean.init();
		int count = facadenFactory.getTokenFacade().count();
		bean.setName("JUnit_Test");
		bean.createToken();
		// Token in DB angelegt
		assertThat(facadenFactory.getTokenFacade().count()).isEqualTo(++count);
		
		// Liste aktualisiert
		assertThat(bean.getTokenList()).isNotNull().isNotEmpty().hasSize(facadenFactory.getTokenFacade().count());
		
		// selectedTokoen gesetzt
		assertThat(bean.getSelectedToken()).isNotNull().extracting("name").contains("JUnit_Test");
	}

	@Test
	void testInvalidateToken() {
		assertThat(bean).isNotNull();
		bean.init();
		assertThat(bean.getSelectedToken().getState()).isEqualTo(ValidityState.VALID);
		bean.invalidateToken();
		assertThat(bean.getSelectedToken().getState()).isEqualTo(ValidityState.INVALID);
		
		assertThat(facadenFactory.getTokenFacade().find(bean.getSelectedToken().getId()).getState()).isEqualTo(ValidityState.INVALID);
	}

	@Test
	void testDeleteToken() {
		assertThat(bean).isNotNull();
		bean.init();
		int count = facadenFactory.getTokenFacade().count();
		bean.deleteToken();
		assertThat(bean.getTokenList()).isNotNull().hasSize(--count);
		assertThat(bean.getTokenList()).isNotNull().hasSize(facadenFactory.getTokenFacade().count());
	}

	// Not real Testcases, Methods are just vor convention
	@Test
	void testGetterSetter() {
		bean.setClipboardURL(null);
		assertThat(bean.getClipboardURL()).isNull();
		bean.setName(null);
		assertThat(bean.getName()).isNull();
		bean.setTokenList(null);
		assertThat(bean.getTokenList()).isNull();
	}
}

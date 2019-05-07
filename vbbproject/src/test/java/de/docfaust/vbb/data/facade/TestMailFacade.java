package de.docfaust.vbb.data.facade;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

import de.docfaust.vbb.data.entity.Mail;
import de.docfaust.vbb.data.facades.MailFacade;
import de.docfaust.vbb.data.util.JpaBaseRolledBackTestCase;

public class TestMailFacade extends JpaBaseRolledBackTestCase {

	@Test
	public void test() {
		assertThat(new MailFacade()).isNotNull();
		Mail mail = new Mail();
		mail.setId(1);
		assertThat(mail.getId()).isEqualTo(1);
	}

	@Test
	public void testFindAll() {
		assertThat(facadenFactory.getMailFacade().findAll()).isNotEmpty();
		assertThat(facadenFactory.getMailFacade().count()).isEqualTo(2);
	}

	@Test
	public void testAddMail() {
		int count = facadenFactory.getMailFacade().count();
		Mail mail = new Mail();
		mail.setRecipient("du");
		mail.setSubject("subject");
		mail.setText("text2");
		mail.setAttempt(0);

		facadenFactory.getMailFacade().create(mail);

		LoggerFactory.getLogger(getClass()).info(mail.toString());
		assertThat(facadenFactory.getMailFacade().count()).isEqualTo(count + 1);
		List<Mail> list = facadenFactory.getMailFacade().findAll();

		Mail mail2 = list.get(count);
		assertThat(mail2.getRecipient()).isEqualTo("du");
		assertThat(mail2.getSubject()).isEqualTo("subject");
		assertThat(mail2.getText()).isEqualTo("text2");
		assertThat(mail2.getAttempt()).isEqualTo(0);
	}

	@Test
	public void testDeleteMail(){
		int count = facadenFactory.getMailFacade().count();
		Mail mail = new Mail();
		mail.setId(1);
		facadenFactory.getMailFacade().remove(mail);
		assertThat(facadenFactory.getMailFacade().count()).isEqualTo(count - 1);
	}
	
	@Test
	public void testDeleteMailNotFound(){
		int count = facadenFactory.getMailFacade().count();
		Mail mail = new Mail();
		mail.setId(404);
		facadenFactory.getMailFacade().remove(mail);
		assertThat(facadenFactory.getMailFacade().count()).isEqualTo(count);
	}
}

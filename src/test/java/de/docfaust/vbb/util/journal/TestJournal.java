package de.docfaust.vbb.util.journal;

import org.junit.jupiter.api.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import de.docfaust.vbb.data.entity.BusinessCase;
import de.docfaust.vbb.data.facades.JournalFacade;
import de.docfaust.vbb.data.util.JpaBaseRolledBackTestCase;
import de.docfaust.vbb.util.journal.impl.JournalDBWriter;

public class TestJournal extends JpaBaseRolledBackTestCase {

	@Test
	public void testWriteJournal() {
		JournalFacade journalFacade = facadenFactory.getJournalFacade();
		Journal journal = new JournalDBWriter(journalFacade);
		assertThat(journalFacade.count(), equalTo(1));
		journal.writeJournal(BusinessCase.CREATE_GAME, "Wer auch immer", "hat eine neues Spiel gebaut");
		assertThat(journalFacade.count(), equalTo(2));
	}

}

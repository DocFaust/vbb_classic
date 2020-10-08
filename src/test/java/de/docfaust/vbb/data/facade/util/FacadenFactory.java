package de.docfaust.vbb.data.facade.util;

import javax.persistence.EntityManager;

import de.docfaust.vbb.data.facades.BuchungFacade;
import de.docfaust.vbb.data.facades.ConfigFacade;
import de.docfaust.vbb.data.facades.GroupFacade;
import de.docfaust.vbb.data.facades.JournalFacade;
import de.docfaust.vbb.data.facades.MailFacade;
import de.docfaust.vbb.data.facades.SeasonFacade;
import de.docfaust.vbb.data.facades.SpielFacade;
import de.docfaust.vbb.data.facades.SpielerFacade;
import de.docfaust.vbb.data.facades.TokenFacade;
import de.docfaust.vbb.data.facades.UserFacade;

public class FacadenFactory {
	private BuchungFacade buchungFacade;
	private SpielerFacade spielerFacade;
	private SpielFacade spielFacade;
	private SeasonFacade seasonFacade;
	private UserFacade userFacade;
	private ConfigFacade configFacade;
	private MailFacade mailFacade;
	private GroupFacade groupFacade;
	private JournalFacade journalFacade;
	private TokenFacade tokenFacade;
	public FacadenFactory(final EntityManager em) {
		buchungFacade = createBuchungFacade(em);
		spielerFacade = createSpielerFacade(em);
		configFacade = createConfigFacade(em);
		seasonFacade = createSeasonFacade(em);
		spielFacade = createSpielFacade(em);
		userFacade = createUserFacade(em);
		mailFacade = createMailFacade(em);
		groupFacade = createGroupFacade(em);
		journalFacade = createJournalFacade(em);
		tokenFacade = createTokenFacade(em);
	}

	private TokenFacade createTokenFacade(EntityManager em) {
		return new TokenFacade(em);
	}

	private JournalFacade createJournalFacade(EntityManager em) {
		return new JournalFacade(em);
	}

	private GroupFacade createGroupFacade(EntityManager em) {
		return new GroupFacade(em);
	}

	private MailFacade createMailFacade(final EntityManager em) {
		return new MailFacade(em);
	}

	private BuchungFacade createBuchungFacade(final EntityManager em) {
		return new BuchungFacade(em);
	}

	private SpielFacade createSpielFacade(final EntityManager em) {
		return new SpielFacade(em);
	}

	private SpielerFacade createSpielerFacade(final EntityManager em) {
		return new SpielerFacade(em);
	}

	private SeasonFacade createSeasonFacade(final EntityManager em) {
		return new SeasonFacade(em);
	}

	private UserFacade createUserFacade(final EntityManager em) {
		return new UserFacade(em);
	}

	private ConfigFacade createConfigFacade(final EntityManager em) {
		return new ConfigFacade(em);
	}

	public BuchungFacade getBuchungFacade() {
		return buchungFacade;
	}

	public SpielerFacade getSpielerFacade() {
		return spielerFacade;
	}

	public SpielFacade getSpielFacade() {
		return spielFacade;
	}

	public SeasonFacade getSeasonFacade() {
		return seasonFacade;
	}

	public UserFacade getUserFacade() {
		return userFacade;
	}

	public ConfigFacade getConfigFacade() {
		return configFacade;
	}

	public MailFacade getMailFacade() {
		return mailFacade;
	}

	public GroupFacade getGroupFacade() {
		return groupFacade;
	}

	public JournalFacade getJournalFacade() {
		return journalFacade;
	}

	public TokenFacade getTokenFacade() {
		return tokenFacade;
	}
}

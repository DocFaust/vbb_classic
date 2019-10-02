package de.docfaust.vbb;

import javax.persistence.EntityManager;

import de.docfaust.vbb.data.facade.util.FacadenFactory;
import de.docfaust.vbb.service.BuchungService;
import de.docfaust.vbb.service.BuchungServiceImpl;
import de.docfaust.vbb.service.ConfigService;
import de.docfaust.vbb.service.ConfigServiceImpl;
import de.docfaust.vbb.service.CutOffService;
import de.docfaust.vbb.service.CutOffServiceImpl;
import de.docfaust.vbb.service.GroupService;
import de.docfaust.vbb.service.GroupServiceImpl;
import de.docfaust.vbb.service.MailService;
import de.docfaust.vbb.service.MailServiceImpl;
import de.docfaust.vbb.service.RegisterService;
import de.docfaust.vbb.service.RegisterServiceImpl;
import de.docfaust.vbb.service.SaldoService;
import de.docfaust.vbb.service.SaldoServiceImpl;
import de.docfaust.vbb.service.SeasonService;
import de.docfaust.vbb.service.SeasonServiceImpl;
import de.docfaust.vbb.service.SpielService;
import de.docfaust.vbb.service.SpielServiceImpl;
import de.docfaust.vbb.service.SpielerService;
import de.docfaust.vbb.service.SpielerServiceImpl;
import de.docfaust.vbb.service.TokenService;
import de.docfaust.vbb.service.TokenServiceImpl;
import de.docfaust.vbb.service.UserService;
import de.docfaust.vbb.service.UserServiceImpl;
import de.docfaust.vbb.util.configuration.MailConfigurationDBImpl;
import de.docfaust.vbb.util.journal.impl.JournalDBWriter;
import de.docfaust.vbb.util.templates.VelocityMailTemplates;
import de.docfaust.vbb.util.templates.VelocityRegisterTemplates;
import lombok.Getter;

@Getter
public class ServiceCreator {

	private FacadenFactory ff;
	
	private final BuchungService buchungService;
	private final SpielService spielService;
	private final SpielerService spielerService;
	private final SeasonService seasonService;
	private final ConfigService configService;
	private final MailService mailService;
	private final CutOffService cutOffService;
	private final GroupService groupService;
	private final UserService userService;
	private final RegisterService registerService;
	private final TokenService tokenService;
	private final SaldoService saldoService;
	
	
	public ServiceCreator(final EntityManager em) {
		this.ff = new FacadenFactory(em);
		this.buchungService = new BuchungServiceImpl(ff.getBuchungFacade());
		this.spielerService = new SpielerServiceImpl(ff.getSpielerFacade());
		this.seasonService = new SeasonServiceImpl(ff.getSeasonFacade());
		this.configService = new ConfigServiceImpl(new MailConfigurationDBImpl(em));
		this.groupService = new GroupServiceImpl(ff.getGroupFacade());
		this.userService = new UserServiceImpl(ff.getUserFacade());
		
		this.registerService = new RegisterServiceImpl(ff.getMailFacade(), userService, configService, groupService, new VelocityRegisterTemplates());
		this.mailService = new MailServiceImpl(ff.getMailFacade(), saldoService, configService, new VelocityMailTemplates());
		this.cutOffService = new CutOffServiceImpl(buchungService, spielService, spielerService, seasonService);
		this.spielService = new SpielServiceImpl(ff.getSpielFacade(), buchungService, spielerService, seasonService, mailService, new JournalDBWriter(ff.getJournalFacade()));

		// FIXME only one facade per service
		this.tokenService = new TokenServiceImpl(ff.getTokenFacade(), ff.getConfigFacade());
		// FIXME only one facade per service
		this.saldoService = new SaldoServiceImpl(ff.getBuchungFacade(), ff.getSpielerFacade());
		
	}
}


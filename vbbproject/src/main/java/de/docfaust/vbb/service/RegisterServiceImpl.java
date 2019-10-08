package de.docfaust.vbb.service;

import javax.ejb.EJB;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.docfaust.vbb.data.entity.Group;
import de.docfaust.vbb.data.entity.User;
import de.docfaust.vbb.data.facades.MailFacade;
import de.docfaust.vbb.util.RegistrationState;
import de.docfaust.vbb.util.templates.RegisterTemplates;
import de.docfaust.vbb.util.templates.VelocityRegisterTemplate;

/**
 * Implementation of the RegisterService.
 * @author wfa339
 *
 */
@Dependent
public class RegisterServiceImpl implements RegisterService {
	/**
	 * 
	 */
	private static final long serialVersionUID = -929357024656291724L;
	@EJB
	private MailFacade mailFacade;
	@Inject
	private Logger logger;
	@Inject
	private UserService userService;
	@Inject
	private ConfigService configService;
	@Inject
	private GroupService groupService;
	@Inject
	@VelocityRegisterTemplate
	private RegisterTemplates templates;

	/**
	 * @param mailFacade    from JUnit
	 * @param userService   from JUnit
	 * @param configService from JUnit
	 * @param groupService  from JUnit
	 * @param templates     from JUnit
	 */
	public RegisterServiceImpl(final MailFacade mailFacade, final UserService userService,
			final ConfigService configService, final GroupService groupService, final RegisterTemplates templates) {
		super();
		this.mailFacade = mailFacade;
		this.userService = userService;
		this.configService = configService;
		this.groupService = groupService;
		this.templates = templates;
		this.logger = LoggerFactory.getLogger(getClass());

	}

	/**
	 * 
	 */
	public RegisterServiceImpl() {
		super();
	}

	/**
	 * Verarbeitet die Registrierung.
	 * 
	 * @param regid  Registrierungsid.
	 * @param userid Benutzerid.
	 * @return ServerResponse.
	 */
	@Override
	public String processRegistration(final String regid, final String userid) {
		String serverResponse = null;
		if (userid != null) {
			logger.info("Registering user: {}", userid);
			User user = userService.findByUserName(userid);

			if (user == null) {
				// Keine Registrierung
				serverResponse = templates.getNotRegistered(userid);
				logger.warn("No corresponding user found");
			} else if (user.getRegid() == null || !user.getRegid().equals(regid)) {
				// Falsche Registrierung
				serverResponse = templates.getWrongID(user.getUserid());
				logger.warn("No, or incorrect RegID: {}", user.getRegid());
			} else {
				// Registrierung gefunden
				if (RegistrationState.PROOFED != user.getState() && user.getGroup() == null) {
					// User ist noch keiner Gruppe zugeordnet
					Group group = groupService.findByName("READER");
					user.setGroup(group);
					String domain = configService.getMailConfig().getDomain();
					serverResponse = templates.getOk(user.getUserid(), domain);
					logger.info("User wurde registriert");
				} else {
					// User bereits registriert
					serverResponse = templates.getYetRegistered(user.getUserid());
					logger.info("User war bereits registriert");
				}
				user.setState(RegistrationState.PROOFED);
				userService.saveUser(user);
				logger.info("Registrierung erfolgreich.");
			}
		} else {
			serverResponse = templates.getWrongRequest();
			logger.info("Erforderliche Parameter fehlen.");
		}
		return serverResponse;
	}
}

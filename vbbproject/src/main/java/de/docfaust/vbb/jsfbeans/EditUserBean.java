package de.docfaust.vbb.jsfbeans;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.docfaust.vbb.data.entity.Group;
import de.docfaust.vbb.data.entity.User;
import de.docfaust.vbb.service.VBBServices;
import de.docfaust.vbb.util.messages.UIMessages;
import de.docfaust.vbb.util.statusliste.Statusliste;

/**
 * Managed Bean für die Userverwaltung.
 * 
 * @author xhu1011
 *
 */
@ViewScoped
@Named
public class EditUserBean extends AbstractJSFBean {
	private static final long serialVersionUID = -2117558184786615638L;
	@Inject
	private Logger logger;

	private List<User> users = null;
	private User selectedUser;
	private List<Group> groups = null;
	private Group selectedGroup = null;

	/**
	 * Konstruktor ohne EJB Kontext.
	 * 
	 * @param services
	 *            Services
	 * @param uiMessages
	 *            UIMessages
	 */
	public EditUserBean(final VBBServices services, final UIMessages uiMessages) {
		super(services, uiMessages);
		logger = LoggerFactory.getLogger(getClass());
		init();
	}

	/**
	 * Konstruktor mit EJB Kontext.
	 * 
	 */
	public EditUserBean() {
		super();
	}

	/**
	 * Initialisiert das Bean mit den Werten aus der Datenbank.
	 */
	@PostConstruct
	public void init() {
		logger.debug("Hole Users");
		setUsers(getServices().getUsers());
		if (users != null && users.size() > 0) {
			setSelectedUser(users.get(0));
			setSelectedGroup(selectedUser.getGroup());
		}
		setGroups(getServices().getGroups());

	}

	public User getSelectedUser() {
		return selectedUser;
	}

	public void setSelectedUser(final User selectedUser) {
		this.selectedUser = selectedUser;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(final List<User> users) {
		this.users = users;
	}

	/**
	 * Löscht die ausgewählte Saison.
	 */
	public void delete() {
		getServices().deleteUser(getSelectedUser());
		init();
	}

	/**
	 * fügt eine Saison hinzu.
	 */
	public void addUser() {
		User user = new User();
		selectedUser = user;
	}

	/**
	 * Speichert den ausgewählten User.
	 */
	public void saveUser() {
		Statusliste statusliste = getServices().saveUser(getSelectedUser());
		showMessages(statusliste);
	}

	public List<Group> getGroups() {
		return groups;
	}

	public void setGroups(final List<Group> groups) {
		this.groups = groups;
	}

	public Group getSelectedGroup() {
		return selectedGroup;
	}

	public void setSelectedGroup(final Group selectedGroup) {
		this.selectedGroup = selectedGroup;
	}
}

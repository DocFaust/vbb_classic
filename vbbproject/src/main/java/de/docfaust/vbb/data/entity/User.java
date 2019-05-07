package de.docfaust.vbb.data.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import de.docfaust.vbb.util.RegistrationState;

/**
 * The persistent class for the USERS database table.
 * 
 */
@Entity
@Table(name = "USERS", indexes = { @Index(unique = true, name = "userid_idx", columnList = "userid") })
@NamedQuery(name = "User.findAll", query = "SELECT u FROM User u")
public class User extends AbstractEntity {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@NotNull
	@Column(unique = true, nullable = false)
	private String email;

	@NotNull
	private String password;

	@Column(unique = true)
	private String regid;

	@Enumerated(EnumType.STRING)
	private RegistrationState state;

	@Column(unique = true, nullable = false)
	private String userid;

	private String username;

	@ManyToOne(targetEntity = Group.class, fetch = FetchType.EAGER)
	private Group group;

	/**
	 * Leerer Konstruktor.
	 */
	public User() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(final int id) {
		this.id = id;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	public String getRegid() {
		return this.regid;
	}

	public void setRegid(final String regid) {
		this.regid = regid;
	}

	public RegistrationState getState() {
		return this.state;
	}

	public void setState(final RegistrationState state) {
		this.state = state;
	}

	public String getUserid() {
		return this.userid;
	}

	public void setUserid(final String userid) {
		this.userid = userid;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(final String username) {
		this.username = username;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(final Group group) {
		this.group = group;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(getClass().getSimpleName()).append("[");
		builder.append("userid=").append(userid).append(", ");
		builder.append("username=").append(username).append(", ");
		builder.append("email=").append(email).append(", ");
		builder.append("regid=").append(regid).append(", ");
		builder.append("state=").append(state).append(", ");
		if (group != null) {
			builder.append("Group=").append(group.getName());
		}
		builder.append("]");
		return builder.toString();
	}
}
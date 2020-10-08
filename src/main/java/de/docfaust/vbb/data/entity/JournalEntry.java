package de.docfaust.vbb.data.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the JOURNAL database table.
 * 
 */
@Entity
@Table(name = "JOURNAL")
public class JournalEntry extends AbstractEntity {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 9140238746458475051L;

	/**
	 * ID.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	/**
	 * Datum der Buchung.
	 */
	@Temporal(TemporalType.TIMESTAMP)
	private Date tms;

	/**
	 * Beschreibung.
	 */
	private String description;

	/**
	 * user.
	 */
	@Column(name = "USERID")
	private String userId;

	/**
	 * GV.
	 */
	@Column(name = "BUSINESS_CASE")
	@Enumerated(EnumType.STRING)
	private BusinessCase businessCase;

	/**
	 * Leerer Konstruktor.
	 */
	public JournalEntry() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(final int id) {
		this.id = id;
	}

	public Date getTms() {
		return tms;
	}

	public void setTms(final Date tms) {
		this.tms = tms;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}


	public String getUserId() {
		return userId;
	}

	public void setUserId(final String userId) {
		this.userId = userId;
	}

	public BusinessCase getBusinessCase() {
		return businessCase;
	}

	public void setBusinessCase(final BusinessCase businessCase) {
		this.businessCase = businessCase;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(this.getClass().getSimpleName()).append(": [");
		builder.append("id=").append(id);
		builder.append("]");

		return builder.toString();
	}
}
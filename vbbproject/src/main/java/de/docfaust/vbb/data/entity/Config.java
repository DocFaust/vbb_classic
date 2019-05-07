package de.docfaust.vbb.data.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the CONFIG database table.
 * 
 */
@Entity
@NamedQuery(name = "Config.findAll", query = "SELECT c FROM Config c")
@Table(name = "CONFIG", indexes = { @Index(unique = true, name = "key_idx", columnList = "configkey") })
public class Config extends AbstractEntity {
	/**
	 * Spaltenlänge 1024.
	 */
	private static final int COL_1024 = 1024;

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(unique = true, nullable = false)
	private String configkey;

	@Column(length = COL_1024)
	private String configvalue;

	/**
	 * Leerer Konstruktor.
	 */
	public Config() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(final int id) {
		this.id = id;
	}

	public String getConfigkey() {
		return this.configkey;
	}

	public void setConfigkey(final String configkey) {
		this.configkey = configkey;
	}

	public String getConfigvalue() {
		return this.configvalue;
	}

	public void setConfigvalue(final String configvalue) {
		this.configvalue = configvalue;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(getClass().getSimpleName()).append(": [");
		builder.append("id=").append(id).append(", ");
		builder.append("configkey=").append(configkey).append(", ");
		builder.append("configvalue=").append(configvalue).append("]");
		return builder.toString();
	}

}
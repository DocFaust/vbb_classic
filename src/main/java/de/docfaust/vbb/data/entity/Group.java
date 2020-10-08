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
 * The persistent class for the GROUPS database table.
 * 
 */
@Entity
@Table(name = "ROLES", indexes = { 
		@Index(unique = true, name = "name_idx", columnList = "name") 
		})

@NamedQuery(name = "Group.findAll", query = "SELECT g FROM Group g")
public class Group extends AbstractEntity {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String description;

	@Column(unique = true, nullable = false)
	private String name;

	/**
	 * Leerer Konstruktor.
	 */
	public Group() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(final int id) {
		this.id = id;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(this.getClass().getSimpleName()).append(": [");
		builder.append("id=").append(id);
		builder.append(", ");
		builder.append("name=").append(name);
		builder.append(", ");
		builder.append("description=").append(description);
		builder.append("]");
		return builder.toString();
	}
}
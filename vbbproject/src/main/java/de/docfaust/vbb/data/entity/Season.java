package de.docfaust.vbb.data.entity;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 * The persistent class for the SEASON database table.
 * 
 */
@Entity
@NamedQuery(name = "Season.findAll", query = "SELECT s FROM Season s")
@Table(name = "SEASON", indexes = { 
		@Index(unique = true, name = "start_end_idx", columnList = "startdate, enddate")
		})
public class Season extends AbstractEntity {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String description;

	@Column(nullable = false)
	@Temporal(TemporalType.DATE)
	@NotNull
	private Date enddate;

	private BigDecimal price;

	@Column(nullable = false)
	@Temporal(TemporalType.DATE)
	@NotNull
	private Date startdate;

	// bi-directional many-to-one association to Spiel
	@OneToMany(mappedBy = "season", fetch = FetchType.LAZY)
	private List<Spiel> spiele = new ArrayList<>();

	/**
	 * Leerer Konstruktor.
	 */
	public Season() {
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

	public Date getEnddate() {
		return this.enddate;
	}

	public void setEnddate(final Date enddate) {
		this.enddate = enddate;
	}

	public BigDecimal getPrice() {
		return this.price;
	}

	public void setPrice(final BigDecimal price) {
		this.price = price;
	}

	public Date getStartdate() {
		return this.startdate;
	}

	public void setStartdate(final Date startdate) {
		this.startdate = startdate;
	}

	public List<Spiel> getSpiele() {
		return this.spiele;
	}

	public void setSpiele(final List<Spiel> spiele) {
		this.spiele = spiele;
	}

	/**
	 * Fügt ein Spiel hinzu.
	 * 
	 * @param spiel
	 *            Neues Spiel
	 * @return Ergänztes Spiel
	 */
	public Spiel addSpiel(final Spiel spiel) {
		getSpiele().add(spiel);
		spiel.setSeason(this);

		return spiel;
	}

	/**
	 * Löscht das Spiel.
	 * 
	 * @param spiel
	 *            zu Löschendes Spiel.
	 * @return gelöschtes Spiel
	 */
	public Spiel removeSpiel(final Spiel spiel) {
		getSpiele().remove(spiel);
		spiel.setSeason(null);

		return spiel;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(this.getClass().getSimpleName()).append(": [");
		builder.append("id=").append(id).append(", ");
		builder.append("description=").append(description).append(", ");
		builder.append("startdate=");
		if (startdate != null) {
			builder.append(new SimpleDateFormat("dd.MM.yyyy").format(startdate));
		} else {
			builder.append("null");
		}
		builder.append(", ");
		builder.append("enddate=");
		if (enddate != null) {
			builder.append(new SimpleDateFormat("dd.MM.yyyy").format(enddate));
		} else {
			builder.append("null");
		}
		builder.append(", ");
		builder.append("price=").append(price).append("]");
		return builder.toString();
	}
}
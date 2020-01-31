package de.docfaust.vbb.data.entity;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the BUCHUNG database table.
 * 
 */
@Entity
@NamedQuery(name = "Buchung.findAll", query = "SELECT b FROM Buchung b")
@Table(name = "BUCHUNG", indexes = { @Index(unique = false, name = "buchung_datum_idx", columnList = "datum") })
public class Buchung extends AbstractEntity {

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
	private Date datum;

	/**
	 * Datum der Inserttimestamp.
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ITMS")
	private Date insertTimestamp;

	/**
	 * Datum der Updatetimestamp.
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UTMS")
	private Date lastUpdate;

	/**
	 * Beschreibung.
	 */
	private String description;

	/**
	 * Betrag.
	 */
	// CHECKSTYLE.OFF: MagicNumber
	@Column(precision = 4, scale = 2)
	private BigDecimal price = BigDecimal.ZERO;
	// CHECKSTYLE.ON

	/**
	 * Zugehöriges Spiel.
	 */
	// bi-directional many-to-one association to Spiel
	@ManyToOne(targetEntity = Spiel.class)
	private Spiel spiel;

	/**
	 * Zugehöriger Spieler.
	 */
	// bi-directional many-to-one association to Spieler
	@ManyToOne(targetEntity = Spieler.class)
	private Spieler spieler;

	/**
	 * Leerer Konstruktor.
	 */
	public Buchung() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(final int id) {
		this.id = id;
	}

	public Date getDatum() {
		return this.datum;
	}

	public void setDatum(final Date datum) {
		this.datum = datum;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public BigDecimal getPrice() {
		return this.price;
	}

	public void setPrice(final BigDecimal price) {
		this.price = price;
	}

	public Spiel getSpiel() {
		return this.spiel;
	}

	public void setSpiel(final Spiel spiel) {
		this.spiel = spiel;
	}

	public Spieler getSpieler() {
		return this.spieler;
	}

	public void setSpieler(final Spieler spieler) {
		this.spieler = spieler;
	}

	/**
	 * creates an insertTimestamp.
	 */
	@PrePersist
	public void insertTimestamp() {
		insertTimestamp = new Date();
	}

	/**
	 * creates an updateTimestamp.
	 */
	@PreUpdate
	public void updateTimestamp() {
		lastUpdate = new Date();
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(this.getClass().getSimpleName()).append(": [");
		builder.append("id=").append(id);
		builder.append(", datum=");
		if (datum == null) {
			builder.append("null");
		} else {
			builder.append(new SimpleDateFormat("dd.MM.yyyy HH:mm:ss,SSS").format(datum));
		}
		builder.append(", description=").append(description);
		builder.append(", price=").append(price).append("]");

		return builder.toString();
	}
}
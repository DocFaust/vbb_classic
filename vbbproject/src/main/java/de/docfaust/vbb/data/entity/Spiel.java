package de.docfaust.vbb.data.entity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the SPIEL database table.
 * 
 */
@Entity
@Table(name = "SPIEL", indexes = { 
		@Index(unique = false, name = "datum_idx", columnList = "datum")
		})
@NamedQuery(name = "Spiel.findAll", query = "SELECT s FROM Spiel s")
public class Spiel extends AbstractEntity {
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * ID.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	/**
	 * Datum des Spiels.
	 */
	@Temporal(TemporalType.DATE)
	private Date datum;

	/**
	 * zugehörige Buchungen.
	 */
	// bi-directional many-to-one association to Buchung
	@OneToMany(mappedBy = "spiel", fetch = FetchType.EAGER, cascade = { CascadeType.REMOVE }, orphanRemoval = true)
	private List<Buchung> buchungen = new ArrayList<>();

	/**
	 * Zugehörige Saison.
	 */
	// bi-directional many-to-one association to Season
	@ManyToOne(targetEntity = Season.class, optional = false)
	private Season season;

	/**
	 * Leerer Konstruktor.
	 */
	public Spiel() {
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

	public List<Buchung> getBuchungen() {
		return this.buchungen;
	}

	public void setBuchungen(final List<Buchung> buchungen) {
		this.buchungen = buchungen;
	}

	/**
	 * Fügt eine neue Buchung hinzu.
	 * 
	 * @param buchung
	 *            Neue Buchung
	 * @return Befüllte Buchung
	 */
	public Buchung addBuchung(final Buchung buchung) {
		getBuchungen().add(buchung);
		buchung.setSpiel(this);
		return buchung;
	}

	/**
	 * Löscht eine Buchung.
	 * 
	 * @param buchung
	 *            zu Löschende Buchung.
	 * @return gelöschte Buchung
	 */
	public Buchung removeBuchung(final Buchung buchung) {
		getBuchungen().remove(buchung);
		buchung.setSpiel(null);
		return buchung;
	}

	public Season getSeason() {
		return this.season;
	}

	public void setSeason(final Season season) {
		this.season = season;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(this.getClass().getSimpleName()).append(": [");
		builder.append("id=").append(id).append(", ");
		builder.append("datum=");
		if (datum != null) {
			builder.append(new SimpleDateFormat("dd.MM.yyyy").format(datum));
		} else {
			builder.append("null");
		}
		if (season != null) {
			builder.append(", ");
			builder.append(season.toString());
		}
		builder.append("]");
		return builder.toString();
	}
}
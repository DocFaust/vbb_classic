package de.docfaust.vbb.data.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * The persistent class for the SPIELER database table.
 * 
 */
@Entity
@Table(name = "SPIELER")
@NamedQuery(name = "Spieler.findAll", query = "SELECT s FROM Spieler s")
public class Spieler extends AbstractEntity {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String name;
	
	private String email;
	
	private int activityLevel;
	
	// bi-directional many-to-one association to Buchung
	@OneToMany(mappedBy = "spieler", fetch = FetchType.EAGER/*, cascade = {
			CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH
	}*/)
	private List<Buchung> buchungen = new ArrayList<>();

	@Transient
	private boolean anwesend;

	@Transient
	private boolean bezahlt;

	/**
	 * Leerer Konstruktor.
	 */
	public Spieler() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(final int id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public int getActivityLevel() {
		return activityLevel;
	}

	public void setActivityLevel(final int activityLevel) {
		this.activityLevel = activityLevel;
	}

	public List<Buchung> getBuchungen() {
		return this.buchungen;
	}

	/**
	 * Fügt eine Buchung hinzu.
	 * 
	 * @param buchung
	 *            Neue Buchung
	 * @return Ergänzte Buchung
	 */
	public Buchung addBuchung(final Buchung buchung) {
		getBuchungen().add(buchung);
		buchung.setSpieler(this);

		return buchung;
	}

	/**
	 * Löscht eine Buchung.
	 * 
	 * @param buchung
	 *            zu löschende Buchung
	 * @return Gelöschte Buchung
	 */
	public Buchung removeBuchung(final Buchung buchung) {
		getBuchungen().remove(buchung);
		buchung.setSpieler(null);

		return buchung;
	}

	public boolean isAnwesend() {
		return anwesend;
	}

	public void setAnwesend(final boolean anwesend) {
		this.anwesend = anwesend;
	}

	public boolean isBezahlt() {
		return bezahlt;
	}

	public void setBezahlt(final boolean bezahlt) {
		this.bezahlt = bezahlt;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(this.getClass().getSimpleName()).append(": [");
		builder.append("id=").append(id).append(", ");
		builder.append("name=").append(getName()).append(",");
		builder.append("email=").append(getEmail()).append("]");

		return builder.toString();
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

}

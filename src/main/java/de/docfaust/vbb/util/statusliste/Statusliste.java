package de.docfaust.vbb.util.statusliste;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import de.docfaust.vbb.util.messages.MessageConstants;

/**
 * Liste der Status, die aufgetreten sind.
 * 
 * @author xhu1011
 *
 */
public class Statusliste implements Iterable<Status> {
	/**
	 * Statusliste.
	 */
	private List<Status> statusliste = new ArrayList<Status>();

	/**
	 * Fügt einen Status der Liste hinzu.
	 * 
	 * @param status
	 *            hinzuzufügender Status
	 * @return liefert sich selbst zurück (functional)
	 */
	public Statusliste addStatus(final Status status) {
		statusliste.add(status);
		return this;
	}

	/**
	 * Erstellt einen Status und fügt ihn hinzu.
	 * 
	 * @param code
	 *            MessageCode
	 * @param zusatzInfo
	 *            zusätzliche Info
	 * @return liefert sich selbst zurück (functional)
	 */
	public Statusliste addStatus(final MessageConstants code, final Object... zusatzInfo) {
		Status status = new Status();
		status.setCode(code);
		status.setZusatzInfo(zusatzInfo);
		addStatus(status);
		return this;
	}

	/**
	 * Indikator, ob ein Fehlerstatus aufgetreten ist.
	 * 
	 * @return false, wenn ein fehlerhafter Status in der Liste ist
	 */
	public boolean booleanValue() {
		for (Status status : statusliste) {
			if (!status.booleanValue()) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Indikator, ob ein bestimmter Fehlerstatus aufgetreten ist.
	 * 
	 * @param message
	 *            StatusCode nach dem gesucht wird
	 * @return true, wenn der Status in der Liste ist
	 */
	public boolean hasStatus(final MessageConstants message) {
		for (Status status : statusliste) {
			if (status.getCode().equals(message)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Iterator<Status> iterator() {
		return statusliste.iterator();
	}

	/**
	 * Fügt eine ganze Statusliste zur aktuellen hinzu.
	 * 
	 * @param liste
	 *            hinzuzufügende Liste
	 * @return liefert sich selbst zurück (functional)
	 */
	public Statusliste addStatusliste(final Statusliste liste) {
		statusliste.addAll(liste.statusliste);
		return this;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(getClass().getSimpleName()).append("[");
		Iterator<Status> iterator = iterator();
		while (iterator.hasNext()) {
			Status status = iterator.next();
			builder.append(status.toString());
			if (iterator.hasNext()) {
				builder.append(", ");
			} else {
				builder.append("]");
			}
		}
		builder.append("]");
		return builder.toString();
	}

	/**
	 * funcionals.
	 * @return liefert sich selbst zurück (functional)
	 */
	public static Statusliste create() {
		return new Statusliste();
	}
}

package de.docfaust.vbb.data.entity;

import java.io.Serializable;

/**
 * Abstrakte Entität, die equals und hashcode anbietet.
 * 
 * @author xhu1011
 *
 */
public abstract class AbstractEntity implements IEntity, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5773823858761770921L;

	@Override
	public boolean equals(final Object other) {
		if (other instanceof AbstractEntity) {
			return getId() == ((AbstractEntity) other).getId();
		} else {
			return other == this;
		}
	}

	@Override
	public int hashCode() {
		return this.getClass().hashCode() + getId();
	}
}

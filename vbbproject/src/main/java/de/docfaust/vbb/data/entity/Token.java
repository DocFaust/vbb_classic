package de.docfaust.vbb.data.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
public class Token extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6640705827861720816L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String token;
	
	@Enumerated(EnumType.STRING)
	private ValidityState state;
}

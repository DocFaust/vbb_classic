package de.docfaust.vbb.data.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Entity for the Token.
 * @author wfa339
 *
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor(staticName = "of")
@NoArgsConstructor
@Table(name = "TOKEN")
@Entity
public class Token extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6640705827861720816L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@NonNull
	private String name;
	@NonNull
	private String token;
	
	@NonNull
	@Enumerated(EnumType.STRING)
	private ValidityState state;
}

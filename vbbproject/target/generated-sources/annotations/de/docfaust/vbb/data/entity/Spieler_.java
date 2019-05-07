package de.docfaust.vbb.data.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Spieler.class)
public abstract class Spieler_ {

	public static volatile SingularAttribute<Spieler, String> name;
	public static volatile SingularAttribute<Spieler, Integer> id;
	public static volatile SingularAttribute<Spieler, String> email;
	public static volatile SingularAttribute<Spieler, Integer> activityLevel;
	public static volatile ListAttribute<Spieler, Buchung> buchungen;

}


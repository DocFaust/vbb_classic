package de.docfaust.vbb.data.entity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Spiel.class)
public abstract class Spiel_ {

	public static volatile SingularAttribute<Spiel, Date> datum;
	public static volatile SingularAttribute<Spiel, Season> season;
	public static volatile SingularAttribute<Spiel, Integer> id;
	public static volatile ListAttribute<Spiel, Buchung> buchungen;

	public static final String DATUM = "datum";
	public static final String SEASON = "season";
	public static final String ID = "id";
	public static final String BUCHUNGEN = "buchungen";

}


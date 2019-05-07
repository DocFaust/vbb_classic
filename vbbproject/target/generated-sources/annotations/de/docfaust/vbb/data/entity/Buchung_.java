package de.docfaust.vbb.data.entity;

import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Buchung.class)
public abstract class Buchung_ {

	public static volatile SingularAttribute<Buchung, Date> datum;
	public static volatile SingularAttribute<Buchung, Spieler> spieler;
	public static volatile SingularAttribute<Buchung, BigDecimal> price;
	public static volatile SingularAttribute<Buchung, Date> lastUpdate;
	public static volatile SingularAttribute<Buchung, String> description;
	public static volatile SingularAttribute<Buchung, Date> insertTimestamp;
	public static volatile SingularAttribute<Buchung, Spiel> spiel;
	public static volatile SingularAttribute<Buchung, Integer> id;

}


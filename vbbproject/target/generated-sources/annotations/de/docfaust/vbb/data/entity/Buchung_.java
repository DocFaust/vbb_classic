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

	public static final String DATUM = "datum";
	public static final String SPIELER = "spieler";
	public static final String PRICE = "price";
	public static final String LAST_UPDATE = "lastUpdate";
	public static final String DESCRIPTION = "description";
	public static final String INSERT_TIMESTAMP = "insertTimestamp";
	public static final String SPIEL = "spiel";
	public static final String ID = "id";

}


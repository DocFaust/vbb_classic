package de.docfaust.vbb.data.entity;

import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Season.class)
public abstract class Season_ {

	public static volatile SingularAttribute<Season, Date> enddate;
	public static volatile SingularAttribute<Season, BigDecimal> price;
	public static volatile SingularAttribute<Season, String> description;
	public static volatile ListAttribute<Season, Spiel> spiele;
	public static volatile SingularAttribute<Season, Integer> id;
	public static volatile SingularAttribute<Season, Date> startdate;

	public static final String ENDDATE = "enddate";
	public static final String PRICE = "price";
	public static final String DESCRIPTION = "description";
	public static final String SPIELE = "spiele";
	public static final String ID = "id";
	public static final String STARTDATE = "startdate";

}


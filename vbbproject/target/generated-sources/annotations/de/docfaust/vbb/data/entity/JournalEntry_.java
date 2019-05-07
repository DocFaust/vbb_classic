package de.docfaust.vbb.data.entity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(JournalEntry.class)
public abstract class JournalEntry_ {

	public static volatile SingularAttribute<JournalEntry, String> description;
	public static volatile SingularAttribute<JournalEntry, Integer> id;
	public static volatile SingularAttribute<JournalEntry, Date> tms;
	public static volatile SingularAttribute<JournalEntry, String> userId;
	public static volatile SingularAttribute<JournalEntry, BusinessCase> businessCase;

}


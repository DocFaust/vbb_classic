package de.docfaust.vbb.data.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Mail.class)
public abstract class Mail_ {

	public static volatile SingularAttribute<Mail, String> subject;
	public static volatile SingularAttribute<Mail, String> recipient;
	public static volatile SingularAttribute<Mail, Integer> id;
	public static volatile SingularAttribute<Mail, String> text;
	public static volatile SingularAttribute<Mail, Integer> attempt;

	public static final String SUBJECT = "subject";
	public static final String RECIPIENT = "recipient";
	public static final String ID = "id";
	public static final String TEXT = "text";
	public static final String ATTEMPT = "attempt";

}


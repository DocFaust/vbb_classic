package de.docfaust.vbb.data.entity;

import de.docfaust.vbb.util.RegistrationState;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(User.class)
public abstract class User_ {

	public static volatile SingularAttribute<User, String> password;
	public static volatile SingularAttribute<User, String> regid;
	public static volatile SingularAttribute<User, Integer> id;
	public static volatile SingularAttribute<User, RegistrationState> state;
	public static volatile SingularAttribute<User, String> userid;
	public static volatile SingularAttribute<User, String> email;
	public static volatile SingularAttribute<User, String> username;
	public static volatile SingularAttribute<User, Group> group;

}


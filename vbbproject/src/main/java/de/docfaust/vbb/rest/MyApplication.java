package de.docfaust.vbb.rest;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/rest")
public class MyApplication extends Application {
	
	
    @Override
    public Set<Class<?>> getClasses() {
        final Set<Class<?>> classes = new HashSet<Class<?>>();
        classes.add(VBBRestImpl.class);
        classes.add(CharsetResponseFilter.class);
        return classes;
    }
}
package controllers;

import controllers.Weight.WeightController;
import controllers.security.AuthenticationFilter;
import controllers.security.AuthorizationFilter;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

//Defines the base URI for all resource URIs.
@ApplicationPath("/rest")
//The java class declares root resource and provider classes
public class MyApplication extends Application {
    //The method returns a non-empty collection with classes, that must be included in the published JAX-RS application


    public MyApplication() {
        WeightController wc = new WeightController();
        Thread T = new Thread(wc,"lol");
        T.start();
    }

    @Override
    public Set<Class<?>> getClasses() {
        HashSet h = new HashSet<Class<?>>();
        h.add( AuthenticationFilter.class );
        h.add( AuthorizationFilter.class );
        h.add( SessionService.class );
        h.add( BrugerService.class );
        h.add( RaavareService.class );
        h.add( ReceptService.class );
        h.add( ReceptkomponentService.class );
        h.add( RaavarebatchService.class );
        h.add( ProduktbatchService.class );
        h.add( ProduktbatchkomponentService.class );
        return h;
    }
}
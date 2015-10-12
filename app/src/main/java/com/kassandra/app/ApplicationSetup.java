package com.kassandra.app;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.ServletModule;
import com.kassandra.repository.RepositoryConfigModule;
import com.kassandra.rest.CompilerResource;
import com.kassandra.rest.DummyRestResource;
import com.kassandra.rest.ResponseCorsFilter;
import com.kassandra.rest.UserResource;

public class ApplicationSetup extends GuiceServletContextListener {
    @Override
    protected Injector getInjector() {
        return Guice.createInjector(new ServletModule() {

            @Override
            protected void configureServlets() {
                super.configureServlets();

                ResourceConfig resourceConfig = new ResourceConfig(UserResource.class,
                        DummyRestResource.class, CompilerResource.class);
                for (Class<?> resource : resourceConfig.getClasses()) {
                    bind(resource);
                }

                // TODO broken... need to be replaced with guice one.
                serve("/*").with(new ServletContainer(resourceConfig));

                filter("/*").through(ResponseCorsFilter.class);
            }
            // register all the guice modules here ...if you want to inject stuff :)
        }, new ConfigModule(), new RepositoryConfigModule());

    }
}

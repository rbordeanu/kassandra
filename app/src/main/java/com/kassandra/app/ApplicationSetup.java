package com.kassandra.app;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Scopes;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.ServletModule;
import com.kassandra.rest.ResponseCorsFilter;
import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.api.core.ResourceConfig;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;

public class ApplicationSetup extends GuiceServletContextListener {

    @Override
    protected Injector getInjector() {
        return Guice.createInjector(new ServletModule() {

            @Override
            protected void configureServlets() {

                super.configureServlets();

                ResourceConfig resourceConfig = new PackagesResourceConfig("com.kassandra.rest");
                for (Class<?> resource : resourceConfig.getClasses()) {
                    bind(resource);
                }

                bind(JacksonJsonProvider.class).in(Scopes.SINGLETON);

                serve("/app/*").with(GuiceContainer.class);

                filter("/app/*").through(ResponseCorsFilter.class);
            }
        }, new ConfigModule());

    }
}

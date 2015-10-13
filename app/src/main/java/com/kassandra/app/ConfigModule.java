package com.kassandra.app;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

public class ConfigModule extends AbstractModule {
    @Override
    protected void configure() {
        // bind stuff here
        bind(Application.class).in(Scopes.SINGLETON);
        // bind(DummyService.class).to(DummyServiceImpl.class);
    }
}

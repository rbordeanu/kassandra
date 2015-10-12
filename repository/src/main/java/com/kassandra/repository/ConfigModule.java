package com.kassandra.repository;

import com.google.inject.AbstractModule;
import com.kassandra.repository.config.RepositoryConfigurationProvider;

/**
 * Created by aAlex on 10/9/2015.
 */
public class ConfigModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(RepositoryConfigurationProvider.class);
    }
}

package com.kassandra.repository.config;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigBeanFactory;
import com.typesafe.config.ConfigFactory;

@Singleton
public class RepositoryConfigurationProvider {
    private static RepositoryConfiguration repositoryConfiguration;

    @Inject
    public RepositoryConfigurationProvider() {
        Config configSource = ConfigFactory.load("com.kassandra.repository");
        repositoryConfiguration = ConfigBeanFactory.create(configSource.getConfig("connection"),
                RepositoryConfiguration.class);
    }

    public static RepositoryConfiguration getConfig() {
        return repositoryConfiguration;
    }

}

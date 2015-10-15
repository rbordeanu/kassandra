package com.kassandra.repository.config;

import org.springframework.stereotype.Component;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigBeanFactory;
import com.typesafe.config.ConfigFactory;

@Component
public class RepositoryConfigurationProvider {
    private static RepositoryConfiguration repositoryConfiguration;

    public RepositoryConfigurationProvider() {
        Config configSource = ConfigFactory.load("com.kassandra.repository");
        repositoryConfiguration = ConfigBeanFactory
                .create(configSource.getConfig("connection"), RepositoryConfiguration.class);
    }

    public static RepositoryConfiguration getConfig() {
        return repositoryConfiguration;
    }

}

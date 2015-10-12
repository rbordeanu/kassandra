package com.kassandra.repository.impl;

import com.google.inject.Inject;
import com.kassandra.repository.IMongoDbClient;
import com.kassandra.repository.IMongoDbProvider;
import com.kassandra.repository.config.RepositoryConfiguration;
import com.kassandra.repository.config.RepositoryConfigurationProvider;
import com.mongodb.MongoClient;

/**
 * Created by aAlex on 10/8/2015.
 */
public class MongoDbProvider implements IMongoDbProvider {
    private final RepositoryConfiguration configuration;

    @Inject
    MongoDbProvider(RepositoryConfigurationProvider provider) {
        this.configuration = provider.getConfig();
    }

    public IMongoDbClient create(String collection) {
        MongoClient client = new MongoClient(configuration.getHost(), configuration.getPort());
        return new MongoDbClient(client.getDatabase("kassandra").getCollection(collection));
    }
}

package com.kassandra.repository;

import com.google.inject.AbstractModule;
import com.kassandra.repository.impl.MongoDbProvider;
import com.kassandra.repository.impl.UserRepository;

public class RepositoryConfigModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(IMongoDbProvider.class).to(MongoDbProvider.class);
        bind(IUserRepository.class).to(UserRepository.class);
    }
}

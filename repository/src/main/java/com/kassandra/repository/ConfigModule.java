package com.kassandra.repository;

import org.javabits.yar.guice.AbstractRegistryModule;

import com.kassandra.repository.impl.MongoDbProvider;
import com.kassandra.repository.impl.UserRepository;

public class ConfigModule extends AbstractRegistryModule {
    @Override
    protected void configureRegistry() {
        register(IMongoDbProvider.class).to(MongoDbProvider.class);
        register(IUserRepository.class).to(UserRepository.class);
    }
}

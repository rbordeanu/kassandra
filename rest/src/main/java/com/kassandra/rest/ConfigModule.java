package com.kassandra.rest;

import org.javabits.yar.guice.AbstractRegistryModule;

import com.kassandra.repository.IUserRepository;

public class ConfigModule extends AbstractRegistryModule {
    @Override
    protected void configureRegistry() {
        bind(IUserRepository.class).toRegistry();
    }
}
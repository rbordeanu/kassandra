package com.kassandra.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.kassandra.repository.config.RepositoryConfigurationProvider;
import com.kassandra.repository.impl.MongoDbProvider;
import com.kassandra.repository.impl.UserRepository;

@Configuration
@ComponentScan(value = { "com.kassandra" })
public class SpringConfig {
    @Bean
    public RepositoryConfigurationProvider getRepoProvider() {
        return new RepositoryConfigurationProvider();
    }

    @Bean
    public MongoDbProvider getMongoDbProvier() {
        return new MongoDbProvider(getRepoProvider());
    }

    @Bean
    public UserRepository getUserRepository() {
        return new UserRepository(getMongoDbProvier());
    }

 /*   @Bean
    public TestService getTestService(){
        return new TestService();
    }*/
}

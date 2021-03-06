package com.kassandra.app.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kassandra.repository.config.RepositoryConfigurationProvider;
import com.kassandra.repository.impl.MongoDbProvider;
import com.kassandra.repository.impl.UserRepository;

@Configuration
@ComponentScan(value = { "com.kassandra" })
@EnableWebMvc
public class SpringConfig extends WebMvcConfigurerAdapter {
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        final MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        converter.setObjectMapper(objectMapper);
        converters.add(converter);
        super.configureMessageConverters(converters);
    }

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

    /*
     * @Bean public TestService getTestService(){ return new TestService(); }
     */
}

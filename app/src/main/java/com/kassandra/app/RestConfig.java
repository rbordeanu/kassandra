package com.kassandra.app;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.kassandra.rest.bean.TestBean;

@Configuration
@ComponentScan(value = {"com.kassandra.rest" })
public class RestConfig {

    @Bean
    public TestBean getTestBean(){
        return new TestBean();
    }
}

package com.application.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.application.api.service.UserServiceImpl;

@Configuration
public class Config {

    @Bean
    public UserServiceImpl service() {
        return new UserServiceImpl();
    }
}

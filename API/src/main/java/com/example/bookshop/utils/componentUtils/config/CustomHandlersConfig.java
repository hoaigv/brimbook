package com.example.bookshop.utils.componentUtils.config;

import com.example.bookshop.authentication.config.RestAccessDeniedHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomHandlersConfig {
    @Bean
    public RestAccessDeniedHandler restAccessDeniedHandler() {
        return new RestAccessDeniedHandler();
    }
}

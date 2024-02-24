package com.rdi.ecommerce.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public ObjectMapper createObjectMapperBean() {
        return new ObjectMapper();
    }

    @Bean
    public ModelMapper createModelMapperBean() {
        return new ModelMapper();
    }
}

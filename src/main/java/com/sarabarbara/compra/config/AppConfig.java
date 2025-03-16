package com.sarabarbara.compra.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

/**
 * AppConfig class
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 15/03/2025
 */

@Configuration
public class AppConfig {

    /**
     * The BigDecimal Bean
     *
     * @return BigDecimal
     */

    @Bean
    public BigDecimal bigDecimal() {

        return BigDecimal.ZERO;
    }

    /**
     * The ObjectMapper Bean
     *
     * @return ObjectMapper
     */

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        return objectMapper;
    }
}

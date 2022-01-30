package com.cydrag.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.yml")
public class Config {

    @Value("${client.basiq.api-key}")
    private String basiqApiKey;

    public String getBasiqApiKey() {
        return basiqApiKey;
    }
}

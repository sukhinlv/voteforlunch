package ru.jsft.voteforlunch.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

@Configuration
public class AppConfig {

    @Autowired
    void configureAndStoreObjectMapper(ObjectMapper objectMapper) {
        objectMapper.registerModule(new Hibernate5Module());
    }

    @Bean
    public Clock clock() {
        return Clock.systemDefaultZone();
    }
}

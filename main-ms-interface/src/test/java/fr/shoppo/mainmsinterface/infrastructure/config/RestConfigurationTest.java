package fr.shoppo.mainmsinterface.infrastructure.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Configuration;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class RestConfigurationTest {

    private Class<RestConfiguration> configRestClass;

    @BeforeEach
    void setUp(){
        configRestClass = RestConfiguration.class;
    }

    @Test
    void configurationAnnotation(){
        assertNotNull(configRestClass.getAnnotation(Configuration.class));
    }

}

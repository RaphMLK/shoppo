package fr.shoppo.ms_commerce.infrastructure.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.assertSame;

class RestConfigurationTest {

    RestConfiguration restConfiguration;

    @BeforeEach
    void setup(){
        restConfiguration = new RestConfiguration();
    }

    @Test
    void should_get_default_rest_template(){
        assertSame(RestTemplate.class,restConfiguration.restTemplate().getClass());
    }

    @Test
    void should_get_secured_rest_template() throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        assertSame(RestTemplate.class,restConfiguration.secureRestTemplate().getClass());
    }

}
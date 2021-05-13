package fr.shoppo.ms_commerce.infrastructure.config;

import fr.shoppo.ms_commerce.infrastructure.service.PasswordManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.security.SecureRandom;

import static org.junit.jupiter.api.Assertions.assertSame;

class PasswordManagerConfigurationTest {

    PasswordManagerConfiguration passwordManagerConfiguration;

    @BeforeEach
    void setup(){
        passwordManagerConfiguration = new PasswordManagerConfiguration();
    }

    @Test
    void should_get_secure_random(){
        assertSame(SecureRandom.class,passwordManagerConfiguration.random().getClass());
    }

    @Test
    void should_passwordGenerator(){
        assertSame(PasswordManager.class,passwordManagerConfiguration.passworGenerator().getClass());
    }

}
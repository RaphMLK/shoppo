package fr.shoppo.msclient.infrastructure.config;

import fr.shoppo.msclient.infrastructure.service.PasswordManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.security.SecureRandom;
import java.util.Random;

@Configuration
public class PasswordManagerConfiguration {

    @Bean
    Random random(){
        return new SecureRandom();
    }

    @Bean
    PasswordManager passworGenerator() {
        return new PasswordManager();
    }
}

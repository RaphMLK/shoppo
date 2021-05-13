package fr.shoppo.ms_commerce.infrastructure.config;

import fr.shoppo.ms_commerce.infrastructure.service.PasswordManager;
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
    PasswordManager passworGenerator(){
        return new PasswordManager();
    }
}

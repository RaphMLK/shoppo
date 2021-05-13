package fr.shoppo.msnotification.infrastructure.config;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.MustacheFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MailTemplatingConfiguration {

    @Bean
    MustacheFactory mustacheFactory(){
        return new DefaultMustacheFactory();
    }

}

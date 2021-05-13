package fr.shoppo.msnotification.infrastructure.config;

import com.github.mustachejava.DefaultMustacheFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertSame;

class MailTemplatingConfigurationTest {
    MailTemplatingConfiguration mailTemplatingConfiguration;

    @BeforeEach
    void setup(){
        mailTemplatingConfiguration = new MailTemplatingConfiguration();
    }

    @Test
    void should_have_MustacheFactory(){
        assertSame(DefaultMustacheFactory.class,mailTemplatingConfiguration.mustacheFactory().getClass());
    }

}
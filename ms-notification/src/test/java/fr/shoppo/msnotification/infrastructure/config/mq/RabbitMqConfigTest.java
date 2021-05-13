package fr.shoppo.msnotification.infrastructure.config.mq;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;

import static org.junit.Assert.assertSame;

class RabbitMqConfigTest {
    @Test
    void should_get_messageConverter(){
        var conf = new RabbitMqConfig();
        assertSame(Jackson2JsonMessageConverter.class,conf.messageConverter().getClass());
    }

}
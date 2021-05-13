package fr.shoppo.msclient.infrastructure.config.mq;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;

import static org.junit.jupiter.api.Assertions.assertSame;

class RabbitMqConfigTest {
    @Test
    void should_get_messageConverter(){
        var conf = new RabbitMqConfig();
        assertSame(Jackson2JsonMessageConverter.class,conf.messageConverter().getClass());
    }
}
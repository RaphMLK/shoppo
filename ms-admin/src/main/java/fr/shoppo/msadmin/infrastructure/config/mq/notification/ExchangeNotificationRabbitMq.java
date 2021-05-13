package fr.shoppo.msadmin.infrastructure.config.mq.notification;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExchangeNotificationRabbitMq {

    String exchangerNotification;

    @Bean
    public DirectExchange exchangeNotification(){
        return new DirectExchange(exchangerNotification,true, false);
    }

    @Value("${mq.exchange.notification}")
    public void setExchangerNotification(String exchangerNotification) {
        this.exchangerNotification = exchangerNotification;
    }
}

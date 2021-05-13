package fr.shoppo.msnotification.infrastructure.config.mq;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

public class ExchangeMqConfig extends TimeToLiveConfiguration{

    String exchangerNotification;

    @Bean
    public DirectExchange exchangeNotification() {
        return new DirectExchange(exchangerNotification, true,false);
    }

    @Value("${mq.exchange.notification}")
    public void setExchangerNotification(String exchangerNotification) {
        this.exchangerNotification = exchangerNotification;
    }
}

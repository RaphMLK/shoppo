package fr.shoppo.mainmsinterface.infrastructure.config.mq;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ExchangeCommerceMqConfig extends TimeToLiveConfiguration {

    String exchangerCommerce;

    @Bean
    public DirectExchange exchangeCommerce(){
        Map<String, Object> args = new HashMap<>();
        args.put("x-message-ttl", timeToLive);
        return new DirectExchange(exchangerCommerce,true, true, args);
    }

    @Value("${mq.exchange.commerce}")
    public void setExchangerCommerce(String exchangerCommerce) {
        this.exchangerCommerce = exchangerCommerce;
    }
}

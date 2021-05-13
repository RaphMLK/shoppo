package fr.shoppo.mainmsinterface.infrastructure.config.mq;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ExchangeStatMqConfig extends TimeToLiveConfiguration {

    String exchangeStat;

    @Bean
    public DirectExchange exchangeStat(){
        Map<String, Object> args = new HashMap<>();
        args.put("x-message-ttl", timeToLive);
        return new DirectExchange(exchangeStat,true, false, args);
    }

    @Value("${mq.exchange.stat}")
    public void setExchangeStat(String exchangeStat) {
        this.exchangeStat = exchangeStat;
    }
}

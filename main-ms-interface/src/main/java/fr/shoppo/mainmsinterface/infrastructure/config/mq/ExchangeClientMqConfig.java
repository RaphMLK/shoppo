package fr.shoppo.mainmsinterface.infrastructure.config.mq;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ExchangeClientMqConfig extends TimeToLiveConfiguration {

    String exchangerClient;

    @Bean
    public DirectExchange exchangeClient(){
        Map<String, Object> args = new HashMap<>();
        args.put("x-message-ttl", timeToLive);
        return new DirectExchange(exchangerClient,true, true, args);
    }

    @Value("${mq.exchange.client}")
    public void setExchangerClient(String exchangerClient) {
        this.exchangerClient = exchangerClient;
    }
}

package fr.shoppo.msclient.infrastructure.config.mq.myexchange;

import fr.shoppo.msclient.infrastructure.config.mq.TimeToLiveConfiguration;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import java.util.HashMap;
import java.util.Map;

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

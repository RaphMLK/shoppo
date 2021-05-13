package fr.shoppo.msclient.infrastructure.config.mq.myexchange;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class IsParkMqConfig extends ExchangeClientMqConfig {
    String queueIsPark;
    String routingKeyIsPark;


    @Bean
    public Queue queueIsPark(){
        Map<String, Object> args = new HashMap<>();
        args.put("x-message-ttl", timeToLive);
        return new Queue(queueIsPark, true, false, true, args);
    }

    @Bean
    public Binding bindingIsPark(
            DirectExchange exchangeClient,
            Queue queueIsPark
    ) {
        return BindingBuilder.bind(queueIsPark)
                .to(exchangeClient)
                .with(routingKeyIsPark);
    }

    @Value("${mq.queue.is-park}")
    public void setQueueIsPark(String queueIsPark) {
        this.queueIsPark = queueIsPark;
    }

    @Value("${mq.routing-key.is-park}")
    public void setRoutingKeyIsPark(String routingKeyIsPark) {
        this.routingKeyIsPark = routingKeyIsPark;
    }
}

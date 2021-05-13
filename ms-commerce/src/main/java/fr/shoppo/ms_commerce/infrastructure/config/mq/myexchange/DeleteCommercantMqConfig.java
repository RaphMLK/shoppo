package fr.shoppo.ms_commerce.infrastructure.config.mq.myexchange;

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
public class DeleteCommercantMqConfig extends ExchangeCommerceMqConfig {
    String queueDeleteCommercant;

    String routingKeyDeleteCommercant;

    @Bean
    public Queue queueDeleteCommercant(){
        Map<String, Object> args = new HashMap<>();
        args.put("x-message-ttl", timeToLive);
        return new Queue(queueDeleteCommercant, true, false, true, args);

    }

    @Bean
    public Binding bindingDeleteCommercant(
            DirectExchange exchangeCommerce,
            Queue queueDeleteCommercant
    ){
        return BindingBuilder.bind(queueDeleteCommercant)
                .to(exchangeCommerce)
                .with(routingKeyDeleteCommercant);
    }

    @Value("${mq.routing-key.delete-commercant}")
    public void setRoutingKeyDeleteCommercant(String routingKeyDeleteCommercant) {
        this.routingKeyDeleteCommercant = routingKeyDeleteCommercant;
    }

    @Value("${mq.queue.delete-commercant}")
    public void setQueueDeleteCommercant(String queueDeleteCommercant) {
        this.queueDeleteCommercant = queueDeleteCommercant;
    }
}

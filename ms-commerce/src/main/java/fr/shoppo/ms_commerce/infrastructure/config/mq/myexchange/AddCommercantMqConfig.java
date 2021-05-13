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
public class AddCommercantMqConfig extends ExchangeCommerceMqConfig {

    String queueAddCommercant;
    String routingKeyAddCommercant;


    @Bean
    public Queue queueAddCommercant(){
        Map<String, Object> args = new HashMap<>();
        args.put("x-message-ttl", timeToLive);
        return new Queue(queueAddCommercant, true, false, true, args);

    }

    @Bean
    public Binding bindingAddCommercant(
            DirectExchange exchangeCommerce,
            Queue queueAddCommercant
    ){
        return BindingBuilder.bind(queueAddCommercant)
                .to(exchangeCommerce)
                .with(routingKeyAddCommercant);
    }

    @Value("${mq.routing-key.add-commercant}")
    public void setRoutingKeyAddCommercant(String routingKeyAddCommercant) {
        this.routingKeyAddCommercant = routingKeyAddCommercant;
    }

    @Value("${mq.queue.add-commercant}")
    public void setQueueAddCommercant(String queueAddCommercant) {
        this.queueAddCommercant = queueAddCommercant;
    }

}

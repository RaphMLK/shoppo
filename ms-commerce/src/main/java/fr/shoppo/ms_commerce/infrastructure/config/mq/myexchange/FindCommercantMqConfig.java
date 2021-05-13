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
public class FindCommercantMqConfig extends ExchangeCommerceMqConfig {

    String queueFindCommercant;
    String routingKeyFindCommercant;

    @Bean
    public Binding bindingFindCommercant(DirectExchange exchangeCommerce, Queue queueFindCommercant){
        return BindingBuilder.bind(queueFindCommercant).to(exchangeCommerce).with(routingKeyFindCommercant);
    }

    @Bean
    public Queue queueFindCommercant(){
        Map<String, Object> args = new HashMap<>();
        args.put("x-message-ttl", timeToLive);
        return new Queue(queueFindCommercant, true, false, true, args);
    }

    @Value("${mq.routing-key.find-commercant}")
    public void setRoutingKeyFindCommercant(String routingKeyFindCommercant) {
        this.routingKeyFindCommercant = routingKeyFindCommercant;
    }

    @Value("${mq.queue.find-commercant}")
    public void setQueueFindCommercant(String queueFindCommercant) {
        this.queueFindCommercant = queueFindCommercant;
    }
}
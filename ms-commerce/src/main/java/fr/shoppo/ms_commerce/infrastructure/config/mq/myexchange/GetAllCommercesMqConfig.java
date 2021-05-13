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
public class GetAllCommercesMqConfig extends ExchangeCommerceMqConfig {
    String queueGetAllCommerces;
    String routingKeyGetAllCommerces;

    @Bean
    public Queue queueGetAllCommerces(){
        Map<String, Object> args = new HashMap<>();
        args.put("x-message-ttl", timeToLive);
        return new Queue(queueGetAllCommerces, true, false, true, args);
    }

    @Bean
    public Binding bindingGetAllCommerces(DirectExchange exchangeCommerce, Queue queueGetAllCommerces){
        return BindingBuilder.bind(queueGetAllCommerces).to(exchangeCommerce).with(routingKeyGetAllCommerces);
    }

    @Value("${mq.queue.get-all-commerces}")
    public void setQueueGetAllCommerces(String queueGetAllCommerces) {
        this.queueGetAllCommerces = queueGetAllCommerces;
    }

    @Value("${mq.routing-key.get-all-commerces}")
    public void setRoutingKeyGetAllCommerces(String routingKeyGetAllCommerces) {
        this.routingKeyGetAllCommerces = routingKeyGetAllCommerces;
    }
}

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
public class GetCommandesCommerceMqConfig extends ExchangeCommerceMqConfig {

    String queueGetCommandesCommerce;
    String routingKeyGetCommandesCommerce;

    @Bean
    public Queue queueGetCommandesCommerce(){
        Map<String, Object> args = new HashMap<>();
        args.put("x-message-ttl", timeToLive);
        return new Queue(queueGetCommandesCommerce, true, false, true, args);
    }

    @Bean
    public Binding bindingGetCommandesCommerce(DirectExchange exchangeCommerce, Queue queueGetCommandesCommerce){
        return BindingBuilder.bind(queueGetCommandesCommerce).to(exchangeCommerce).with(routingKeyGetCommandesCommerce);
    }

    @Value("${mq.queue.get-commandes-commerce}")
    public void setQueueGetCommandesCommerce(String queueGetCommandesCommerce) {
        this.queueGetCommandesCommerce = queueGetCommandesCommerce;
    }

    @Value("${mq.routing-key.get-commandes-commerce}")
    public void setRoutingKeyGetCommandesCommerce(String routingKeyGetCommandesCommerce) {
        this.routingKeyGetCommandesCommerce = routingKeyGetCommandesCommerce;
    }

}

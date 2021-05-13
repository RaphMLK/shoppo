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
public class GetCommercantMqConfig extends ExchangeCommerceMqConfig {

    String queueGetCommercant;
    String routingKeyGetCommercant;

    @Bean
    public Queue queueGetCommercant(){
        Map<String, Object> args = new HashMap<>();
        args.put("x-message-ttl", timeToLive);
        return new Queue(queueGetCommercant, true, false, true, args);
    }

    @Bean
    public Binding bindingGetCommercant(DirectExchange exchangeCommerce, Queue queueGetCommercant){
        return BindingBuilder.bind(queueGetCommercant).to(exchangeCommerce).with(routingKeyGetCommercant);
    }

    @Value("${mq.queue.get-commercant}")
    public void setQueueGetCommercant(String queueGetCommercant) {
        this.queueGetCommercant = queueGetCommercant;
    }

    @Value("${mq.routing-key.get-commercant}")
    public void setRoutingKeyGetCommercant(String routingKeyGetCommercant) {
        this.routingKeyGetCommercant = routingKeyGetCommercant;
    }

}

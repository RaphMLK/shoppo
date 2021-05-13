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
public class CreateCommerceMqConfig extends ExchangeCommerceMqConfig {
    String queueCreateCommerce;
    String routingKeyCreateCommerce;

    @Bean
    public Queue queueCreateCommerce(){
        Map<String, Object> args = new HashMap<>();
        args.put("x-message-ttl", timeToLive);
        return new Queue(queueCreateCommerce, true, false, true, args);
    }

    @Bean
    public Binding bindingCreateCommerce(
            DirectExchange exchangeCommerce,
            Queue queueCreateCommerce
    ){
        return BindingBuilder.bind(queueCreateCommerce)
                .to(exchangeCommerce)
                .with(routingKeyCreateCommerce);
    }

    @Value("${mq.routing-key.create-commerce}")
    public void setRoutingKeyCreateCommerce(String routingKeyCreateCommerce) {
        this.routingKeyCreateCommerce = routingKeyCreateCommerce;
    }

    @Value("${mq.queue.create-commerce}")
    public void setQueueCreateCommerce(String queueCreateCommerce) {
        this.queueCreateCommerce = queueCreateCommerce;
    }
}

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
public class GetProductsCommerceMqConfig extends ExchangeCommerceMqConfig {
    String queueGetProductsCommerce;
    String routingKeyGetProductsCommerce;

    @Bean
    public Queue queueGetProductsCommerce(){
        Map<String, Object> args = new HashMap<>();
        args.put("x-message-ttl", timeToLive);
        return new Queue(queueGetProductsCommerce, true, false, true, args);
    }

    @Bean
    public Binding bindingGetProductsCommerce(DirectExchange exchangeCommerce, Queue queueGetProductsCommerce){
        return BindingBuilder.bind(queueGetProductsCommerce).to(exchangeCommerce).with(routingKeyGetProductsCommerce);
    }

    @Value("${mq.queue.get-products-commerce}")
    public void setQueueGetProductsCommerce(String queueGetProductsCommerce) {
        this.queueGetProductsCommerce = queueGetProductsCommerce;
    }

    @Value("${mq.routing-key.get-products-commerce}")
    public void setRoutingKeyGetProductsCommerce(String routingKeyGetProductsCommerce) {
        this.routingKeyGetProductsCommerce = routingKeyGetProductsCommerce;
    }
}

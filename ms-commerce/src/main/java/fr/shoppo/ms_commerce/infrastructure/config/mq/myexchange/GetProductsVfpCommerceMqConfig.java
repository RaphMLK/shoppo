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
public class GetProductsVfpCommerceMqConfig extends ExchangeCommerceMqConfig {
    String queueGetProductsVfpCommerce;
    String routingKeyGetProductsVfpCommerce;

    @Bean
    public Queue queueGetProductsVfpCommerce(){
        Map<String, Object> args = new HashMap<>();
        args.put("x-message-ttl", timeToLive);
        return new Queue(queueGetProductsVfpCommerce, true, false, true, args);
    }

    @Bean
    public Binding bindingGetProductsVfpCommerce(DirectExchange exchangeCommerce, Queue queueGetProductsVfpCommerce){
        return BindingBuilder.bind(queueGetProductsVfpCommerce).to(exchangeCommerce).with(routingKeyGetProductsVfpCommerce);
    }

    @Value("${mq.queue.get-product-commerce-vfp}")
    public void setQueueGetProductsVfpCommerce(String queueGetProductsVfpCommerce) {
        this.queueGetProductsVfpCommerce = queueGetProductsVfpCommerce;
    }

    @Value("${mq.routing-key.get-product-commerce-vfp}")
    public void setRoutingKeyGetProductsVfpCommerce(String routingKeyGetProductsVfpCommerce) {
        this.routingKeyGetProductsVfpCommerce = routingKeyGetProductsVfpCommerce;
    }
}

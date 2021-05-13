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
public class GetProductsNotVfpCommerceMqConfig extends ExchangeCommerceMqConfig {
    String queueGetProductsNotVfpCommerce;
    String routingKeyGetProductsNotVfpCommerce;

    @Bean
    public Queue queueGetProductsNotVfpCommerce(){
        Map<String, Object> args = new HashMap<>();
        args.put("x-message-ttl", timeToLive);
        return new Queue(queueGetProductsNotVfpCommerce, true, false, true, args);
    }

    @Bean
    public Binding bindingGetProductsNotVfpCommerce(DirectExchange exchangeCommerce, Queue queueGetProductsNotVfpCommerce){
        return BindingBuilder.bind(queueGetProductsNotVfpCommerce).to(exchangeCommerce).with(routingKeyGetProductsNotVfpCommerce);
    }

    @Value("${mq.queue.get-product-commerce-not-vfp}")
    public void setQueueGetProductsNotVfpCommerce(String queueGetProductsNotVfpCommerce) {
        this.queueGetProductsNotVfpCommerce = queueGetProductsNotVfpCommerce;
    }

    @Value("${mq.routing-key.get-product-commerce-not-vfp}")
    public void setRoutingKeyGetProductsNotVfpCommerce(String routingKeyGetProductsNotVfpCommerce) {
        this.routingKeyGetProductsNotVfpCommerce = routingKeyGetProductsNotVfpCommerce;
    }
}

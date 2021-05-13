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
public class GetProductMqConfig extends ExchangeCommerceMqConfig {

    String queueGetProduct;
    String routingKeyGetProduct;

    @Bean
    public Binding bindingGetProduct(DirectExchange exchangeCommerce, Queue queueGetProduct){
        return BindingBuilder.bind(queueGetProduct).to(exchangeCommerce).with(routingKeyGetProduct);
    }

    @Bean
    public Queue queueGetProduct(){
        Map<String, Object> args = new HashMap<>();
        args.put("x-message-ttl", timeToLive);
        return new Queue(queueGetProduct, true, false, true, args);
    }

    @Value("${mq.routing-key.get-product}")
    public void setRoutingKeyGetProduct(String routingKeyGetProduct) {
        this.routingKeyGetProduct = routingKeyGetProduct;
    }

    @Value("${mq.queue.get-product}")
    public void setQueueGetProduct(String queueGetProduct) {
        this.queueGetProduct = queueGetProduct;
    }



}

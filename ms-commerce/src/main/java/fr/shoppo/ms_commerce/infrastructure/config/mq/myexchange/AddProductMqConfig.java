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
public class AddProductMqConfig extends ExchangeCommerceMqConfig {
    
    String queueAddProduct;
    String routingKeyAddProduct;
    
    @Bean
    public Queue queueAddProduct(){
        Map<String, Object> args = new HashMap<>();
        args.put("x-message-ttl", timeToLive);
        return new Queue(queueAddProduct, true, false, true, args);
    }
    
    @Bean
    public Binding bindingAddProduct(DirectExchange exchangeCommerce, Queue queueAddProduct){
        return BindingBuilder.bind(queueAddProduct).to(exchangeCommerce).with(routingKeyAddProduct);
    }

    @Value("${mq.routing-key.add-product}")
    public void setRoutingKeyAddProduct(String routingKeyAddProduct) {
        this.routingKeyAddProduct = routingKeyAddProduct;
    }

    @Value("${mq.queue.add-product}")
    public void setQueueAddProduct(String queueAddProduct) {
        this.queueAddProduct = queueAddProduct;
    }

}

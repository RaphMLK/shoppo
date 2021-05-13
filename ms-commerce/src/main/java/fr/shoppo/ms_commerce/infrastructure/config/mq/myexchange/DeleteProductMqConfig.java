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
public class DeleteProductMqConfig extends ExchangeCommerceMqConfig {

    String queueDeleteProduct;
    String routingKeyDeleteProduct;

    @Bean
    public Binding bindingDeleteProduct(DirectExchange exchangeCommerce, Queue queueDeleteProduct){
        return BindingBuilder.bind(queueDeleteProduct).to(exchangeCommerce).with(routingKeyDeleteProduct);
    }

    @Bean
    public Queue queueDeleteProduct(){
        Map<String, Object> args = new HashMap<>();
        args.put("x-message-ttl", timeToLive);
        return new Queue(queueDeleteProduct, true, false, true, args);
    }

    @Value("${mq.routing-key.delete-product}")
    public void setRoutingKeyDeleteProduct(String routingKeyDeleteProduct) {
        this.routingKeyDeleteProduct = routingKeyDeleteProduct;
    }

    @Value("${mq.queue.delete-product}")
    public void setQueueDeleteProduct(String queueDeleteProduct) {
        this.queueDeleteProduct = queueDeleteProduct;
    }

}

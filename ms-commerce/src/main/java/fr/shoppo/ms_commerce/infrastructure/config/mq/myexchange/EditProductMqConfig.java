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
public class EditProductMqConfig extends ExchangeCommerceMqConfig {

    String queueEditProduct;
    String routingKeyEditProduct;

    @Bean
    public Binding bindingEditProduct(DirectExchange exchangeCommerce, Queue queueEditProduct){
        return BindingBuilder.bind(queueEditProduct).to(exchangeCommerce).with(routingKeyEditProduct);
    }

    @Bean
    public Queue queueEditProduct(){
        Map<String, Object> args = new HashMap<>();
        args.put("x-message-ttl", timeToLive);
        return new Queue(queueEditProduct, true, false, true, args);
    }

    @Value("${mq.routing-key.edit-product}")
    public void setRoutingKeyEditProduct(String routingKeyEditProduct) {
        this.routingKeyEditProduct = routingKeyEditProduct;
    }

    @Value("${mq.queue.edit-product}")
    public void setQueueEditProduct(String queueEditProduct) {
        this.queueEditProduct = queueEditProduct;
    }


}

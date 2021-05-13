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
public class AddVfpByProductAndCommerceMqConfig extends ExchangeCommerceMqConfig {

    String queueAddVfpByProductAndCommerce;
    String routingKeyAddVfpByProductAndCommerce;


    @Bean
    public Queue queueAddVfpByProductAndCommerce(){
        Map<String, Object> args = new HashMap<>();
        args.put("x-message-ttl", timeToLive);
        return new Queue(queueAddVfpByProductAndCommerce, true, false, true, args);

    }

    @Bean
    public Binding bindingAddVfpByProudctAndCommerce(
            DirectExchange exchangeCommerce,
            Queue queueAddVfpByProductAndCommerce
    ){
        return BindingBuilder.bind(queueAddVfpByProductAndCommerce)
                .to(exchangeCommerce)
                .with(routingKeyAddVfpByProductAndCommerce);
    }

    @Value("${mq.queue.add-vfp-by-product-and-commerce}")
    public void setQueueAddVfpByProductAndCommerce(String queueAddVfpByProductAndCommerce) {
        this.queueAddVfpByProductAndCommerce = queueAddVfpByProductAndCommerce;
    }


    @Value("${mq.routing-key.add-vfp-by-product-and-commerce}")
    public void setRoutingKeyAddVfpByProductAndCommerce(String routingKeyAddVfpByProductAndCommerce) {
        this.routingKeyAddVfpByProductAndCommerce = routingKeyAddVfpByProductAndCommerce;
    }
}

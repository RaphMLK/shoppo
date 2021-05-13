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
public class UpdateCommerceMqConfig extends ExchangeCommerceMqConfig {
    String queueUpdateCommerce;
    String routingKeyUpdateCommerce;

    @Bean
    public Queue queueUpdateCommerce(){
        Map<String, Object> args = new HashMap<>();
        args.put("x-message-ttl", timeToLive);
        return new Queue(queueUpdateCommerce, true, false, true, args);
    }
    
    @Bean
    public Binding bindingUpdateCommerce(
            DirectExchange exchangeCommerce,
            Queue queueUpdateCommerce
    ){
        return BindingBuilder.bind(queueUpdateCommerce)
                .to(exchangeCommerce)
                .with(routingKeyUpdateCommerce);
    }

    @Value("${mq.routing-key.update-commerce}")
    public void setRoutingKeyUpdateCommerce(String routingKeyUpdateCommerce) {
        this.routingKeyUpdateCommerce = routingKeyUpdateCommerce;
    }

    @Value("${mq.queue.update-commerce}")
    public void setQueueUpdateCommerce(String queueUpdateCommerce) {
        this.queueUpdateCommerce = queueUpdateCommerce;
    }
}

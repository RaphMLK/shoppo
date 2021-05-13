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
public class DeleteVfpByCommerceMqConfig extends ExchangeCommerceMqConfig {

    String queueDeleteVfpByCommerce;
    String routingKeyDeleteVfpByCommerce;


    @Bean
    public Queue queueDeleteVfpByCommerce(){
        Map<String, Object> args = new HashMap<>();
        args.put("x-message-ttl", timeToLive);
        return new Queue(queueDeleteVfpByCommerce, true, false, true, args);

    }

    @Bean
    public Binding bindingDeleteVfpByCommerce(
            DirectExchange exchangeCommerce,
            Queue queueDeleteVfpByCommerce
    ){
        return BindingBuilder.bind(queueDeleteVfpByCommerce)
                .to(exchangeCommerce)
                .with(routingKeyDeleteVfpByCommerce);
    }

    @Value("${mq.queue.delete-vfp-by-commerce}")
    public void setQueueDeleteVfpByCommerce(String queueDeleteVfpByCommerce) {
        this.queueDeleteVfpByCommerce = queueDeleteVfpByCommerce;
    }


    @Value("${mq.routing-key.delete-vfp-by-commerce}")
    public void setRoutingKeyDeleteVfpByCommerce(String routingKeyDeleteVfpByCommerce) {
        this.routingKeyDeleteVfpByCommerce = routingKeyDeleteVfpByCommerce;
    }
}

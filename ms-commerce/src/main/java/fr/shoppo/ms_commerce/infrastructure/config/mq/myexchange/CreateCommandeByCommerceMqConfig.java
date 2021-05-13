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
public class CreateCommandeByCommerceMqConfig extends ExchangeCommerceMqConfig {
    String queueCreateCommandeByCommerce;
    String routingKeyCreateCommandeByCommerce;

    @Bean
    public Queue queueCreateCommandeByCommerce(){
        Map<String, Object> args = new HashMap<>();
        args.put("x-message-ttl", timeToLive);
        return new Queue(queueCreateCommandeByCommerce, true, false, true, args);
    }


    @Bean
    public Binding bindingCreateCommandeByCommerce(
            DirectExchange exchangeCommerce,
            Queue queueCreateCommandeByCommerce
    ){
        return BindingBuilder.bind(queueCreateCommandeByCommerce)
                .to(exchangeCommerce)
                .with(routingKeyCreateCommandeByCommerce);
    }


    @Value("${mq.queue.create-commande-by-commerce}")
    public void setQueueCreateCommandeByCommerce(String queueCreateCommandeByCommerce) {
        this.queueCreateCommandeByCommerce = queueCreateCommandeByCommerce;
    }

    @Value("${mq.routing-key.create-commande-by-commerce}")
    public void setRoutingKeyCreateCommandeByCommerce(String routingKeyCreateCommandeByCommerce) {
        this.routingKeyCreateCommandeByCommerce = routingKeyCreateCommandeByCommerce;
    }
}

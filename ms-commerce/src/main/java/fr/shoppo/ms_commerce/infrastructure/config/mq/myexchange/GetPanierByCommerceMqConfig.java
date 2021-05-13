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
public class GetPanierByCommerceMqConfig extends ExchangeCommerceMqConfig {

    String queueGetPanierByCommerce;
    String routingKeyGetPanierByCommerce;


    @Bean
    public Queue queueGetPanierByCommerce(){
        Map<String, Object> args = new HashMap<>();
        args.put("x-message-ttl", timeToLive);
        return new Queue(queueGetPanierByCommerce, true, false, true, args);

    }

    @Bean
    public Binding bindingGetPanierByCommerce(
            DirectExchange exchangeCommerce,
            Queue queueGetPanierByCommerce
    ){
        return BindingBuilder.bind(queueGetPanierByCommerce)
                .to(exchangeCommerce)
                .with(routingKeyGetPanierByCommerce);
    }

    @Value("${mq.queue.get-panier-by-commerce}")
    public void setQueueGetPanier(String queueGetPanierByCommerce) {
        this.queueGetPanierByCommerce = queueGetPanierByCommerce;
    }

    @Value("${mq.routing-key.get-panier-by-commerce}")
    public void setRoutingKeyGetPanier(String routingKeyGetPanierByCommerce) {
        this.routingKeyGetPanierByCommerce = routingKeyGetPanierByCommerce;
    }

}

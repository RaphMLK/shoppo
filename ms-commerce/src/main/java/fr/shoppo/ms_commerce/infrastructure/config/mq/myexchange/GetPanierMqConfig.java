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
public class GetPanierMqConfig extends ExchangeCommerceMqConfig {

    String queueGetPanier;
    String routingKeyGetPanier;


    @Bean
    public Queue queueGetPanier(){
        Map<String, Object> args = new HashMap<>();
        args.put("x-message-ttl", timeToLive);
        return new Queue(queueGetPanier, true, false, true, args);

    }

    @Bean
    public Binding bindingGetPanier(
            DirectExchange exchangeCommerce,
            Queue queueGetPanier
    ){
        return BindingBuilder.bind(queueGetPanier)
                .to(exchangeCommerce)
                .with(routingKeyGetPanier);
    }

    @Value("${mq.queue.get-panier}")
    public void setQueueGetPanier(String queueGetPanier) {
        this.queueGetPanier = queueGetPanier;
    }

    @Value("${mq.routing-key.get-panier}")
    public void setRoutingKeyGetPanier(String routingKeyGetPanier) {
        this.routingKeyGetPanier = routingKeyGetPanier;
    }

}

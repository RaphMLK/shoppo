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
public class CreateCommandeMqConfig extends ExchangeCommerceMqConfig {
    String queueCreateCommande;
    String routingKeyCreateCommande;


    @Bean
    public Queue queueCreateCommande(){
        Map<String, Object> args = new HashMap<>();
        args.put("x-message-ttl", timeToLive);
        return new Queue(queueCreateCommande, true, false, true, args);
    }

    @Bean
    public Binding bindingCreateCommande(
            DirectExchange exchangeCommerce,
            Queue queueCreateCommande
    ){
        return BindingBuilder.bind(queueCreateCommande)
                .to(exchangeCommerce)
                .with(routingKeyCreateCommande);
    }

    @Value("${mq.routing-key.create-commande}")
    public void setRoutingKeyCreateCommande(String routingKeyCreateCommande) {
        this.routingKeyCreateCommande = routingKeyCreateCommande;
    }

    @Value("${mq.queue.create-commande}")
    public void setQueueCreateCommande(String queueCreateCommande) {
        this.queueCreateCommande = queueCreateCommande;
    }
}

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
public class GetCommandeMqConfig extends ExchangeCommerceMqConfig {

    String queueGetCommande;
    String routingKeyGetCommande;

    @Bean
    public Queue queueGetCommande(){
        Map<String, Object> args = new HashMap<>();
        args.put("x-message-ttl", timeToLive);
        return new Queue(queueGetCommande, true, false, true, args);
    }

    @Bean
    public Binding bindingGetCommande(DirectExchange exchangeCommerce, Queue queueGetCommande){
        return BindingBuilder.bind(queueGetCommande).to(exchangeCommerce).with(routingKeyGetCommande);
    }

    @Value("${mq.queue.get-commande}")
    public void setQueueGetCommande(String queueGetCommande) {
        this.queueGetCommande = queueGetCommande;
    }

    @Value("${mq.routing-key.get-commande}")
    public void setRoutingKeyGetCommande(String routingKeyGetCommande) {
        this.routingKeyGetCommande = routingKeyGetCommande;
    }

}

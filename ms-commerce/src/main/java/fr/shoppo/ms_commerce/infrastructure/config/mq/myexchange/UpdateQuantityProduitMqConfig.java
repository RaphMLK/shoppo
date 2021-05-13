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
public class UpdateQuantityProduitMqConfig extends ExchangeCommerceMqConfig {
    String queueUpdateQuantityProduit;
    String routingKeyUpdateQuantityProduit;

    @Bean
    public Queue queueUpdateQuantityProduit(){
        Map<String, Object> args = new HashMap<>();
        args.put("x-message-ttl", timeToLive);
        return new Queue(queueUpdateQuantityProduit, true, false, true, args);
    }

    @Bean
    public Binding bindingUpdateQuantityProduit(DirectExchange exchangeCommerce, Queue queueUpdateQuantityProduit){
        return BindingBuilder.bind(queueUpdateQuantityProduit).to(exchangeCommerce).with(routingKeyUpdateQuantityProduit);
    }

    @Value("${mq.queue.update-quantity}")
    public void setQueueUpdateQuantityProduit(String queueUpdateQuantityProduit) {
        this.queueUpdateQuantityProduit = queueUpdateQuantityProduit;
    }

    @Value("${mq.routing-key.update-quantity}")
    public void setRoutingKeyUpdateQuantityProduit(String routingKeyUpdateQuantityProduit) {
        this.routingKeyUpdateQuantityProduit = routingKeyUpdateQuantityProduit;
    }
}

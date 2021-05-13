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
public class UpdatePanierMqConfig extends ExchangeCommerceMqConfig {

    String queueUpdatePanier;
    String routingKeyUpdatePanier;


    @Bean
    public Queue queueUpdatePanier(){
        Map<String, Object> args = new HashMap<>();
        args.put("x-message-ttl", timeToLive);
        return new Queue(queueUpdatePanier, true, false, true, args);

    }

    @Bean
    public Binding bindingUpdatePanier(
            DirectExchange exchangeCommerce,
            Queue queueUpdatePanier
    ){
        return BindingBuilder.bind(queueUpdatePanier)
                .to(exchangeCommerce)
                .with(routingKeyUpdatePanier);
    }

    @Value("${mq.queue.update-panier}")
    public void setQueueUpdatePanier(String queueUpdatePanier) {
        this.queueUpdatePanier = queueUpdatePanier;
    }

    @Value("${mq.routing-key.update-panier}")
    public void setRoutingKeyUpdatePanier(String routingKeyUpdatePanier) {
        this.routingKeyUpdatePanier = routingKeyUpdatePanier;
    }

}

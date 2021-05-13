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
public class AddDeleteVfpPanierMqConfig extends ExchangeCommerceMqConfig {

    String queueAddDeleteVfpPanier;
    String routingKeyAddDeleteVfpPanier;


    @Bean
    public Queue queueAddDeleteVfpPanier(){
        Map<String, Object> args = new HashMap<>();
        args.put("x-message-ttl", timeToLive);
        return new Queue(queueAddDeleteVfpPanier, true, false, true, args);

    }

    @Bean
    public Binding bindingAddDeleteVfpPanier(
            DirectExchange exchangeCommerce,
            Queue queueAddDeleteVfpPanier
    ){
        return BindingBuilder.bind(queueAddDeleteVfpPanier)
                .to(exchangeCommerce)
                .with(routingKeyAddDeleteVfpPanier);
    }

    @Value("${mq.queue.add-delete-vfp-panier}")
    public void setQueueAddDeleteVfpPanier(String queueAddDeleteVfpPanier) {
        this.queueAddDeleteVfpPanier = queueAddDeleteVfpPanier;
    }

    @Value("${mq.routing-key.add-delete-vfp-panier}")
    public void setRoutingKeyAddDeleteVfpPanier(String routingKeyAddDeleteVfpPanier) {
        this.routingKeyAddDeleteVfpPanier = routingKeyAddDeleteVfpPanier;
    }
}

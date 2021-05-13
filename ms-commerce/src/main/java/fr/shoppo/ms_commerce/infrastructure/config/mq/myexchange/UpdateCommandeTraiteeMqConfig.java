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
public class UpdateCommandeTraiteeMqConfig extends ExchangeCommerceMqConfig {

    String queueUpdateCommandeTraitee;
    String routingKeyUpdateCommandeTraitee;

    @Bean
    public Queue queueUpdateCommandeTraitee(){
        Map<String, Object> args = new HashMap<>();
        args.put("x-message-ttl", timeToLive);
        return new Queue(queueUpdateCommandeTraitee, true, false, true, args);
    }

    @Bean
    public Binding bindingUpdateCommandeTraitee(
            DirectExchange exchangeCommerce,
            Queue queueUpdateCommandeTraitee
    ){
        return BindingBuilder.bind(queueUpdateCommandeTraitee)
                .to(exchangeCommerce)
                .with(routingKeyUpdateCommandeTraitee);
    }

    @Value("${mq.routing-key.update-commande-traitee}")
    public void setRoutingKeyUpdateCommandeTraitee(String routingKeyUpdateCommandeTraitee) {
        this.routingKeyUpdateCommandeTraitee = routingKeyUpdateCommandeTraitee;
    }

    @Value("${mq.queue.update-commande-traitee}")
    public void setQueueUpdateCommandeTraitee(String queueUpdateCommandeTraitee) {
        this.queueUpdateCommandeTraitee = queueUpdateCommandeTraitee;
    }

}

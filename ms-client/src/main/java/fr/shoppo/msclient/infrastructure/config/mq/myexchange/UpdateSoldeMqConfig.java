package fr.shoppo.msclient.infrastructure.config.mq.myexchange;

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
public class UpdateSoldeMqConfig extends ExchangeClientMqConfig {

    String queueUpdateSolde;
    String routingKeyUpdateSolde;

    @Bean
    public Queue queueUpdateSolde() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-message-ttl", timeToLive);
        return new Queue(queueUpdateSolde, true, false, true, args);
    }

    @Bean
    public Binding bindingUpdateSolde(
            DirectExchange exchangeClient,
            Queue queueUpdateSolde
    ) {
        return BindingBuilder.bind(queueUpdateSolde)
                .to(exchangeClient)
                .with(routingKeyUpdateSolde);
    }

    @Value("${mq.queue.update-solde}")
    public void setQueueUpdateSolde(String queueUpdateSolde) {
        this.queueUpdateSolde = queueUpdateSolde;
    }

    @Value("${mq.routing-key.update-solde}")
    public void setRoutingKeyUpdateSolde(String routingKeyUpdateSolde) {
        this.routingKeyUpdateSolde = routingKeyUpdateSolde;
    }

}

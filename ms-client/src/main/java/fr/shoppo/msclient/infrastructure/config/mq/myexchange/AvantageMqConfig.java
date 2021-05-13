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
public class AvantageMqConfig extends ExchangeClientMqConfig {

    String queueUpdateAvantage;
    String routingKeyUpdateAvantage;

    @Bean
    public Queue queueUpdateAvantage() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-message-ttl", timeToLive);
        return new Queue(queueUpdateAvantage, true, false, true, args);
    }

    @Bean
    public Binding bindingUpdateAvantage(
            DirectExchange exchangeClient,
            Queue queueUpdateAvantage
    ) {
        return BindingBuilder.bind(queueUpdateAvantage)
                .to(exchangeClient)
                .with(routingKeyUpdateAvantage);
    }

    @Value("${mq.queue.update-avantage}")
    public void setQueueUpdateAvantage(String queueUpdateAvantage) {
        this.queueUpdateAvantage = queueUpdateAvantage;
    }

    @Value("${mq.routing-key.update-avantage}")
    public void setRoutingKeyUpdateSolde(String routingKeyUpdateAvantage) {
        this.routingKeyUpdateAvantage = routingKeyUpdateAvantage;
    }

    String queueGetAllAvantage;
    String routingKeyGetAllAvantage;

    @Bean
    public Queue queueGetAllAvantage() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-message-ttl", timeToLive);
        return new Queue(queueGetAllAvantage, true, false, true, args);
    }

    @Bean
    public Binding bindingGetAllAvantage(
            DirectExchange exchangeClient,
            Queue queueGetAllAvantage
    ) {
        return BindingBuilder.bind(queueGetAllAvantage)
                .to(exchangeClient)
                .with(routingKeyGetAllAvantage);
    }

    @Value("${mq.queue.get-all-avantage}")
    public void setQueueGetAllAvantage(String queueGetAllAvantage) {
        this.queueGetAllAvantage = queueGetAllAvantage;
    }

    @Value("${mq.routing-key.get-all-avantage}")
    public void setRoutingKeyGetAllAvantage(String routingKeyGetAllAvantage) {
        this.routingKeyGetAllAvantage = routingKeyGetAllAvantage;
    }

}

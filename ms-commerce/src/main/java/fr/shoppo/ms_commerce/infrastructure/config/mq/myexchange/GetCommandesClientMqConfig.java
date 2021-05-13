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
public class GetCommandesClientMqConfig extends ExchangeCommerceMqConfig {

    String queueGetCommandesClient;
    String routingKeyGetCommandesClient;

    @Bean
    public Queue queueGetCommandesClient(){
        Map<String, Object> args = new HashMap<>();
        args.put("x-message-ttl", timeToLive);
        return new Queue(queueGetCommandesClient, true, false, true, args);
    }

    @Bean
    public Binding bindingGetCommandesClient(DirectExchange exchangeCommerce, Queue queueGetCommandesClient){
        return BindingBuilder.bind(queueGetCommandesClient).to(exchangeCommerce).with(routingKeyGetCommandesClient);
    }

    @Value("${mq.queue.get-commandes-client}")
    public void setQueueGetCommandesClient(String queueGetCommandesClient) {
        this.queueGetCommandesClient = queueGetCommandesClient;
    }

    @Value("${mq.routing-key.get-commandes-client}")
    public void setRoutingKeyGetCommandesClient(String routingKeyGetCommandesClient) {
        this.routingKeyGetCommandesClient = routingKeyGetCommandesClient;
    }

}

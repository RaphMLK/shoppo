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
public class CreateClientMqConfig extends ExchangeClientMqConfig {
    
    String queueCreateClient;
    String routingKeyCreateClient;


    @Bean
    public Queue queueCreateClient(){
        Map<String, Object> args = new HashMap<>();
        args.put("x-message-ttl", timeToLive);
        return new Queue(queueCreateClient, true, false, true, args);

    }

    @Bean
    public Binding bindingCreateClient(
            DirectExchange exchangeClient,
            Queue queueCreateClient
    ) {
        return BindingBuilder.bind(queueCreateClient)
                .to(exchangeClient)
                .with(routingKeyCreateClient);
    }

    @Value("${mq.routing-key.create-client}")
    public void setRoutingKeyCreateClient(String routingKeyCreateClient) {
        this.routingKeyCreateClient = routingKeyCreateClient;
    }

    @Value("${mq.queue.create-client}")
    public void setQueueCreateClient(String queueCreateClient) {
        this.queueCreateClient = queueCreateClient;
    }
}

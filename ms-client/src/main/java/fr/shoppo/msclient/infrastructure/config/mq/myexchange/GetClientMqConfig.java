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
public class GetClientMqConfig extends ExchangeClientMqConfig {

    String queueGetClient;
    String routingGetClient;

    @Bean
    public Queue queueGetClient() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-message-ttl", timeToLive);
        return new Queue(queueGetClient, true, false, true, args);
    }

    @Bean
    public Binding bindingGetClient(
            DirectExchange exchangeClient,
            Queue queueGetClient
    ) {
        return BindingBuilder.bind(queueGetClient)
                .to(exchangeClient)
                .with(routingGetClient);
    }

    @Value("${mq.queue.get-client}")
    public void setQueueGetClient(String queueGetClient) {
        this.queueGetClient = queueGetClient;
    }

    @Value("${mq.routing-key.get-client}")
    public void setRoutingKeyGetClient(String routingGetClient) {
        this.routingGetClient = routingGetClient;
    }


}

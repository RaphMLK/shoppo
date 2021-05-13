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
public class IsTransportMqConfig extends ExchangeClientMqConfig {
    String queueIsTransport;
    String routingKeyIsTransport;


    @Bean
    public Queue queueIsTransport(){
        Map<String, Object> args = new HashMap<>();
        args.put("x-message-ttl", timeToLive);
        return new Queue(queueIsTransport, true, false, true, args);
    }

    @Bean
    public Binding bindingIsTransport(
            DirectExchange exchangeClient,
            Queue queueIsTransport
    ) {
        return BindingBuilder.bind(queueIsTransport)
                .to(exchangeClient)
                .with(routingKeyIsTransport);
    }

    @Value("${mq.queue.is-transport}")
    public void setQueueIsTransport(String queueIsTransport) {
        this.queueIsTransport = queueIsTransport;
    }

    @Value("${mq.routing-key.is-transport}")
    public void setRoutingKeyIsTransport(String routingKeyIsTransport) {
        this.routingKeyIsTransport = routingKeyIsTransport;
    }
}

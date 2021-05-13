package fr.shoppo.msnotification.infrastructure.config.mq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AddCommercantMqConfig extends ExchangeMqConfig {
    String queueAddCommercantNotification;
    String routingKeyAddCommercantNotification;

    @Bean
    public Queue queueAddCommercantNotification(){
        return new Queue(queueAddCommercantNotification);
    }


    @Bean
    public Binding bindingAddCommercant(DirectExchange exchangeNotification,
                             Queue queueAddCommercantNotification) {
        return BindingBuilder.bind(queueAddCommercantNotification)
                .to(exchangeNotification)
                .with(routingKeyAddCommercantNotification);
    }

    @Value("${mq.queue.notification.add-commercant}")
    public void setQueueAddCommercantNotification(String queueAddCommercantNotification) {
        this.queueAddCommercantNotification = queueAddCommercantNotification;
    }

    @Value("${mq.routing-key.add-commercant}")
    public void setRoutingKeyAddCommercantNotification(String routingKeyAddCommercantNotification) {
        this.routingKeyAddCommercantNotification = routingKeyAddCommercantNotification;
    }
}

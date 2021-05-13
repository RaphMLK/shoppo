package fr.shoppo.msnotification.infrastructure.config.mq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CreateClientMqConfig extends ExchangeMqConfig{

    String queueCreateClientNotification;
    String routingKeyCreateClientNotification;

    @Bean
    public Queue queueCreateClientNotification(){
        return new Queue(queueCreateClientNotification);
    }

    @Bean
    public Binding bindingCreateClient(DirectExchange exchangeNotification,
                                        Queue queueCreateClientNotification) {
        return BindingBuilder.bind(queueCreateClientNotification)
                .to(exchangeNotification)
                .with(routingKeyCreateClientNotification);
    }

    @Value("${mq.queue.notification.create-client}")
    public void setQueueCreateClientNotification(String queueCreateClientNotification) {
        this.queueCreateClientNotification = queueCreateClientNotification;
    }

    @Value("${mq.routing-key.create-client}")
    public void setRoutingKeyCreateClientNotification(String routingKeyCreateClientNotification) {
        this.routingKeyCreateClientNotification = routingKeyCreateClientNotification;
    }
}

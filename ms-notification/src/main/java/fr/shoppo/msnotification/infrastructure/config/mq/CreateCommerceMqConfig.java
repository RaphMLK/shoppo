package fr.shoppo.msnotification.infrastructure.config.mq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CreateCommerceMqConfig extends ExchangeMqConfig {

    String queueCreateCommerceNotification;
    String routingKeyCommerceNotification;

    @Bean
    public Queue queueCreateCommerceNotification(){
        return new Queue(queueCreateCommerceNotification);
    }

    @Bean
    public Binding bindingCreateCommerce(DirectExchange exchangeNotification,
                                        Queue queueCreateCommerceNotification) {
        return BindingBuilder.bind(queueCreateCommerceNotification)
                .to(exchangeNotification)
                .with(routingKeyCommerceNotification);
    }

    @Value("${mq.queue.notification.create-commerce}")
    public void setQueueCreateCommerceNotification(String queueCreateCommerceNotification) {
        this.queueCreateCommerceNotification = queueCreateCommerceNotification;
    }

    @Value("${mq.routing-key.create-commerce}")
    public void setRoutingKeyCommerceNotification(String routingKeyCommerceNotification) {
        this.routingKeyCommerceNotification = routingKeyCommerceNotification;
    }
}

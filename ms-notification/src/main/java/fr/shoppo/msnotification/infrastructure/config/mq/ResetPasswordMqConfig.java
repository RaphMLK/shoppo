package fr.shoppo.msnotification.infrastructure.config.mq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ResetPasswordMqConfig extends ExchangeMqConfig {
    String queueResetPasswordNotification;
    String routingKeyResetPasswordNotification;

    @Bean
    public Queue queueResetPasswordNotification(){
        return new Queue(queueResetPasswordNotification);
    }

    @Bean
    public Binding bindingResetPassword(DirectExchange exchangeNotification,
                                         Queue queueResetPasswordNotification) {
        return BindingBuilder.bind(queueResetPasswordNotification)
                .to(exchangeNotification)
                .with(routingKeyResetPasswordNotification);
    }

    @Value("${mq.queue.notification.reset-password}")
    public void setQueueResetPasswordNotification(String queueResetPasswordNotification) {
        this.queueResetPasswordNotification = queueResetPasswordNotification;
    }
    @Value("${mq.routing-key.reset-password}")
    public void setRoutingKeyResetPasswordNotification(String routingKeyResetPasswordNotification) {
        this.routingKeyResetPasswordNotification = routingKeyResetPasswordNotification;
    }
}

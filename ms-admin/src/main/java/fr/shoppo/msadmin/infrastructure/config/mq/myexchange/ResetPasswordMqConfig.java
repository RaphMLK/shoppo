package fr.shoppo.msadmin.infrastructure.config.mq.myexchange;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ResetPasswordMqConfig extends ExchangeAdminMqConfig {
    String queueResetPassword;
    String routingKeyResetPassword;

    @Bean
    public Queue queueResetPassword(){
        Map<String, Object> args = new HashMap<>();
        args.put("x-message-ttl", timeToLive);
        return new Queue(queueResetPassword, true, false,true, args);
    }

    @Bean
    public Binding binding(
            DirectExchange exchangeAdmin,
            Queue queueResetPassword
    ) {
        return BindingBuilder.bind(queueResetPassword)
                .to(exchangeAdmin)
                .with(routingKeyResetPassword);
    }

    @Value("${mq.queue.reset-password}")
    public void setQueueResetPassword(String queueResetPassword) {
        this.queueResetPassword = queueResetPassword;
    }

    @Value("mq.routing-key.reset-password")
    public void setRoutingKeyResetPassword(String routingKeyResetPassword) {
        this.routingKeyResetPassword = routingKeyResetPassword;
    }
}

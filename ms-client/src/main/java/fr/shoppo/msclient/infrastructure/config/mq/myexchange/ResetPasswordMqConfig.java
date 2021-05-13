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
public class ResetPasswordMqConfig extends ExchangeClientMqConfig {
    String queueResetPassword;
    String routingKeyResetPassword;


    @Bean
    public Queue queueResetPassword(){
        Map<String, Object> args = new HashMap<>();
        args.put("x-message-ttl", timeToLive);
        return new Queue(queueResetPassword, true, false, true, args);
    }

    @Bean
    public Binding bindingResetPassword(
            DirectExchange exchangeClient,
            Queue queueResetPassword
    ) {
        return BindingBuilder.bind(queueResetPassword)
                .to(exchangeClient)
                .with(routingKeyResetPassword);
    }

    @Value("${mq.routing-key.reset-password}")
    public void setRoutingKeyResetPassword(String routingKeyResetPassword) {
        this.routingKeyResetPassword = routingKeyResetPassword;
    }
    @Value("${mq.queue.reset-password}")
    public void setQueueResetPassword(String queueResetPassword) {
        this.queueResetPassword = queueResetPassword;
    }

}

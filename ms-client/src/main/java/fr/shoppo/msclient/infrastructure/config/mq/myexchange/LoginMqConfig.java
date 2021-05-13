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
public class LoginMqConfig extends ExchangeClientMqConfig {

    String queueLogin;
    String routingKeyLogin;

    @Bean
    public Queue queueLogin() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-message-ttl", timeToLive);
        return new Queue(queueLogin, true, false, true, args);
    }

    @Bean
    public Binding bindingLogin(
            DirectExchange exchangeClient,
            Queue queueLogin
    ) {
        return BindingBuilder.bind(queueLogin)
                .to(exchangeClient)
                .with(routingKeyLogin);
    }

    @Value("${mq.queue.login}")
    public void setQueueLogin(String queueLogin) {
        this.queueLogin = queueLogin;
    }

    @Value("${mq.routing-key.login}")
    public void setRoutingKeyLogin(String routingKeyLogin) {
        this.routingKeyLogin = routingKeyLogin;
    }

}

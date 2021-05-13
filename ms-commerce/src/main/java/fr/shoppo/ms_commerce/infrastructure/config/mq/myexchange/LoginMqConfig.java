package fr.shoppo.ms_commerce.infrastructure.config.mq.myexchange;

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
public class LoginMqConfig extends ExchangeCommerceMqConfig {
    String queueLoginCommercant;
    String routingKeyLoginCommercant;

    /*Name is important cause
    Spring will match Type first
    and name of your
    parameter in second part*/
    /* to get less confusion name method like the attribute which match ( uniformisation )*/
    @Bean/*(1)*/
    public Queue queueLoginCommercant(){
        Map<String, Object> args = new HashMap<>();
        args.put("x-message-ttl", timeToLive);
        return new Queue(queueLoginCommercant, true, false, true, args);
    }

    @Bean
    public Binding bindingLogin(
            DirectExchange exchangeCommerce,/*(2)*/
            /*You can see it here, parameter and method had the same name*/
            Queue queueLoginCommercant/*(1)*/
    ) {
        return BindingBuilder.bind(queueLoginCommercant)
                .to(exchangeCommerce)
                .with(routingKeyLoginCommercant);
    }

    @Value("${mq.routing-key.login}")
    public void setRoutingKeyLogin(String routingKeyLogin) {
        this.routingKeyLoginCommercant = routingKeyLogin;
    }


    @Value("${mq.queue.login}")
    public void setQueueLogin(String queueLoginCommercant) {
        this.queueLoginCommercant = queueLoginCommercant;
    }

}

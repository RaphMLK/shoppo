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
public class ChangePasswordNeedMqConfig extends ExchangeCommerceMqConfig {

    String queueChangePasswordNeed;
    String routingKeyChangePasswordNeed;

    @Bean
    public Queue queueChangePasswordNeed(){
        Map<String, Object> args = new HashMap<>();
        args.put("x-message-ttl", timeToLive);
        return new Queue(queueChangePasswordNeed, true, false, true, args);
    }

    @Bean
    public Binding bindingChangePasswordNeed(DirectExchange exchangeCommerce, Queue queueChangePasswordNeed){
        return BindingBuilder.bind(queueChangePasswordNeed).to(exchangeCommerce).with(routingKeyChangePasswordNeed);
    }

    @Value("${mq.routing-key.change-password-need}")
    public void setRoutingKeyChangePasswordNeed(String routingKeyChangePasswordNeed) {
        this.routingKeyChangePasswordNeed = routingKeyChangePasswordNeed;
    }

    @Value("${mq.queue.change-password-need}")
    public void setQueueChangePasswordNeed(String queueChangePasswordNeed) {
        this.queueChangePasswordNeed = queueChangePasswordNeed;
    }
}

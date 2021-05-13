package fr.shoppo.ms_stat.infrastructure.config.routing;

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
public class ReadMqConfig extends ExchangeStatMqConfig{
    String queueReadStat;
    String routingKeyReadStat;


    @Bean
    public Binding bindingAddProduct(
            DirectExchange exchangeStat,
            Queue queueReadStat
    ){
        return BindingBuilder.bind(queueReadStat).to(exchangeStat).with(routingKeyReadStat);
    }

    @Bean
    public Queue queueReadStat(){
        Map<String, Object> args = new HashMap<>();
        args.put("x-message-ttl", timeToLive);
        return new Queue(queueReadStat, true, false, true, args);
    }

    @Value("${mq.queue.stat.read}")
    public void setQueueReadStat(String queueReadStat) {
        this.queueReadStat = queueReadStat;
    }

    @Value("${mq.routing-key.read}")
    public void setRoutingKeyReadStat(String routingKeyReadStat) {
        this.routingKeyReadStat = routingKeyReadStat;
    }
}

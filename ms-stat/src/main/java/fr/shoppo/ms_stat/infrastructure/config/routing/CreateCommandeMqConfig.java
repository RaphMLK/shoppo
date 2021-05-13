package fr.shoppo.ms_stat.infrastructure.config.routing;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CreateCommandeMqConfig extends ExchangeStatMqConfig{
    String queueCreateCommandeStat;
    String routingKeyCreateCommandeStat;


    @Bean
    public Binding bindingCreateClient(DirectExchange exchangeStat,
                                       Queue queueCreateCommandeStat) {
        return BindingBuilder.bind(queueCreateCommandeStat)
                .to(exchangeStat)
                .with(routingKeyCreateCommandeStat);
    }

    @Bean
    public Queue queueCreateCommandeStat(){
        return new Queue(queueCreateCommandeStat);
    }

    @Value("${mq.queue.stat.create-commande}")
    public void queueCreateCommandeStat(String queueCreateCommandeStat) {
        this.queueCreateCommandeStat = queueCreateCommandeStat;
    }

    @Value("${mq.routing-key.create-commande}")
    public void setRoutingKeyCreateCommandeStat(String routingKeyCreateCommandeStat) {
        this.routingKeyCreateCommandeStat = routingKeyCreateCommandeStat;
    }
}

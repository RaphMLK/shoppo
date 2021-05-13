package fr.shoppo.ms_commerce.infrastructure.config.mq.stat;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExchangeStatRabbitMq {

    String exchangerStat;

    @Bean
    public DirectExchange exchangeStat(){
        return new DirectExchange(exchangerStat,true, false);
    }

    @Value("${mq.exchange.stat}")
    public void setExchangerStat(String exchangerStat) {
        this.exchangerStat = exchangerStat;
    }
}

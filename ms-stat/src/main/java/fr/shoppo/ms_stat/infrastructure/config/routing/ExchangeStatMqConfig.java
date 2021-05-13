package fr.shoppo.ms_stat.infrastructure.config.routing;

import fr.shoppo.ms_stat.infrastructure.config.TimeToLiveConfiguration;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

public class ExchangeStatMqConfig extends TimeToLiveConfiguration {

    String exchangerStat;

    @Bean
    public DirectExchange exchangeStat(){
        return new DirectExchange(exchangerStat,true, false);
    }

    @Value("${mq.exchange.stat}")
    public void setExchangerCommerce(String exchangerStat) {
        this.exchangerStat = exchangerStat;
    }
}

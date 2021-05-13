package fr.shoppo.mainmsinterface.infrastructure.config.mq;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ExchangeAdminMqConfig extends TimeToLiveConfiguration {

    String exchangerAdmin;

    @Bean
    public DirectExchange exchangeAdmin(){
        Map<String, Object> args = new HashMap<>();
        args.put("x-message-ttl", timeToLive);
        return new DirectExchange(exchangerAdmin,true, true, args);
    }

    @Value("${mq.exchange.admin}")
    public void setExchangerAdmin(String exchangerAdmin) {
        this.exchangerAdmin = exchangerAdmin;
    }
}

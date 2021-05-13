package fr.shoppo.msadmin.infrastructure.config.mq.myexchange;

import fr.shoppo.msadmin.infrastructure.config.mq.TimeToLiveConfiguration;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import java.util.HashMap;
import java.util.Map;

public class ExchangeAdminMqConfig extends TimeToLiveConfiguration {

    String exchangerAdmin;

    @Bean
    public DirectExchange exchangeAdmin() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-message-ttl", timeToLive);
        return new DirectExchange(exchangerAdmin, true,true,args);
    }

    @Value("${mq.exchange.admin}")
    public void setExchangerAdmin(String exchangerAdmin) {
        this.exchangerAdmin = exchangerAdmin;
    }
}

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
public class GetClientByQrCodeMqConfig extends ExchangeClientMqConfig {

    String queueGetClientByQrCode;
    String routingGetClientByQrCode;

    @Bean
    public Queue queueGetClientByQrCode() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-message-ttl", timeToLive);
        return new Queue(queueGetClientByQrCode, true, false, true, args);
    }

    @Bean
    public Binding bindingGetClientByQrCode(
            DirectExchange exchangeClient,
            Queue queueGetClientByQrCode
    ) {
        return BindingBuilder.bind(queueGetClientByQrCode)
                .to(exchangeClient)
                .with(routingGetClientByQrCode);
    }

    @Value("${mq.queue.get-client-qrcode}")
    public void setQueueGetClientByQrCode(String queueGetClientByQrCode) {
        this.queueGetClientByQrCode = queueGetClientByQrCode;
    }

    @Value("${mq.routing-key.get-client-qrcode}")
    public void setRoutingKeyGetClientByQrCode(String routingGetClientByQrCode) {
        this.routingGetClientByQrCode = routingGetClientByQrCode;
    }


}

package fr.shoppo.msclient.infrastructure.service;

import fr.shoppo.msclient.domain.bo.UserNewPassword;
import fr.shoppo.msclient.domain.service.NotificationService;
import fr.shoppo.msclient.infrastructure.entity.Client;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final RabbitTemplate template;

    DirectExchange exchangeNotification;

    String routingKeyResetPassword;
    String routingKeyCreateClient;

    public NotificationServiceImpl(
            RabbitTemplate template
    ) {
        this.template = template;
    }

    @Override
    public void resetPassword(Client client) {
        this.template.convertAndSend(
                exchangeNotification.getName(),
                routingKeyResetPassword,
                new UserNewPassword(client.getEmail(), client.getPassword())
        );
    }

    @Override
    public void createClient(Client client) {
        this.template.convertAndSend(
                exchangeNotification.getName(),
                routingKeyCreateClient,
                client
        );
    }

    @Value("${mq.routing-key.notification.reset-password}")
    public void setRoutingKeyResetPassword(String routingKeyResetPassword) {
        this.routingKeyResetPassword = routingKeyResetPassword;
    }

    @Autowired
    public void setExchangeNotification(DirectExchange exchangeNotification) {
        this.exchangeNotification = exchangeNotification;
    }

    @Value("${mq.routing-key.notification.create-client}")
    public void setRoutingKeyCreateClient(String routingKeyCreateClient) {
        this.routingKeyCreateClient = routingKeyCreateClient;
    }
}

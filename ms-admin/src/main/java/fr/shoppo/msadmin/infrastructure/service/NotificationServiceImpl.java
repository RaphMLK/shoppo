package fr.shoppo.msadmin.infrastructure.service;

import fr.shoppo.msadmin.domain.bo.UserNewPassword;
import fr.shoppo.msadmin.domain.service.NotificationService;
import fr.shoppo.msadmin.infrastructure.entity.Admin;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService {

    private RabbitTemplate template;

    DirectExchange exchangeNotification;

    String routingKeyResetPassword;

    @Override
    public void resetPassword(Admin admin) {
        this.template.convertAndSend(
                exchangeNotification.getName(),
                routingKeyResetPassword,
                new UserNewPassword(admin.getEmail(), admin.getPassword())
        );
    }

    @Autowired
    public void setTemplate(RabbitTemplate template) {
        this.template = template;
    }

    @Autowired
    public void setExchangeNotification(DirectExchange exchangeNotification) {
        this.exchangeNotification = exchangeNotification;
    }

    @Value("${mq.routing-key.notification.reset-password}")
    public void setRoutingKeyResetPassword(String routingKeyResetPassword) {
        this.routingKeyResetPassword = routingKeyResetPassword;
    }
}

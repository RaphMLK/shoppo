package fr.shoppo.ms_commerce.infrastructure.service;

import fr.shoppo.ms_commerce.domain.bo.Information;
import fr.shoppo.ms_commerce.domain.bo.UserNewPassword;
import fr.shoppo.ms_commerce.domain.service.NotificationService;
import fr.shoppo.ms_commerce.infrastructure.entity.Commande;
import fr.shoppo.ms_commerce.infrastructure.entity.Commercant;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final RabbitTemplate template;

    DirectExchange exchangeNotification;

    String routingKeyResetPassword;
    String routingKeyCreateCommerce;
    String routingKeyCreateCommande;
    String routingKeyAddCommecant;

    public NotificationServiceImpl(
            RabbitTemplate template
    ) {
        this.template = template;
    }

    @Override
    public void resetPassword(Commercant commercant) {
         var user = new UserNewPassword(commercant.getEmail(), commercant.getPassword());
        this.template.convertAndSend(
                exchangeNotification.getName(),
                routingKeyResetPassword,
                user
        );
    }

    @Override
    public void createCommerce(Information information){
        this.template.convertAndSend(exchangeNotification.getName(),
                routingKeyCreateCommerce, information);
    }

    @Override
    public void createCommande(List<Commande> commandeList) {
        this.template.convertAndSend(exchangeNotification.getName(),
                routingKeyCreateCommande,commandeList);
    }

    @Override
    public void addCommercant(Information information) {
        this.template.convertAndSend(exchangeNotification.getName(),
                routingKeyAddCommecant, information);
    }

    @Autowired
    public void setExchangeNotification(DirectExchange exchangeNotification) {
        this.exchangeNotification = exchangeNotification;
    }

    @Value("${mq.routing-key.notification.reset-password}")
    public void setRoutingKeyResetPassword(String routingKeyResetPassword) {
        this.routingKeyResetPassword = routingKeyResetPassword;
    }

    @Value("${mq.routing-key.notification.create-commerce}")
    public void setRoutingKeyCreateCommerce(String routingKeyCreateCommerce) {
        this.routingKeyCreateCommerce = routingKeyCreateCommerce;
    }

    @Value("${mq.routing-key.notification.create-commande}")
    public void setRoutingKeyCreateCommande(String routingKeyCreateCommande) {
        this.routingKeyCreateCommande = routingKeyCreateCommande;
    }

    @Value("${mq.routing-key.notification.add-commercant}")
    public void setRoutingKeyAddCommecant(String routingKeyAddCommecant) {
        this.routingKeyAddCommecant = routingKeyAddCommecant;
    }
}

package fr.shoppo.msnotification.presentation;

import fr.shoppo.msnotification.domain.bo.UserNewPassword;
import fr.shoppo.msnotification.domain.bo.client.Client;
import fr.shoppo.msnotification.domain.bo.commerce.Commercant;
import fr.shoppo.msnotification.domain.bo.commerce.Commerce;
import fr.shoppo.msnotification.domain.bo.commerce.Information;
import fr.shoppo.msnotification.domain.service.ManageUserNotification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class ManageUserNotificationController {
    private static final Logger logger = LoggerFactory.getLogger(ManageUserNotificationController.class);

    final ManageUserNotification manageUserNotification;

    public ManageUserNotificationController(ManageUserNotification manageUserNotification) {
        this.manageUserNotification = manageUserNotification;
    }

    @RabbitListener(queues = "${mq.queue.notification.reset-password}")
    public void notificationResetPassword(UserNewPassword userNewPassword){
        manageUserNotification.sendNewPassword(userNewPassword.getEmail(), userNewPassword.getPassword());
    }

    @RabbitListener(queues = "${mq.queue.notification.create-commerce}")
    public void notificationCreateCommerce(Information information){
        manageUserNotification.sendInformationAboutNewCommerce(information);
    }

    @RabbitListener(queues = "${mq.queue.notification.add-commercant}")
    public void notificationAddCommercant(Information information){
        var commercant = information.getCommercant();
        var mail = commercant.getEmail();
        var mailSplit = mail.split("@");
        var name = Arrays.stream(mailSplit).findFirst().orElse("New Customer");
        commercant.setName(name);
        manageUserNotification.sendInformationAboutAddCommercant(commercant);
    }

    @RabbitListener(queues = "${mq.queue.notification.create-client}")
    public void notificationCreateClient(Client client){
        var mail = client.getEmail();
        var mailSplit = mail.split("@");
        var name = Arrays.stream(mailSplit).findFirst().orElse("New Customer");
        client.setName(name);
        logger.info("Received client to create : {}",client);
        manageUserNotification.sendInformationAboutNewClient(client);
    }
}

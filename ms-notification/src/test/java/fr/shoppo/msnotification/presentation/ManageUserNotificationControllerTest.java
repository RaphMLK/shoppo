package fr.shoppo.msnotification.presentation;

import fr.shoppo.msnotification.domain.bo.UserNewPassword;
import fr.shoppo.msnotification.domain.bo.client.Client;
import fr.shoppo.msnotification.domain.bo.commerce.Commercant;
import fr.shoppo.msnotification.domain.bo.commerce.Commerce;
import fr.shoppo.msnotification.domain.bo.commerce.Information;
import fr.shoppo.msnotification.domain.service.ManageUserNotification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class ManageUserNotificationControllerTest {

    ManageUserNotification manageUserNotification;
    ManageUserNotificationController manageUserNotificationController;
    @BeforeEach
    void setup(){
        manageUserNotification = mock(ManageUserNotification.class);
        manageUserNotificationController = new ManageUserNotificationController(manageUserNotification);

        doNothing().when(manageUserNotification).sendInformationAboutNewCommerce(any());
        doNothing().when(manageUserNotification).sendInformationAboutAddCommercant(any());
        doNothing().when(manageUserNotification).sendInformationAboutNewClient(any());
        doNothing().when(manageUserNotification).sendNewPassword(anyString(),anyString());
    }

    @Test
    void notificationResetPassword(){
        var unp = new UserNewPassword();
        unp.setEmail("test");
        unp.setPassword("test");

        manageUserNotificationController.notificationResetPassword(unp);

        verify(manageUserNotification).sendNewPassword(unp.getEmail(), unp.getPassword());
    }

    @Test
    void notificationCreateCommerce(){
        var information = new Information();
        information.setCommerce(new Commerce());

        manageUserNotificationController.notificationCreateCommerce(information);

        verify(manageUserNotification).sendInformationAboutNewCommerce(information);
    }

    @Test
    void notificationAddCommercant(){
        var commercant = new Commercant();
        var information = new Information();

        commercant.setEmail("test@test");

        information.setCommercant(commercant);

        manageUserNotificationController.notificationAddCommercant(information);

        verify(manageUserNotification).sendInformationAboutAddCommercant(commercant);
    }

    @Test
    void notificationCreateClient(){
        var client = new Client();
        client.setEmail("test@test");

        manageUserNotificationController.notificationCreateClient(client);

        verify(manageUserNotification).sendInformationAboutNewClient(client);
    }


}
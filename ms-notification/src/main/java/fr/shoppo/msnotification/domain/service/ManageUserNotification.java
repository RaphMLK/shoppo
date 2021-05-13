package fr.shoppo.msnotification.domain.service;

import fr.shoppo.msnotification.domain.bo.client.Client;
import fr.shoppo.msnotification.domain.bo.commerce.Commercant;
import fr.shoppo.msnotification.domain.bo.commerce.Commerce;
import fr.shoppo.msnotification.domain.bo.commerce.Information;

public interface ManageUserNotification {

    void sendNewPassword(String email, String password);

    void sendInformationAboutNewCommerce(Information information);
    void sendInformationAboutNewClient(Client client);
    void sendInformationAboutAddCommercant(Commercant commercant);
}

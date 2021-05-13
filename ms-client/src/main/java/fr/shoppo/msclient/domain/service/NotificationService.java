package fr.shoppo.msclient.domain.service;

import fr.shoppo.msclient.infrastructure.entity.Client;

public interface NotificationService {
    void resetPassword(Client client);
    void createClient(Client client);
}

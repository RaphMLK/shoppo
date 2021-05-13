package fr.shoppo.msclient.domain.service;

import fr.shoppo.msclient.infrastructure.entity.Client;

public interface VFPStateManager {

    String command(Client client);
    String parse(Client client);
    String reset(Client client);

}

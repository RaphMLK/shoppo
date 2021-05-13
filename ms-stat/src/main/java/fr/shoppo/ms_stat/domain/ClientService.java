package fr.shoppo.ms_stat.domain;

import fr.shoppo.ms_stat.infrastructure.entity.Client;

import java.util.Optional;

public interface ClientService {
    int countVFPClient();
    Optional<Client> findById(int id);
}

package fr.shoppo.msclient.infrastructure.dao;

import fr.shoppo.msclient.infrastructure.entity.Client;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClientDao extends CrudRepository<Client, Integer> {
    Optional<Client> findByEmail(String email);

    Optional<Client> findByQrCode(UUID qrCode);
}

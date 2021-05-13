package fr.shoppo.ms_stat.infrastructure.dao;

import fr.shoppo.ms_stat.infrastructure.entity.Client;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClientDao extends CrudRepository<Client, Integer> {
    Optional<Client> findByEmail(String email);

    Optional<Client> findByQrCode(UUID qrCode);

    List<Client> findAllByStatusVFP(boolean status);
}

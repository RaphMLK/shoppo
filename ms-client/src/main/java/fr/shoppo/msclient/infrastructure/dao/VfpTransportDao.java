package fr.shoppo.msclient.infrastructure.dao;

import fr.shoppo.msclient.infrastructure.entity.VfpTransport;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface VfpTransportDao extends CrudRepository<VfpTransport, Integer> {

    Iterable<VfpTransport> findVfpTransportByClient_QrCode(UUID qrCode);
}

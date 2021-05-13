package fr.shoppo.msclient.infrastructure.dao;

import fr.shoppo.msclient.infrastructure.entity.VfpPlaque;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VfpPlaqueDao extends CrudRepository<VfpPlaque, Integer> {

    Iterable<VfpPlaque> findVfpPlaqueByClient_Plaque(String plaque);
}

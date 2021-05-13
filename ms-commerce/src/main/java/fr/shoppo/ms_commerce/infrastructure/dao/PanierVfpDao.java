package fr.shoppo.ms_commerce.infrastructure.dao;

import fr.shoppo.ms_commerce.infrastructure.entity.PanierVfp;
import fr.shoppo.ms_commerce.infrastructure.entity.PanierVfpPK;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface PanierVfpDao extends CrudRepository<PanierVfp, PanierVfpPK> {

    Optional<PanierVfp> findPanierVfpByPanierVfpPK_IdClient(int idClient);
}

package fr.shoppo.ms_commerce.infrastructure.dao;

import fr.shoppo.ms_commerce.infrastructure.entity.Commercant;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommercantDao extends CrudRepository<Commercant, Integer> {

    Commercant findByEmail(String email);
    List<Commercant> findByCommerceSiretCode(String siretCode);
}

package fr.shoppo.ms_commerce.infrastructure.dao;

import fr.shoppo.ms_commerce.infrastructure.entity.Commerce;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CommerceDao extends CrudRepository<Commerce, Integer> {

    Optional<Commerce> findBySiretCode(String siretCode);

}

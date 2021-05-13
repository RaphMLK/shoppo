package fr.shoppo.ms_commerce.infrastructure.dao;

import fr.shoppo.ms_commerce.infrastructure.entity.CodeNaf;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CodeNafDao extends CrudRepository<CodeNaf,Integer> {
    Optional<CodeNaf> findByCode(String code);

}

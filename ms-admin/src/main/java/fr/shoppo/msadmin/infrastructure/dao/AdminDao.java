package fr.shoppo.msadmin.infrastructure.dao;

import fr.shoppo.msadmin.infrastructure.entity.Admin;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminDao extends CrudRepository<Admin, Integer> {

    Optional<Admin> findByEmail(String email);
}

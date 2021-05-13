package fr.shoppo.mainmsinterface.infrastructure.dao;

import fr.shoppo.mainmsinterface.infrastructure.entity.Connexion;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConnexionDao extends CrudRepository<Connexion, Integer> {

    Connexion findByToken(String token);
}

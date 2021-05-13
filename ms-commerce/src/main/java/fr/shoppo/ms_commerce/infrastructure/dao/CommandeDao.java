package fr.shoppo.ms_commerce.infrastructure.dao;

import fr.shoppo.ms_commerce.infrastructure.entity.Commande;
import fr.shoppo.ms_commerce.infrastructure.entity.Commerce;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CommandeDao extends CrudRepository<Commande, Integer>  {

    List<Commande> findByCommerce(Commerce commerce);
    List<Commande> findByClientId(int id);
    List<Commande> findCommandeByCommerceAndClientId(Commerce commerce, int idClient);

}

package fr.shoppo.ms_commerce.infrastructure.dao;

import fr.shoppo.ms_commerce.infrastructure.entity.Panier;
import fr.shoppo.ms_commerce.infrastructure.entity.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface PanierDao extends CrudRepository<Panier, Integer> {

    List<Panier> findByIdClient(int idClient);
    Optional<Panier> findByIdClientAndProduct(int idClient, Product product);

}

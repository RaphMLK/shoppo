package fr.shoppo.ms_stat.infrastructure.dao;


import fr.shoppo.ms_stat.infrastructure.entity.Commande;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface CommandeDao extends CrudRepository<Commande, Integer>  {
    @Query(value = "FROM Commande t WHERE t.dateCreation BETWEEN :start AND :end")
    List<Commande> findByDateCreationBetween(Date start, Date end);
}

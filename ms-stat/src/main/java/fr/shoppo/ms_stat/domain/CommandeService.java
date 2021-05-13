package fr.shoppo.ms_stat.domain;


import fr.shoppo.ms_stat.infrastructure.entity.Commande;

import java.util.Date;
import java.util.Map;

public interface CommandeService {
    Map<String,Commande> findAllCommande();
    Map<String, Commande> findAllCommandeSinceLastSave(Date lastSave);
}

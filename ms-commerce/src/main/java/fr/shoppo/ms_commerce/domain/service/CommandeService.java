package fr.shoppo.ms_commerce.domain.service;

import fr.shoppo.ms_commerce.domain.bo.TypeCommandeEnum;
import fr.shoppo.ms_commerce.infrastructure.entity.Commande;
import fr.shoppo.ms_commerce.infrastructure.entity.Panier;
import fr.shoppo.ms_commerce.infrastructure.entity.PanierVfp;

import java.util.List;
import java.util.Optional;

public interface CommandeService {

    List<Commande> getCommandesOfCommerce(String email);
    List<Commande> getCommandesOfClient(int id);
    Optional<Commande> save(Commande commande);
    Optional<Commande> getCommandeById(int id);
    String updateCommandeTraitee(int id);
    String createCommandeByPanier(List<Panier> panier, Optional<PanierVfp> panierVfpOptional, TypeCommandeEnum typeCommandeEnum,
                                  boolean istraiter);

}

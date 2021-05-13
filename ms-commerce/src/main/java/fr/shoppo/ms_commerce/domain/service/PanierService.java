package fr.shoppo.ms_commerce.domain.service;

import fr.shoppo.ms_commerce.domain.bo.panier.PanierIdQuantity;
import fr.shoppo.ms_commerce.domain.bo.ReturnBasicExchange;
import fr.shoppo.ms_commerce.infrastructure.entity.Panier;
import fr.shoppo.ms_commerce.infrastructure.entity.PanierVfp;

import java.util.List;
import java.util.Optional;

public interface PanierService {

    List<Panier> getPanierCommande(int idClient);
    Optional<PanierVfp> getPanierVfp(int idClient);
    boolean validatePanierProduct(Panier panier);
    boolean validatePanierVfp(PanierVfp panierVfp);
    void deletePanier(Panier panier);
    ReturnBasicExchange updatePanierCommande(int idClient, int idProduit, int quantite);
    List<Panier> createPanierByCommerce(String emailCommerce, int idClient, List<PanierIdQuantity> panierIdQuantityList);
    ReturnBasicExchange addVfp(int idClient, int idVfp);
    ReturnBasicExchange deleteVfp(int idClient);
}

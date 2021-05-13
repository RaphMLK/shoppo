package fr.shoppo.mainmsinterface.domain.service;

import fr.shoppo.mainmsinterface.domain.bo.commerce.CreateCommandeByCommerceOutput;

public interface PanierService {
    String getPanier(int idClient);
    String updatePanierProduct(int idClient, int idProduit, int quantite);
    String getPanierByCommerce(CreateCommandeByCommerceOutput getPanierByCommerceOutput);
    String addOrDeleteVfpPanier(int idClient, int idVfp, boolean addOrDelete);
}

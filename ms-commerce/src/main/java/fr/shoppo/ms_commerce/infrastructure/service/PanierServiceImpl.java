package fr.shoppo.ms_commerce.infrastructure.service;

import fr.shoppo.ms_commerce.domain.bo.ReturnBasicExchange;
import fr.shoppo.ms_commerce.domain.bo.panier.PanierIdQuantity;
import fr.shoppo.ms_commerce.domain.service.PanierService;
import fr.shoppo.ms_commerce.infrastructure.dao.*;
import fr.shoppo.ms_commerce.infrastructure.entity.Panier;
import fr.shoppo.ms_commerce.infrastructure.entity.PanierVfp;
import fr.shoppo.ms_commerce.infrastructure.entity.PanierVfpPK;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import static fr.shoppo.ms_commerce.domain.constant.MessageConstantEnum.*;

@Service
public class PanierServiceImpl implements PanierService {

    private PanierDao panierDao;
    private ProductDao productDao;
    private CommercantDao commercantDao;
    private ProductVfpDao productVfpDao;
    private PanierVfpDao panierVfpDao;
    private CommandeDao commandeDao;

    @Override
    public List<Panier> getPanierCommande(int idClient) {
        return panierDao.findByIdClient(idClient);
    }

    @Override
    public Optional<PanierVfp> getPanierVfp(int idClient) {
        return panierVfpDao.findPanierVfpByPanierVfpPK_IdClient(idClient);
    }

    @Override
    public boolean validatePanierProduct(Panier panier) {
        var product = panier.getProduct();
        if (product == null)
            return false;
        var attr = product.getAttribute();
        if (attr == null)
            return false;
        var stock = attr.getStock();
        var qte = panier.getQuantite();

        return qte >= 0 && stock > 0 && qte <= stock;
    }

    @Override
    public boolean validatePanierVfp(PanierVfp panierVfp) {
        AtomicBoolean ok = new AtomicBoolean(false);
        var productVfp = panierVfp.getPanierVfpPK().getProductVfp();
        Optional.of(productVfp.getProduct()).ifPresentOrElse(
                product -> ok.set(product.getAttribute().getStock() > 0),
                () -> ok.set(productVfp.getStock() > 0)
        );
        return ok.get();
    }

    @Override
    public void deletePanier(Panier panier) {
        panierDao.delete(panier);
    }

    @Override
    public ReturnBasicExchange updatePanierCommande(int idClient, int idProduit, int quantite) {
        var response = new ReturnBasicExchange().status(ERREUR_TYPE.toString());
        var finalQuantite = Math.max(quantite, 0);
        productDao
                .findById(idProduit)
                .ifPresentOrElse(
                        product -> {
                            var attribute = product.getAttribute();
                            panierDao
                                    .findByIdClientAndProduct(idClient, product)
                                    .ifPresentOrElse(panier -> {
                                                if (finalQuantite == 0) {
                                                    panierDao.delete(panier);
                                                    response
                                                            .status(OK.toString())
                                                            .content(null);
                                                } else if (attribute.getStock() <= finalQuantite)
                                                    response.content(ERREUR_ADD_PANIER_STOCK_TROP_PETIT.toString());
                                                else {
                                                    panier.setQuantite(finalQuantite);
                                                    panierDao.save(panier);
                                                    response
                                                            .status(OK.toString())
                                                            .content(null);
                                                }
                                            },
                                            () -> {
                                                if (attribute.getStock() <= finalQuantite)
                                                    response.content(ERREUR_ADD_PANIER_STOCK_TROP_PETIT.toString());
                                                else if (finalQuantite <= 0)
                                                    response.content(ERREUR_ADD_PANIER_QUANTITE_NULL.toString());
                                                else {
                                                    panierDao.save(Panier.of(idClient, product, quantite));
                                                    response
                                                            .status(OK.toString())
                                                            .content(null);
                                                }
                                            });
                        },
                        () -> response.content(ERREUR_PRODUCT_NOT_FOUND.toString()));

        return response;
    }


    @Override
    public List<Panier> createPanierByCommerce(String emailCommerce, int idClient, List<PanierIdQuantity> panierIdQuantityList) {
        var productList = new ArrayList<Panier>();
        panierIdQuantityList.forEach(panierIdQuantity ->
                productDao
                        .findById(panierIdQuantity.getId())
                        .ifPresent(product -> {
                                    if (product.getCommerce().getSiretCode().equals(commercantDao.findByEmail(emailCommerce).getCommerce().getSiretCode()))
                                        productList.add(Panier.of(idClient, product, panierIdQuantity.getQuantity()));
                                }
                        )
        );
        return productList;
    }

    @Override
    public ReturnBasicExchange addVfp(int idClient, int idVfp) {
        var response = new ReturnBasicExchange().status(ERREUR_TYPE.toString());
        productVfpDao.findById(idVfp).ifPresentOrElse(
                productVfp ->
                        panierVfpDao.findPanierVfpByPanierVfpPK_IdClient(idClient).ifPresentOrElse(
                                panierVfp -> response.content("Vous avez déjà un VFP dans le panier"),
                                () -> {
                                    var commandeList = commandeDao.findCommandeByCommerceAndClientId(productVfp.getCommerce(), idClient);
                                    if(commandeList.size()>0) {
                                        AtomicInteger stock = new AtomicInteger(0);
                                        Optional.ofNullable(productVfp.getProduct()).ifPresentOrElse(
                                                product -> stock.set(product.getAttribute().getStock()),
                                                () -> stock.set(productVfp.getStock())
                                        );
                                        if(stock.get() > 0) {
                                            panierVfpDao.save(new PanierVfp(new PanierVfpPK(productVfp, idClient)));
                                            response.status(OK.toString());
                                        } else
                                            response.content("Le VFP n'est pas disponible");
                                    } else
                                        response.content("Vous devez avoir effectué une commande dans ce commerce pour ajouter un VFP");
                                }
                        ),
                () -> response.content("VFP non trouvé")
        );
        return response;
    }

    @Override
    public ReturnBasicExchange deleteVfp(int idClient) {
        var response = new ReturnBasicExchange().status(ERREUR_TYPE.toString());
        panierVfpDao.findPanierVfpByPanierVfpPK_IdClient(idClient).ifPresentOrElse(
                panierVfp -> {
                    panierVfpDao.delete(panierVfp);
                    response.status(OK.toString());
                },
                () -> response.setContent("Vous n'avez pas de VFP dans le panier")
        );
        return response;
    }

    @Autowired
    public void setPanierDao(PanierDao panierDao) {
        this.panierDao = panierDao;
    }

    @Autowired
    public void setProductDao(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Autowired
    public void setCommercantDao(CommercantDao commercantDao) {
        this.commercantDao = commercantDao;
    }

    @Autowired
    public void setProductVfpDao(ProductVfpDao productVfpDao) {
        this.productVfpDao = productVfpDao;
    }

    @Autowired
    public void setPanierVfpDao(PanierVfpDao panierVfpDao) {
        this.panierVfpDao = panierVfpDao;
    }

    @Autowired
    public void setCommandeDao(CommandeDao commandeDao) {
        this.commandeDao = commandeDao;
    }
}

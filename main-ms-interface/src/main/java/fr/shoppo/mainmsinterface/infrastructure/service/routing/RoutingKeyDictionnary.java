package fr.shoppo.mainmsinterface.infrastructure.service.routing;

import org.springframework.beans.factory.annotation.Value;

public class RoutingKeyDictionnary {

    String routingKeyLoginAdmin;
    String routingKeyLoginClient;
    String routingKeyLoginCommercant;
    
    String routingKeyCreateClient;
    String routingKeyCreateCommerce;
    String routingKeyCreateCommande;
    String routingKeyCreateCommandeByCommerce;

    String routingKeyGetCommande;

    String routingKeyUpdateCommandeTraitee;

    String routingKeyUpdateCommerce;

    String routingKeyAddCommercant;
    String routingKeyDeleteCommercant;
    String routingKeyFindCommercant;
    String routingKeyGetCommercant;
    String routingKeyAddProduct;
    String routingKeyGetAllCommerces;

    String routingKeyEditProduct;
    String routingKeyDeleteProduct;
    String routingKeyGetProduct;

    String routingKeyUpdateQuantity;
    String routingKeyGetProductsCommerce;
    String routingKeyGetProductsCommerceNotVfp;


    String routingKeyResetPasswordAdmin;
    String routingKeyResetPasswordClient;
    String routingKeyResetPasswordCommercant;
    String routingKeyGetCommandesCommerce;
    String routingKeyGetCommandesClient;
    String routingKeyChangePasswordNeedClient;
    String routingKeyChangePasswordNeedCommercant;
    String routingKeyGetClient;
    String routingKeyGetClientByQrCode;

    String routingKeyGetPanier;
    String routingKeyUpdatePanier;
    String routingKeyGetPanierByCommerce;
    String routingKeyAddOrDeleteVfpPanier;

    String routingKeyUpdateSolde;

    String routingAddVfpByProductAndCommerce;
    String routingKeyGetVfpCommerce;
    String routingdeleteVfpByCommerce;

    String routingKeyGetAllAvantage;
    String routingKeyUpdateAvantage;
    String routingKeyIsPark;
    String routingKeyIsTransport;

    @Value("${mq.routing-key.is-park}")
    public void setRoutingKeyIsPark(String routingKeyIsPark) {
        this.routingKeyIsPark = routingKeyIsPark;
    }

    @Value("${mq.routing-key.is-transport}")
    public void setRoutingKeyIsTransport(String routingKeyIsTransport) {
        this.routingKeyIsTransport = routingKeyIsTransport;
    }

    String routingKeyReadStat;

    @Value("${mq.routing-key.read-stat}")
    public void setRoutingKeyReadStat(String routingKeyReadStat) {
        this.routingKeyReadStat = routingKeyReadStat;
    }

    @Value("${mq.routing-key.get-all-avantage}")
    public void setRoutingKeyGetAllAvantage(String routingKeyGetAllAvantage) {
        this.routingKeyGetAllAvantage = routingKeyGetAllAvantage;
    }

    @Value("${mq.routing-key.update-avantage}")
    public void setRoutingKeyUpdateAvantage(String routingKeyUpdateAvantage) {
        this.routingKeyUpdateAvantage = routingKeyUpdateAvantage;
    }

    @Value("${mq.routing-key.add-delete-vfp-panier}")
    public void setRoutingKeyAddOrDeleteVfpPanier(String routingKeyAddOrDeleteVfpPanier) {
        this.routingKeyAddOrDeleteVfpPanier = routingKeyAddOrDeleteVfpPanier;
    }

    @Value("${mq.routing-key.get-product-commerce-vfp}")
    public void setRoutingKeyGetVfpCommerce(String routingKeyGetVfpCommerce) {
        this.routingKeyGetVfpCommerce = routingKeyGetVfpCommerce;
    }

    @Value("${mq.routing-key.get-product-commerce-not-vfp}")
    public void setRoutingKeyGetProductsCommerceNotVfp(String routingKeyGetProductsCommerceNotVfp) {
        this.routingKeyGetProductsCommerceNotVfp = routingKeyGetProductsCommerceNotVfp;
    }

    @Value("${mq.routing-key.delete-vfp-by-commerce}")
    public void setRoutingdeleteVfpByCommerce(String routingdeleteVfpByCommerce) {
        this.routingdeleteVfpByCommerce = routingdeleteVfpByCommerce;
    }

    @Value("${mq.routing-key.add-vfp-by-product-and-commerce}")
    public void setRoutingAddVfpByProductAndCommerce(String routingAddVfpByProductAndCommerce) {
        this.routingAddVfpByProductAndCommerce = routingAddVfpByProductAndCommerce;
    }

    @Value("${mq.routing-key.create-commande-by-commerce}")
    public void setRoutingKeyCreateCommandeByCommerce(String routingKeyCreateCommandeByCommerce) {
        this.routingKeyCreateCommandeByCommerce = routingKeyCreateCommandeByCommerce;
    }

    @Value("${mq.routing-key.get-client-qrcode}")
    public void setRoutingKeyGetClientByQrCode(String routingKeyGetClientByQrCode) {
        this.routingKeyGetClientByQrCode = routingKeyGetClientByQrCode;
    }

    @Value("${mq.routing-key.get-panier-by-commerce}")
    public void setRoutingKeyGetPanierByCommerce(String routingKeyGetPanierByCommerce) {
        this.routingKeyGetPanierByCommerce = routingKeyGetPanierByCommerce;
    }

    @Value("${mq.routing-key.update-panier}")
    public void setRoutingKeyUpdatePanier(String routingKeyUpdatePanier) {
        this.routingKeyUpdatePanier = routingKeyUpdatePanier;
    }

    @Value("${mq.routing-key.get-panier}")
    public void setRoutingKeyGetPanier(String routingKeyGetPanier) {
        this.routingKeyGetPanier = routingKeyGetPanier;
    }

    @Value("${mq.routing-key.get-all-commerces}")
    public void setRoutingKeyGetAllCommerces(String routingKeyGetAllCommerces) {
        this.routingKeyGetAllCommerces = routingKeyGetAllCommerces;
    }

    @Value("${mq.routing-key.login-admin}")
    public void setRoutingKeyLoginAdmin(String routingKeyLoginAdmin) {
        this.routingKeyLoginAdmin = routingKeyLoginAdmin;
    }

    @Value("${mq.routing-key.get-products-commerce}")
    public void setRoutingKeyGetProductsCommerce(String routingKeyGetProductsCommerce) {
        this.routingKeyGetProductsCommerce = routingKeyGetProductsCommerce;
    }

    @Value("${mq.routing-key.login-client}")
    public void setRoutingKeyLoginClient(String routingKeyLoginClient) {
        this.routingKeyLoginClient = routingKeyLoginClient;
    }

    @Value("${mq.routing-key.login-commercant}")
    public void setRoutingKeyLoginCommercant(String routingKeyLoginCommercant) {
        this.routingKeyLoginCommercant = routingKeyLoginCommercant;
    }

    @Value("${mq.routing-key.create-client}")
    public void setRoutingKeyCreateClient(String routingKeyCreateClient) {
        this.routingKeyCreateClient = routingKeyCreateClient;
    }

    @Value("${mq.routing-key.create-commerce}")
    public void setRoutingKeyCreateCommerce(String routingKeyCreateCommerce) {
        this.routingKeyCreateCommerce = routingKeyCreateCommerce;
    }

    @Value("${mq.routing-key.create-commande}")
    public void setRoutingKeyCreateCommande(String routingKeyCreateCommande) {
        this.routingKeyCreateCommande = routingKeyCreateCommande;
    }

    @Value("${mq.routing-key.get-commande}")
    public void setRoutingKeyGetCommande(String routingKeyGetCommande) {
        this.routingKeyGetCommande = routingKeyGetCommande;
    }

    @Value("${mq.routing-key.update-commande-traitee}")
    public void setRoutingKeyUpdateCommandeTraitee(String routingKeyUpdateCommandeTraitee) {
        this.routingKeyUpdateCommandeTraitee = routingKeyUpdateCommandeTraitee;
    }

    @Value("${mq.routing-key.update-commerce}")
    public void setRoutingKeyUpdateCommerce(String routingKeyUpdateCommerce) {
        this.routingKeyUpdateCommerce = routingKeyUpdateCommerce;
    }

    @Value("${mq.routing-key.add-commercant}")
    public void setRoutingKeyAddCommercant(String routingKeyAddCommercant) {
        this.routingKeyAddCommercant = routingKeyAddCommercant;
    }

    @Value("${mq.routing-key.delete-commercant}")
    public void setRoutingKeyDeleteCommercant(String routingKeyDeleteCommercant) {
        this.routingKeyDeleteCommercant = routingKeyDeleteCommercant;
    }

    @Value("${mq.routing-key.find-commercant}")
    public void setRoutingKeyFindCommercant(String routingKeyFindCommercant) {
        this.routingKeyFindCommercant = routingKeyFindCommercant;
    }

    @Value("${mq.routing-key.get-commercant}")
    public void setRoutingKeyGetCommercant(String routingKeyGetCommercant) {
        this.routingKeyGetCommercant = routingKeyGetCommercant;
    }

    @Value("${mq.routing-key.add-product}")
    public void setRoutingKeyAddProduct(String routingKeyAddProduct) {
        this.routingKeyAddProduct = routingKeyAddProduct;
    }

    @Value("${mq.routing-key.edit-product}")
    public void setRoutingKeyEditProduct(String routingKeyEditProduct) {
        this.routingKeyEditProduct = routingKeyEditProduct;
    }

    @Value("${mq.routing-key.delete-product}")
    public void setRoutingKeyDeleteProduct(String routingKeyDeleteProduct) {
        this.routingKeyDeleteProduct = routingKeyDeleteProduct;
    }

    @Value("${mq.routing-key.get-product}")
    public void setRoutingKeyGetProduct(String routingKeyGetProduct) {
        this.routingKeyGetProduct = routingKeyGetProduct;
    }

    @Value("${mq.routing-key.reset-password-admin}")
    public void setRoutingKeyResetPasswordAdmin(String routingKeyResetPasswordAdmin) {
        this.routingKeyResetPasswordAdmin = routingKeyResetPasswordAdmin;
    }

    @Value("${mq.routing-key.reset-password-client}")
    public void setRoutingKeyResetPasswordClient(String routingKeyResetPasswordClient) {
        this.routingKeyResetPasswordClient = routingKeyResetPasswordClient;
    }

    @Value("${mq.routing-key.reset-password-commercant}")
    public void setRoutingKeyResetPasswordCommercant(String routingKeyResetPasswordCommercant) {
        this.routingKeyResetPasswordCommercant = routingKeyResetPasswordCommercant;
    }

    @Value("${mq.routing-key.change-password-need-client}")
    public void setRoutingKeyChangePasswordNeedClient(String routingKeyChangePasswordNeedClient) {
        this.routingKeyChangePasswordNeedClient = routingKeyChangePasswordNeedClient;
    }

    @Value("${mq.routing-key.change-password-need-commercant}")
    public void setRoutingKeyChangePasswordNeedCommercant(String routingKeyChangePasswordNeedCommercant) {
        this.routingKeyChangePasswordNeedCommercant = routingKeyChangePasswordNeedCommercant;
    }

    @Value("${mq.routing-key.update-quantity}")
    public void setRoutingKeyUpdateQuantity(String routingKeyUpdateQuantity) {
        this.routingKeyUpdateQuantity = routingKeyUpdateQuantity;
    }

    @Value("${mq.routing-key.get-commandes-commerce}")
    public void setRoutingKeyGetCommandesCommerce(String routingKeyGetCommandesCommerce) {
        this.routingKeyGetCommandesCommerce = routingKeyGetCommandesCommerce;
    }

    @Value("${mq.routing-key.get-commandes-client}")
    public void setRoutingKeyGetCommandesClient(String routingKeyGetCommandesClient) {
        this.routingKeyGetCommandesClient = routingKeyGetCommandesClient;
    }

    @Value("${mq.routing-key.get-client}")
    public void setRoutingKeyGetClient(String routingKeyGetClient) {
        this.routingKeyGetClient = routingKeyGetClient;
    }

    @Value("${mq.routing-key.update-solde-client}")
    public void setRoutingKeyUpdateSolde(String routingKeyUpdateSolde) {
        this.routingKeyUpdateSolde = routingKeyUpdateSolde;
    }
}

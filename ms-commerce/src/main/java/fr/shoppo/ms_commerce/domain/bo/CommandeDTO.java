package fr.shoppo.ms_commerce.domain.bo;

import fr.shoppo.ms_commerce.infrastructure.entity.Commerce;
import fr.shoppo.ms_commerce.infrastructure.entity.ProductQuantite;
import fr.shoppo.ms_commerce.infrastructure.entity.TypeCommande;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

public class CommandeDTO {

    private Integer id;

    Date dateCreation;

    Date dateRepriseClient;

    Boolean traitee;

    int clientId;

    BigDecimal prix;

    Set<ProductQuantite> products;

    Commerce commerce;

    TypeCommande typeCommande;

    ProductVfpDTO productVfpDTO;

    public CommandeDTO(Integer id, Date dateCreation, Date dateRepriseClient, Boolean traitee, int clientId, BigDecimal prix, Set<ProductQuantite> products, Commerce commerce, TypeCommande typeCommande, ProductVfpDTO productVfpDTO) {
        this.id = id;
        this.dateCreation = dateCreation;
        this.dateRepriseClient = dateRepriseClient;
        this.traitee = traitee;
        this.clientId = clientId;
        this.prix = prix;
        this.products = products;
        this.commerce = commerce;
        this.typeCommande = typeCommande;
        this.productVfpDTO = productVfpDTO;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public Date getDateRepriseClient() {
        return dateRepriseClient;
    }

    public void setDateRepriseClient(Date dateRepriseClient) {
        this.dateRepriseClient = dateRepriseClient;
    }

    public Boolean getTraitee() {
        return traitee;
    }

    public void setTraitee(Boolean traitee) {
        this.traitee = traitee;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public BigDecimal getPrix() {
        return prix;
    }

    public void setPrix(BigDecimal prix) {
        this.prix = prix;
    }

    public Set<ProductQuantite> getProducts() {
        return products;
    }

    public void setProducts(Set<ProductQuantite> products) {
        this.products = products;
    }

    public Commerce getCommerce() {
        return commerce;
    }

    public void setCommerce(Commerce commerce) {
        this.commerce = commerce;
    }

    public TypeCommande getTypeCommande() {
        return typeCommande;
    }

    public void setTypeCommande(TypeCommande typeCommande) {
        this.typeCommande = typeCommande;
    }

    public ProductVfpDTO getProductVfpDTO() {
        return productVfpDTO;
    }

    public void setProductVfpDTO(ProductVfpDTO productVfpDTO) {
        this.productVfpDTO = productVfpDTO;
    }
}

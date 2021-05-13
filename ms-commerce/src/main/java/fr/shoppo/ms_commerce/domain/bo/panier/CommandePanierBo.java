package fr.shoppo.ms_commerce.domain.bo.panier;

import java.util.List;

public class CommandePanierBo {

    private String id;

    private String enseigne;

    private List<ProductPanier> products;

    float prixTotal;

    public CommandePanierBo(String id, String enseigne, List<ProductPanier> products) {
        this.id = id;
        this.enseigne = enseigne;
        this.products = products;
        this.prixTotal = products
                .stream()
                .map(ProductPanier::getPrix)
                .reduce(Float::sum)
                .orElse(0f);
    }

    public static CommandePanierBo of(String s, float prixTotal){
        var pbo = new CommandePanierBo(s,s,List.of());
        pbo.prixTotal = prixTotal;
        return pbo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEnseigne() {
        return enseigne;
    }

    public void setEnseigne(String enseigne) {
        this.enseigne = enseigne;
    }

    public List<ProductPanier> getProducts() {
        return products;
    }

    public void setProducts(List<ProductPanier> products) {
        this.products = products;
    }

    public float getPrixTotal() {
        return prixTotal;
    }

    public void setPrixTotal(float prixTotal) {
        this.prixTotal = prixTotal;
    }
}

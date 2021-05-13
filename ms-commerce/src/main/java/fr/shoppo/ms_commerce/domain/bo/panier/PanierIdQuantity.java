package fr.shoppo.ms_commerce.domain.bo.panier;

public class PanierIdQuantity {

    /* Id du produit */
    private int id;

    /* quantité du produit */
    private int quantity;

    public PanierIdQuantity() {
    }

    public PanierIdQuantity(int id, int quantity) {
        this.id = id;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

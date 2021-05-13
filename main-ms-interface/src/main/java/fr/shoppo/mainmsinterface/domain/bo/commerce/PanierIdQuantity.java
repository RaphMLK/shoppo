package fr.shoppo.mainmsinterface.domain.bo.commerce;

import java.util.Objects;

public class PanierIdQuantity {

    /* Id du produit */
    private int id;

    /* quantité du produit */
    private int quantity;

    public PanierIdQuantity() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PanierIdQuantity that = (PanierIdQuantity) o;
        return id == that.id && quantity == that.quantity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, quantity);
    }
}

package fr.shoppo.ms_commerce.domain.bo.panier;

public class ProductPanier {

    private int id;

    private String name;

    private float prix;

    private int quantity;

    private int stock;

    public ProductPanier(int id, String name, float prix, int quantity, int stock) {
        this.id = id;
        this.name = name;
        this.prix = prix;
        this.quantity = quantity;
        this.stock = stock;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}

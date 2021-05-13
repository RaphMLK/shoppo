package fr.shoppo.ms_commerce.infrastructure.entity;

import javax.persistence.*;
import java.util.Objects;


@Entity
public class Panier {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private int idClient;

    @ManyToOne
    @JoinColumn(name = "id_produit", nullable = false)
    private Product product;

    @Column
    private int quantite;

    public static Panier of(){
        return new Panier();
    }

    public static Panier of(int idClient,Product product, int quantite){
        var p = Panier.of();
        p.idClient = idClient;
        p.product = product;
        p.quantite = quantite;

        return p;
    }

    public Panier() {
        /*Data binding*/
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Panier panier = (Panier) o;
        return id == panier.id && idClient == panier.idClient && quantite == panier.quantite && Objects.equals(product, panier.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, idClient, product, quantite);
    }
}

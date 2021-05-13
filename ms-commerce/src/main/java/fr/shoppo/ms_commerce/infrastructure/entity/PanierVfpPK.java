package fr.shoppo.ms_commerce.infrastructure.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.io.Serializable;

@Embeddable
public class PanierVfpPK implements Serializable {

    @OneToOne
    @JoinColumn(name = "id_vfp", nullable = false)
    private ProductVfp productVfp;

    @Column(nullable = false)
    private int idClient;

    public PanierVfpPK() {
    }

    public PanierVfpPK(ProductVfp productVfp, int idClient) {
        this.productVfp = productVfp;
        this.idClient = idClient;
    }

    public ProductVfp getProductVfp() {
        return productVfp;
    }

    public void setProductVfp(ProductVfp productVfp) {
        this.productVfp = productVfp;
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }
}

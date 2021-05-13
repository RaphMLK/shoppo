package fr.shoppo.ms_commerce.infrastructure.entity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.io.Serializable;

@Entity
public class PanierVfp implements Serializable {

    @EmbeddedId
    private PanierVfpPK panierVfpPK;

    public PanierVfp(PanierVfpPK panierVfpPK) {
        this.panierVfpPK = panierVfpPK;
    }

    public PanierVfp() {

    }

    public PanierVfpPK getPanierVfpPK() {
        return panierVfpPK;
    }

    public void setPanierVfpPK(PanierVfpPK panierVfpPK) {
        this.panierVfpPK = panierVfpPK;
    }
}

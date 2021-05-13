package fr.shoppo.ms_commerce.domain.bo.panier;

import fr.shoppo.ms_commerce.domain.bo.ProductVfpDTO;

import java.util.List;
import java.util.Set;

public class PanierDTO {

    private ProductVfpDTO advantage;

    private List<CommandePanierBo> panier;

    public PanierDTO(ProductVfpDTO advantage, List<CommandePanierBo> panier) {
        this.advantage = advantage;
        this.panier = panier;
    }

    public ProductVfpDTO getAdvantage() {
        return advantage;
    }

    public void setAdvantage(ProductVfpDTO advantage) {
        this.advantage = advantage;
    }

    public List<CommandePanierBo> getPanier() {
        return panier;
    }

    public void setPanier(List<CommandePanierBo> panier) {
        this.panier = panier;
    }
}

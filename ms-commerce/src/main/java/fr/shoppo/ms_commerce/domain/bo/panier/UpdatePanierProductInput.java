package fr.shoppo.ms_commerce.domain.bo.panier;

public class UpdatePanierProductInput {

    private int idCLient;

    private int idProduit;

    private int quantite;

    public UpdatePanierProductInput() {
        /* empty constructor for rabbit */
    }

    public int getIdCLient() {
        return idCLient;
    }

    public void setIdCLient(int idCLient) {
        this.idCLient = idCLient;
    }

    public int getIdProduit() {
        return idProduit;
    }

    public void setIdProduit(int idProduit) {
        this.idProduit = idProduit;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }
}

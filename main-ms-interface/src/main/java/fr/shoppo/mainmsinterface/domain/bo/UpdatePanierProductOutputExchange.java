package fr.shoppo.mainmsinterface.domain.bo;

import java.util.Objects;

public class UpdatePanierProductOutputExchange {

    private int idCLient;

    private int idProduit;

    private int quantite;

    public UpdatePanierProductOutputExchange() {
        /* empty constructor for rabbit */
    }

    public UpdatePanierProductOutputExchange(int idCLient, int idProduit, int quantite) {
        this.idCLient = idCLient;
        this.idProduit = idProduit;
        this.quantite = quantite;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UpdatePanierProductOutputExchange that = (UpdatePanierProductOutputExchange) o;
        return idCLient == that.idCLient && idProduit == that.idProduit && quantite == that.quantite;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCLient, idProduit, quantite);
    }
}

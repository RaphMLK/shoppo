package fr.shoppo.ms_commerce.domain.bo.panier;

public class AddDeleteVfpPanier {

    private int idClient;

    private int idVfp;

    private boolean addOrDelete;

    public AddDeleteVfpPanier() {
    }

    public AddDeleteVfpPanier(int idClient, int idVfp, boolean addOrDelete) {
        this.idClient = idClient;
        this.idVfp = idVfp;
        this.addOrDelete = addOrDelete;
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public int getIdVfp() {
        return idVfp;
    }

    public void setIdVfp(int idVfp) {
        this.idVfp = idVfp;
    }

    public boolean isAddOrDelete() {
        return addOrDelete;
    }

    public void setAddOrDelete(boolean addOrDelete) {
        this.addOrDelete = addOrDelete;
    }
}

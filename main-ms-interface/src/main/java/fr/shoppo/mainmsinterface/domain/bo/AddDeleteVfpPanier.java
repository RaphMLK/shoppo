package fr.shoppo.mainmsinterface.domain.bo;

import java.util.Objects;

public class AddDeleteVfpPanier {

    private int idClient;

    private int idVfp;

    private boolean addOrDelete;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddDeleteVfpPanier that = (AddDeleteVfpPanier) o;
        return idClient == that.idClient && idVfp == that.idVfp && addOrDelete == that.addOrDelete;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idClient, idVfp, addOrDelete);
    }
}

package fr.shoppo.mainmsinterface.domain.bo.commerce;

import java.util.Objects;

public class CreateCommandeInput {
    int idClient;
    TypeCommandeEnum typeCommandeEnum;

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public TypeCommandeEnum getTypeCommandeEnum() {
        return typeCommandeEnum;
    }

    public void setTypeCommandeEnum(TypeCommandeEnum typeCommandeEnum) {
        this.typeCommandeEnum = typeCommandeEnum;
    }

    public static CreateCommandeInput of(int idC, TypeCommandeEnum t){
        var cci = new CreateCommandeInput();
        cci.setIdClient(idC);
        cci.setTypeCommandeEnum(t);
        return cci;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreateCommandeInput that = (CreateCommandeInput) o;
        return idClient == that.idClient && typeCommandeEnum == that.typeCommandeEnum;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idClient, typeCommandeEnum);
    }
}

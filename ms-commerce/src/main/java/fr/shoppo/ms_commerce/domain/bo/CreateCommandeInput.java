package fr.shoppo.ms_commerce.domain.bo;

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
}

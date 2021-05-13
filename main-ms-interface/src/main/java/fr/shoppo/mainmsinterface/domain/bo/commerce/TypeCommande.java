package fr.shoppo.mainmsinterface.domain.bo.commerce;

import java.util.Objects;

public class TypeCommande {

    Integer id;
    TypeCommandeEnum libelle;

    public TypeCommande() {
        /*binding*/
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public TypeCommandeEnum getLibelle() {
        return libelle;
    }

    public void setLibelle(TypeCommandeEnum libelle) {
        this.libelle = libelle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TypeCommande that = (TypeCommande) o;
        return Objects.equals(id, that.id) &&
                libelle == that.libelle;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, libelle);
    }
}

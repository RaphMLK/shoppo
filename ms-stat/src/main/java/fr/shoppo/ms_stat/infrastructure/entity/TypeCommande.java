package fr.shoppo.ms_stat.infrastructure.entity;


import com.sun.istack.NotNull;
import fr.shoppo.ms_stat.domain.bo.commande.TypeCommandeEnum;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class TypeCommande implements Serializable  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    Integer id;

    @Column
    @NotNull
    @Enumerated(value = EnumType.STRING)
    TypeCommandeEnum libelle;

    public TypeCommande() {
        /*data binding*/
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
                Objects.equals(libelle, that.libelle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, libelle);
    }

}

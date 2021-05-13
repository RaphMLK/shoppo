package fr.shoppo.ms_commerce.infrastructure.entity;


import com.sun.istack.NotNull;
import fr.shoppo.ms_commerce.domain.bo.TypeCommandeEnum;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class TypeCommande implements Serializable  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    Integer id;

    @Column(name = "libelle")
    @NotNull
    @Enumerated(value = EnumType.STRING)
    TypeCommandeEnum libelle;

    public static TypeCommande of(TypeCommandeEnum tce){
        var tc = new TypeCommande();
        tc.setLibelle(tce);
        return tc;
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

    public TypeCommande() {
        /*data binding*/
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

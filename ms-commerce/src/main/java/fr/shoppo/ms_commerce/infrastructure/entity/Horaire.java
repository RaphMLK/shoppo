package fr.shoppo.ms_commerce.infrastructure.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class Horaire implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    Integer id;

    @Column(nullable = false)
    String jour;
    @Column
    String heureDebut = "";
    @Column
    String heureFin = "";
    @Column
    String heureDebut2 = "";
    @Column
    String heureFin2 = "";

    @Column
    boolean fermetureExceptionnelle = false;

    public static Horaire of(String jour){
        var hr = new Horaire();
        hr.jour = jour;
        return hr;
    }


    public Horaire() {
        /*data binding*/
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getJour() {
        return jour;
    }

    public void setJour(String jour) {
        this.jour = jour;
    }

    public String getHeureDebut() {
        return heureDebut;
    }

    public void setHeureDebut(String heureDebut) {
        this.heureDebut = heureDebut;
    }

    public String getHeureFin() {
        return heureFin;
    }

    public void setHeureFin(String heureFin) {
        this.heureFin = heureFin;
    }

    public String getHeureDebut2() {
        return heureDebut2;
    }

    public void setHeureDebut2(String heureDebut2) {
        this.heureDebut2 = heureDebut2;
    }

    public String getHeureFin2() {
        return heureFin2;
    }

    public void setHeureFin2(String heureFin2) {
        this.heureFin2 = heureFin2;
    }

    public boolean isFermetureExceptionnelle() {
        return fermetureExceptionnelle;
    }

    public void setFermetureExceptionnelle(boolean fermetureExceptionnelle) {
        this.fermetureExceptionnelle = fermetureExceptionnelle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Horaire)) return false;
        Horaire horaire = (Horaire) o;
        return Objects.equals(getJour(), horaire.getJour());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getJour());
    }
}

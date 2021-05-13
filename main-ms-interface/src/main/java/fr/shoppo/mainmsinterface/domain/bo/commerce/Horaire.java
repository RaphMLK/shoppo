package fr.shoppo.mainmsinterface.domain.bo.commerce;


import java.util.Objects;

public class Horaire {
    Integer id;
    String jour;
    String heureDebut = "";
    String heureFin = "";
    String heureDebut2 = "";
    String heureFin2 = "";

    boolean fermetureExceptionnelle = false;


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
        return isFermetureExceptionnelle() == horaire.isFermetureExceptionnelle() &&
                Objects.equals(getJour(), horaire.getJour()) &&
                Objects.equals(getHeureDebut(), horaire.getHeureDebut()) &&
                Objects.equals(getHeureFin(), horaire.getHeureFin()) &&
                Objects.equals(getHeureDebut2(), horaire.getHeureDebut2()) &&
                Objects.equals(getHeureFin2(), horaire.getHeureFin2());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getJour(), getHeureDebut(), getHeureFin(), getHeureDebut2(), getHeureFin2(), isFermetureExceptionnelle());
    }
}

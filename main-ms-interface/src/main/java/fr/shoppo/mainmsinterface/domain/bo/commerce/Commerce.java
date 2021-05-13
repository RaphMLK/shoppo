package fr.shoppo.mainmsinterface.domain.bo.commerce;

import fr.shoppo.mainmsinterface.domain.bo.Adresse;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Commerce implements Serializable {

    String sirenCode;
    String siretCode;
    String enseigne;
    String name;
    String lienPhoto;
    String description;
    CodeNaf codeNaf;
    Adresse adresse;
    List<Horaire> horaire = new ArrayList<>();

    public Commerce() {
        /*binding*/
    }

    public String getSirenCode() {
        return sirenCode;
    }

    public void setSirenCode(String sirenCode) {
        this.sirenCode = sirenCode;
    }

    public String getSiretCode() {
        return siretCode;
    }

    public void setSiretCode(String siretCode) {
        this.siretCode = siretCode;
    }

    public String getEnseigne() {
        return enseigne;
    }

    public void setEnseigne(String enseigne) {
        this.enseigne = enseigne;
    }

    public String getLienPhoto() {
        return lienPhoto;
    }

    public void setLienPhoto(String lienPhoto) {
        this.lienPhoto = lienPhoto;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CodeNaf getCodeNaf() {
        return codeNaf;
    }

    public void setCodeNaf(CodeNaf codeNaf) {
        this.codeNaf = codeNaf;
    }

    public Adresse getAdresse() {
        return adresse;
    }

    public void setAdresse(Adresse adresse) {
        this.adresse = adresse;
    }

    public List<Horaire> getHoraire() {
        return horaire;
    }

    public void setHoraire(List<Horaire> horaire) {
        this.horaire = horaire;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "{" +
                "sirenCode='" + sirenCode + '\'' +
                ", siretCode='" + siretCode + '\'' +
                ", enseigne='" + enseigne + '\'' +
                ", lienPhoto='" + lienPhoto + '\'' +
                ", description='" + description + '\'' +
                ", codeNaf=" + codeNaf +
                ", adresse=" + adresse +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Commerce commerce = (Commerce) o;
        return Objects.equals(sirenCode, commerce.sirenCode) &&
                Objects.equals(siretCode, commerce.siretCode) &&
                Objects.equals(enseigne, commerce.enseigne) &&
                Objects.equals(lienPhoto, commerce.lienPhoto) &&
                Objects.equals(description, commerce.description) &&
                Objects.equals(codeNaf, commerce.codeNaf) &&
                Objects.equals(adresse, commerce.adresse);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sirenCode, siretCode, enseigne, name, lienPhoto, description, codeNaf, adresse, horaire);
    }
}

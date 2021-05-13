package fr.shoppo.msnotification.domain.bo.commerce;


import fr.shoppo.msnotification.domain.bo.Adresse;
import fr.shoppo.msnotification.domain.bo.Template;

import java.util.ArrayList;
import java.util.List;

public class Commerce implements Template {

    String sirenCode;
    String siretCode;
    String enseigne;
    String lienPhoto;
    String description;
    CodeNaf codeNaf;
    Adresse adresse;
    List<Commercant> commercants = new ArrayList<>();
    Commercant owner;

    public Commerce() {
        /*data binding*/
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

    public List<Commercant> getCommercants() {
        return commercants;
    }

    public void setCommercants(List<Commercant> commercants) {
        this.commercants = commercants;
    }

    public Commercant getOwner() {
        return owner;
    }

    public void setOwner(Commercant owner) {
        this.owner = owner;
    }

    @Override
    public String toString() {
        return "{" +
                "\nsirenCode='" + sirenCode + '\'' +
                ",\n siretCode='" + siretCode + '\'' +
                ",\n enseigne='" + enseigne + '\'' +
                ",\n lienPhoto='" + lienPhoto + '\'' +
                ",\n description='" + description + '\'' +
                ",\n codeNaf=" + codeNaf +
                ",\n adresse=" + adresse +
                ",\n nbCommercants=" + commercants.size() +
                '}';
    }
}

package fr.shoppo.msnotification.domain.bo;

public class Adresse {
    String codePostal;
    String numeroRue;
    String libelleRue;
    String ville;

    public Adresse() {
        /*data binding*/
    }

    public String getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    public String getNumeroRue() {
        return numeroRue;
    }

    public void setNumeroRue(String numeroRue) {
        this.numeroRue = numeroRue;
    }

    public String getLibelleRue() {
        return libelleRue;
    }

    public void setLibelleRue(String libelleRue) {
        this.libelleRue = libelleRue;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    @Override
    public String toString() {
        return "Adresse{" +
                "codePostal='" + codePostal + '\'' +
                ", numeroRue='" + numeroRue + '\'' +
                ", libelleRue='" + libelleRue + '\'' +
                ", ville='" + ville + '\'' +
                '}';
    }
}

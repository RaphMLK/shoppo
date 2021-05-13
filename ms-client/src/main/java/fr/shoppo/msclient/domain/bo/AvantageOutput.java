package fr.shoppo.msclient.domain.bo;

public class AvantageOutput {

    String id;
    String libelle;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public AvantageOutput(AvantageVFP e) {
        this.id = e.name();
        this.libelle = e.description;
    }

    public AvantageOutput() {
        /*binding*/
    }
}

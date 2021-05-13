package fr.shoppo.mainmsinterface.infrastructure.config.securite;

public enum Role {
    ADMIN("ADMIN"),
    CLIENT("CLIENT"),
    COMMERCANT("COMMERCANT"),
    PREFIXE_ROLE("ROLE_");

    String libelle;

    Role(String libelle) {
        this.libelle = libelle;
    }

    public String libelle(){
        return libelle;
    }
}

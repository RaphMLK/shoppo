package fr.shoppo.mainmsinterface.infrastructure.config.securite;

public enum PrexifeRoleUrl {
    ADMIN_URL("/admin-auth/"),
    CLIENT_URL("/client-auth/"),
    COMMERCANT_URL("/commercant-auth/");

    String libelle;

    PrexifeRoleUrl(String libelle) {
        this.libelle = libelle;
    }

    public String libelle(){
        return libelle;
    }

    public String libelleRoute(){
        return libelle+"**";
    }
}

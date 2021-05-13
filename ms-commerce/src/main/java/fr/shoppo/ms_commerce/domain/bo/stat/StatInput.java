package fr.shoppo.ms_commerce.domain.bo.stat;

import fr.shoppo.ms_commerce.infrastructure.entity.Commande;

import java.util.List;
import java.util.Objects;

public class StatInput {
    List<Commande> commandes;

    public List<Commande> getCommandes() {
        return commandes;
    }

    public void setCommandes(List<Commande> commandes) {
        this.commandes = commandes;
    }

    public static StatInput of(List<Commande> commandes){
        var in = new StatInput();
        in.setCommandes(commandes);
        return in;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StatInput)) return false;
        StatInput statInput = (StatInput) o;
        return Objects.equals(getCommandes(), statInput.getCommandes());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCommandes());
    }

    public StatInput() {
        /*binding*/
    }
}

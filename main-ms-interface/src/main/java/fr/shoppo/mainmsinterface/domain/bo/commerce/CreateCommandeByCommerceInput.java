package fr.shoppo.mainmsinterface.domain.bo.commerce;

import java.util.List;

public class CreateCommandeByCommerceInput {

    /* Qr Code du client */
    private String client;

    /* Liste des produits du commerce */
    private List<PanierIdQuantity> command;

    /* Boolean pour savoir si on paye en ligne ou non */
    private boolean onlinePayment;

    /* id de l'avantage si il y en a un */
    private Integer advantage;

    public CreateCommandeByCommerceInput() {
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public List<PanierIdQuantity> getCommand() {
        return command;
    }

    public void setCommand(List<PanierIdQuantity> command) {
        this.command = command;
    }

    public void setOnlinePayment(boolean onlinePayment) {
        this.onlinePayment = onlinePayment;
    }

    public boolean isOnlinePayment() {
        return onlinePayment;
    }

    public Integer getAdvantage() {
        return advantage;
    }

    public void setAdvantage(Integer advantage) {
        this.advantage = advantage;
    }
}

package fr.shoppo.mainmsinterface.domain.service.commerce;

import fr.shoppo.mainmsinterface.domain.bo.commerce.CreateCommandeByCommerceOutput;
import fr.shoppo.mainmsinterface.domain.bo.commerce.TypeCommandeEnum;

public interface ManageCommandeService {

    String getCommandesCommerce(String email);
    String getCommandesClient(int id);
    String createCommande(int id, TypeCommandeEnum typeCommandeEnum);
    String createCommandeByCommerce(CreateCommandeByCommerceOutput createCommandeByCommerceOutput);
    String getCommande(int id);
    String updateCommande(int id);

}

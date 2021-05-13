package fr.shoppo.mainmsinterface.domain.service.commerce;

public interface ManageCommerceService {

    String createCommerce(String siret, String mail);
    String addCommercant(String siret, String mail);
    String rmCommercant(String siret, String mail);
}

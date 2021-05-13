package fr.shoppo.mainmsinterface.domain.service.commerce;

import fr.shoppo.mainmsinterface.domain.bo.commerce.AddVfpByProductGroup;

public interface ManageVfpCommerceService {
    String addVfpByProduct(AddVfpByProductGroup addVfpByProductGroup);
    String deleteVfp(int idVfp);
    String getVfp(String email);
}

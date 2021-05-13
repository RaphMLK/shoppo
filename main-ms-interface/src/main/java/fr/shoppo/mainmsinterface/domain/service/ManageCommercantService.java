package fr.shoppo.mainmsinterface.domain.service;

import fr.shoppo.mainmsinterface.domain.bo.User;
import fr.shoppo.mainmsinterface.domain.bo.commerce.Commerce;

public interface ManageCommercantService {

    String resetPassword(String email);
    String login(User user);
    String changePassword(User user);
    String findCommercant(String emailOrSiret,boolean withProducts);
    String update(Commerce commerce);
    String getCommercant(String email);
}

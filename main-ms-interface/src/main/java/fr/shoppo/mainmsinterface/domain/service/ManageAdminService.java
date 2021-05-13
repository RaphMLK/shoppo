package fr.shoppo.mainmsinterface.domain.service;

import fr.shoppo.mainmsinterface.domain.bo.User;

public interface ManageAdminService {

    String resetPassword(String email);
    String login(User user);
}

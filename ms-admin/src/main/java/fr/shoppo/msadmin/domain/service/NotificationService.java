package fr.shoppo.msadmin.domain.service;

import fr.shoppo.msadmin.infrastructure.entity.Admin;

public interface NotificationService {

    void resetPassword(Admin admin);
}

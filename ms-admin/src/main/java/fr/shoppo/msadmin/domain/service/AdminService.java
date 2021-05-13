package fr.shoppo.msadmin.domain.service;

import fr.shoppo.msadmin.infrastructure.entity.Admin;
import fr.shoppo.msadmin.infrastructure.exception.AdminException;

public interface AdminService {
    Admin resetPassword(String email) throws AdminException;
    Admin login(String email, String password) throws AdminException;
}

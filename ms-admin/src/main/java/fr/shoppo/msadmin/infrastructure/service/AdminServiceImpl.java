package fr.shoppo.msadmin.infrastructure.service;

import fr.shoppo.msadmin.domain.service.AdminService;
import fr.shoppo.msadmin.infrastructure.dao.AdminDao;
import fr.shoppo.msadmin.infrastructure.entity.Admin;
import fr.shoppo.msadmin.infrastructure.exception.AdminErrorLogin;
import fr.shoppo.msadmin.infrastructure.exception.AdminException;
import fr.shoppo.msadmin.infrastructure.exception.AdminNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

    private final AdminDao adminDao;
    PasswordManager passwordManager;

    public AdminServiceImpl(AdminDao adminDao) {
        this.adminDao = adminDao;
    }

    @Override
    public Admin resetPassword(String email) throws AdminException {
        var admin = adminDao
                .findByEmail(email)
                .orElseThrow(AdminNotFoundException::new);

        var clearPass = passwordManager.generateSecureRandomPassword();
        admin.setPassword(passwordManager.hash(clearPass));
        adminDao.save(admin);
        admin.setPassword(clearPass);
        return admin;
    }

    @Override
    public Admin login(String email, String password) throws AdminException {
        var admin = adminDao
                .findByEmail(email)
                .orElseThrow(AdminNotFoundException::new);

        var hashPass = passwordManager.hash(password);
        if (admin.getPassword().equals(hashPass)) {
            return admin;
        }
        throw new AdminErrorLogin();
    }

    @Autowired
    public void setPasswordManager(PasswordManager passwordManager) {
        this.passwordManager = passwordManager;
    }
}

package fr.shoppo.msadmin.infrastructure.service;

import fr.shoppo.msadmin.infrastructure.dao.AdminDao;
import fr.shoppo.msadmin.infrastructure.entity.Admin;
import fr.shoppo.msadmin.infrastructure.exception.AdminException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AdminServiceImplTest {

    AdminDao adminDao;
    PasswordManager passwordManager;

    AdminServiceImpl adminService;

    Admin roger;

    @BeforeEach
    void setup(){
        adminDao = mock(AdminDao.class);
        passwordManager = mock(PasswordManager.class);

        adminService = new AdminServiceImpl(adminDao);
        adminService.setPasswordManager(passwordManager);

        roger = new Admin();
        roger.setPassword("test");
        roger.setEmail("test");

        when(passwordManager.generateSecureRandomPassword()).thenReturn("generate");
        when(passwordManager.hash("test")).thenReturn("secure");
    }

    @Test
    void should_resetPassword() throws AdminException {
        when(adminDao.findByEmail("test")).thenReturn(Optional.of(roger));
        when(adminDao.save(roger)).thenReturn(roger);

        assertEquals(roger,adminService.resetPassword("test"));
        assertEquals("generate",roger.getPassword());
    }

    @Test
    void should_resetPassword_but_not_found()  {
        when(adminDao.findByEmail("test")).thenReturn(Optional.empty());
        assertThrows(AdminException.class,()->adminService.resetPassword("test"));
    }

    @Test
    void should_login() throws AdminException {
        roger.setPassword("secure");
        when(adminDao.findByEmail("test")).thenReturn(Optional.of(roger));
        assertEquals(roger,adminService.login("test","test"));
    }

    @Test
    void should_login_but_bad_credentials() {
        roger.setPassword("secure");
        when(adminDao.findByEmail("test")).thenReturn(Optional.of(roger));
        assertThrows(AdminException.class,()->adminService.login("test","bad"));
    }

    @Test
    void should_login_but_not_found() {
        when(adminDao.findByEmail("test")).thenReturn(Optional.empty());
        assertThrows(AdminException.class,()->adminService.login("test","bad"));
    }

}
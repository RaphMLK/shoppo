package fr.shoppo.mainmsinterface.domain.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ManageAdminServiceTest {

    private Class<ManageAdminService> manageAdminServiceClass;

    @BeforeEach
    void setUp(){
        manageAdminServiceClass = ManageAdminService.class;
    }

    @Test
    void resetPasswordTest() throws NoSuchMethodException {
        var method = manageAdminServiceClass.getDeclaredMethod("resetPassword", String.class);
        assertNotNull(method);
        assertEquals(String.class,method.getReturnType());
    }
}

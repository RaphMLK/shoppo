package fr.shoppo.mainmsinterface.domain.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ManageClientServiceTest {

    private Class<ManageClientService> manageClientServiceClass;

    @BeforeEach
    void setUp(){
        manageClientServiceClass = ManageClientService.class;
    }

    @Test
    void resetPasswordTest() throws NoSuchMethodException {
        var method = manageClientServiceClass.getDeclaredMethod("resetPassword", String.class);
        assertNotNull(method);
        assertEquals(String.class,method.getReturnType());
    }
}

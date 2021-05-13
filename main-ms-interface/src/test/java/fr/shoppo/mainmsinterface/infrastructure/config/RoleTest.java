package fr.shoppo.mainmsinterface.infrastructure.config;


import org.junit.jupiter.api.Test;

import static fr.shoppo.mainmsinterface.infrastructure.config.securite.Role.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RoleTest {

    @Test
    void roleAdmin(){
        assertEquals("ADMIN", ADMIN.libelle());
    }

    @Test
    void roleClient(){
        assertEquals("CLIENT", CLIENT.libelle());
    }

    @Test
    void roleCommercant(){
        assertEquals("COMMERCANT", COMMERCANT.libelle());
    }

    @Test
    void rolePrefixeRole(){
        assertEquals("ROLE_", PREFIXE_ROLE.libelle());
    }

}

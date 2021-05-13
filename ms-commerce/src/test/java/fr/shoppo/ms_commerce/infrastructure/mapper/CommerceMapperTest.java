package fr.shoppo.ms_commerce.infrastructure.mapper;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class CommerceMapperTest {
    /*for disable smelling*/

    @Test
    void couldnt_be_instanciated(){
        assertThrows(IllegalAccessException.class, CommerceMapper::new);
    }

}
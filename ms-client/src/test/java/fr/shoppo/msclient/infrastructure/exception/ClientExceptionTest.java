package fr.shoppo.msclient.infrastructure.exception;

import fr.shoppo.msclient.domain.constant.MessageConstantEnum;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class ClientExceptionTest {

    @Test
    void should_throw_exception_with_enum_and_message(){
        assertThrows(ClientException.class,() ->{throw new ClientException(MessageConstantEnum.OK,"test");});
    }

    @Test
    void should_throw_exception_with_enum(){
        assertThrows(ClientException.class,() ->{throw new ClientException(MessageConstantEnum.OK);});
    }
}
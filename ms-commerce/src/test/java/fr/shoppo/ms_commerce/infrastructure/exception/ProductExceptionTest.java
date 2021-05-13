package fr.shoppo.ms_commerce.infrastructure.exception;

import fr.shoppo.ms_commerce.domain.constant.MessageConstantEnum;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class ProductExceptionTest {

    @Test
    void should_be_instanciating_with_string_and_params() {
        assertThrows(ProductException.class,() ->  {throw new ProductException(MessageConstantEnum.OK,"Test");});
    }
}
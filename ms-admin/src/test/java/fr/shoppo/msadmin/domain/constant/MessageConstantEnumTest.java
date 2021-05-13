package fr.shoppo.msadmin.domain.constant;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static fr.shoppo.msadmin.domain.constant.MessageConstantEnum.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MessageConstantEnumTest {

    @ParameterizedTest
    @ValueSource( strings = {
            "ERREUR",
            "OK",
            "ADMIN_NOT_FOUND"
    })
    void should_be_instancied_by_string_name(String name){
        var message = MessageConstantEnum.fromName(name);
        assertThat(message).isNotNull();
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "dssqdqdqsjhgdqsj",
            "qsdlkljmqs",
            "hdspoqjmdoqpbsihodmnk"
    })
    void shouldnt_be_instancied_by_fake_string_name(String name){
        assertThrows(IllegalArgumentException.class,() -> MessageConstantEnum.fromName(name));
    }

    @Test
    void shouldnt_be_instancied_by_null(){
        assertThrows(IllegalArgumentException.class,() -> MessageConstantEnum.fromName(null));
    }

    @Test
    void should_be_instancied_by_customized_accessors(){
        var name = "Une erreur est survenue";
        var message = MessageConstantEnum.from(messageConstantEnum -> name.equals(messageConstantEnum.message),name);

        assertThat(message).isEqualTo(ERREUR);
    }

    @Test
    void should_be_choose_the_first_in_the_list_of_multiple_choose(){
        var message = MessageConstantEnum.from(messageConstantEnum -> true,"all");

        assertThat(message).isEqualTo(OK);
    }

    @Test
    void should_gave_a_message_by_tostring_method(){
        assertThat(OK.toString()).hasToString("OK");
        assertThat(ERREUR.toString()).hasToString("Une erreur est survenue");
        assertThat(ADMIN_NOT_FOUND.toString()).hasToString("L'admin n'existe pas");
    }

}
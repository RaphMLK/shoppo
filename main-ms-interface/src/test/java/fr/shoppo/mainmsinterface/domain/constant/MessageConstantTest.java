package fr.shoppo.mainmsinterface.domain.constant;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.http.HttpStatus;

import static fr.shoppo.mainmsinterface.domain.constant.MessageConstantEnum.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MessageConstantTest {

    @ParameterizedTest
    @ValueSource( strings = {
            "ERREUR",
            "OK",
            "COMMERCANT_NOT_FOUND"
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
        assertEquals(UNKNOWN, MessageConstantEnum.fromName(name));
    }

    @ParameterizedTest
    @ValueSource( strings = {
            "Une erreur est survenue",
            "OK",
            "L'utilisateur n'existe pas"
    })
    void should_be_instancied_by_string_message(String name){
        var message = MessageConstantEnum.fromMessage(name);
        assertThat(message).isNotNull();
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "dssqdqdqsjhgdqsj",
            "qsdlkljmqs",
            "hdspoqjmdoqpbsihodmnk"
    })
    void shouldnt_be_instancied_by_fake_string_message(String name){
        assertEquals(UNKNOWN, MessageConstantEnum.fromMessage(name));
    }

    @Test
    void shouldnt_be_instancied_by_null(){
        assertEquals(UNKNOWN, MessageConstantEnum.fromName(null));
        assertEquals(UNKNOWN, MessageConstantEnum.fromMessage(null));
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
        assertThat(COMMERCANT_NOT_FOUND.toString()).hasToString("Le commerçant n'existe pas");
    }

    @Test
    void should_gave_a_httpStatus(){
        assertThat(OK.httpStatus()).isEqualTo(HttpStatus.OK);
        assertThat(ERREUR.httpStatus()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(USER_NOT_FOUND.httpStatus()).isEqualTo(HttpStatus.NOT_FOUND);
    }


}

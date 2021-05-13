package fr.shoppo.ms_commerce.infrastructure.json;

import fr.shoppo.ms_commerce.domain.constant.MessageConstantEnum;
import fr.shoppo.ms_commerce.infrastructure.exception.CommerceException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
class JsonManagerComponentTest{

    @Test
    void couldnt_be_instancied(){
        assertThrows(IllegalAccessException.class, JsonManagerComponent::new);
    }

    @Test
    void should_get_an_empty_Object_if_thrown_JsonException()  {
        var json = JsonManagerComponent.tryToGet(unused -> {throw new JSONException("null");},Object.class);

        assertEquals(Object.class,json.getClass());
    }
    @Test
    void should_get_an_null_if_thrown_JsonException(){
        var json = JsonManagerComponent.tryToGet(unused -> {throw new JSONException("null");});

        assertNull(json);
    }


    @Test
    void should_get_a_custom_exception_if_thrown_JsonException(){
        assertThrows(CommerceException.class,() -> JsonManagerComponent.tryToGet(unused -> {throw new JSONException("null");},new CommerceException(MessageConstantEnum.OK)));
    }

    @Test
    void should_get_result_of_apply(){
        var jsonObject = new JSONObject();

        var json = JsonManagerComponent.tryToGet(unused -> {
            jsonObject.put("test","test");
            return jsonObject.get("test");
        });

        assertEquals("test",json);
    }

    @Test
    void should_thrown_runtime_exception_if_something_really_go_wrong(){
        assertThrows(RuntimeException.class,() -> JsonManagerComponent.tryToGet(unused -> {throw new JSONException("null");}, IntStream.class));
    }

    @Test
    void should_applying_on_function_which_redirect_exception() throws Exception {
        assertEquals("test",JsonManagerComponent.tryToGet(unused -> "test",new CommerceException(MessageConstantEnum.OK)));
    }
}
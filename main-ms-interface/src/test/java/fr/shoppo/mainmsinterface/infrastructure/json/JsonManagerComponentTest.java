package fr.shoppo.mainmsinterface.infrastructure.json;

import fr.shoppo.mainmsinterface.infrastructure.service.exception.CommerceRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class JsonManagerComponentTest {
    JsonManagerComponent jsonManagerComponent;

    @BeforeEach
    public void setup(){
        jsonManagerComponent = new JsonManagerComponent();
    }

    @Test
    public void should_try_to_get_thrown_custom_exception()  {
        assertThrows(CommerceRequestException.class,
                ()-> jsonManagerComponent
                        .tryToGet((Void) -> { throw new JSONException(""); },
                                new CommerceRequestException("Oh terrible")));

        assertThrows(IllegalArgumentException.class,
                ()-> jsonManagerComponent
                        .tryToGet((Void) -> { throw new JSONException(""); },
                                new IllegalArgumentException("Oh terrible")));

        assertThrows(RuntimeException.class,
                ()-> jsonManagerComponent
                        .tryToGet((Void) -> { throw new JSONException(""); },
                                new RuntimeException("Oh terrible")));

    }

    @Test
    public void should_try_to_get_the_answer_where_nothing_is_thrown() throws Exception {
        assertEquals("123",
                jsonManagerComponent.tryToGet( (Void) -> new JSONObject("{nb:\"123\"}").getString("nb"),
                        new IllegalArgumentException("Never happens")
                ));
    }
}
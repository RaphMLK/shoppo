package fr.shoppo.mainmsinterface.infrastructure.action;

import fr.shoppo.mainmsinterface.domain.bo.stat.ReadingInput;
import fr.shoppo.mainmsinterface.domain.bo.stat.ReadingInputController;
import fr.shoppo.mainmsinterface.domain.bo.stat.UserType;
import fr.shoppo.mainmsinterface.domain.service.StatService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ReadStatActionTest {

    StatService statService;

    @BeforeEach
    void setup(){
        statService = mock(StatService.class);
    }

    /*

     public static ResponseEntity<String>
    read(ReadingInputController input, UserType user, StatService statService){
        var inputContent = input != null ?
                input.getLabel().split("#"):new String[]{"undefined:label"};
        var label = StatLabel.fromLabelFormating(inputContent[0]);

        var inputStat = new ReadingInput();
        inputStat.setLabel(label);
        inputStat.setUserType(user);
        inputStat.setArgs(Arrays.copyOfRange(inputContent,1,inputContent.length));
        if(label.needParameter() && inputStat.getArgs().length == 0)
            StatLabel.fromLabelFormating("undefined:needed args");
        return ResponseEntity.ok(statService.readStat(inputStat));
    }
    * */

    @Test
    void test_read(){
        var input = new ReadingInputController();
        input.setLabel("CnC#Client#12345");
        when(statService.readStat(any(ReadingInput.class))).thenReturn("");

        assertEquals(ResponseEntity.ok(""),ReadStatAction.read(input, UserType.COMMERCANT,statService));
        input.setLabel("CnC");

        assertThrows(IllegalArgumentException.class,() ->ReadStatAction.read(input, UserType.COMMERCANT,statService));
    }

}
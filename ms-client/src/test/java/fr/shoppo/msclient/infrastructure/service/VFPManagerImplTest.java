package fr.shoppo.msclient.infrastructure.service;

import fr.shoppo.msclient.domain.bo.VFPStateFormat;
import fr.shoppo.msclient.domain.service.VFPStateManager;
import fr.shoppo.msclient.infrastructure.entity.Client;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.IntStream;

import static fr.shoppo.msclient.domain.bo.VFPStateFormat.*;
import static org.junit.jupiter.api.Assertions.*;

class VFPManagerImplTest {

    public static final int SEUIL = 5;

    VFPStateManager stateManager;
    Client henry;

    @BeforeEach
    void setup(){
        stateManager = new VFPManagerImpl();
        ((VFPManagerImpl)stateManager).setVfpSeuil(SEUIL);

        henry = new Client();
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "",/*empty should get a 7 sized with underscore*/
            "#123456",/*good size but not good parse*/
            "#1234",/*too little*/
            "123#",/*too little and ending with hashtag for today*/
            "1234567",/*only unavailable value*/
            "12345678",/*too large*/
            "djslqijnkdsbjldknqsmjdmlsq"/*oups my head hits my keyboard*/
    })
    void could_be_composed_only_with_hashtag_or_space_replacing_by_space_bydefault(String before){
        henry.setPointsVFP(before);
        var parsed = stateManager.parse(henry);
        System.out.printf("'%s' <- '%s'",parsed,before); /*if you wanna see the parsed char vs unparsed*/

        assertTrue(parsed.matches(VFPStateFormat.regex(true))); /*regex meaning : contains only spaces or hashtag on seven chars not more, not minus*/
    }

    @Test
    void change_last_char_if_content_is_empty(){
        var expectAtBegin = StringUtils.repeat(NOT_COMMAND.value(),LENGTH);
        assertEquals(expectAtBegin,henry.getPointsVFP());
        assertEquals(LENGTH,expectAtBegin.length());

        var expect = StringUtils.repeat(NOT_COMMAND.value(), LENGTH-1) + COMMAND.value();
        var actual = stateManager.command(henry);
        assertEquals(expect,actual);
        assertEquals(expect,henry.getPointsVFP());
        assertEquals(7,actual.length());
    }

    @Test
    void command_is_idempotent(){
        stateManager.command(henry);
        var before = henry.getPointsVFP();
        IntStream.range(0,1000).forEach(e ->{
            stateManager.command(henry);
            var after = henry.getPointsVFP();
            assertEquals(after,before);
        });
    }

    @Test
    void reset_should_remove_head_and_add_underscore_at_the_tail(){
        /*given henry command yesterday*/
        stateManager.command(henry);
        var before = henry.getPointsVFP();
        /*when a new day comes*/
        stateManager.reset(henry);
        var after = henry.getPointsVFP();
        /*then*/
        assertNotEquals(before,after);
        assertEquals(COMMAND.value(),before.substring(before.length()-1));
        assertEquals(NOT_COMMAND.value(),after.substring(after.length()-1));
        assertEquals(COMMAND.value(),after.substring(after.length()-2,after.length()-1));
    }

    @Test
    void reset_is_idempotent_if_only_contain_underscore(){
        henry.setPointsVFP(StringUtils.repeat(NOT_COMMAND.value(),LENGTH));
        var before = henry.getPointsVFP();
        IntStream.range(0,1000).forEach(e -> {
            stateManager.reset(henry);
            var after = henry.getPointsVFP();
            assertEquals(before,after);
        });
    }

    @Test
    void should_automaticaly_set_status_vfp_command(){
        /*given*/
        henry.setPointsVFP(StringUtils.repeat(NOT_COMMAND.value(),LENGTH));

        IntStream.range(0,SEUIL-1)
                .forEach(e -> {
                    /*when*/
                    stateManager.command(henry);
                    /*then*/
                    assertFalse(henry.isStatusVFP());
                    stateManager.reset(henry);
                });
        /*when*/
        stateManager.command(henry);
        assertTrue(henry.isStatusVFP());
    }

    @Test
    void should_automaticaly_set_status_vfp_reset(){
        henry.setPointsVFP(StringUtils.repeat(COMMAND.value(),LENGTH));
        henry.setStatusVFP(true);
        IntStream.range(0,LENGTH-SEUIL).forEach(e -> {
            stateManager.reset(henry);
            assertTrue(henry.isStatusVFP());
        });

        IntStream.range(0,1000).forEach(e -> {
            stateManager.reset(henry);
            assertFalse(henry.isStatusVFP());
        });
    }

    @Test
    void should_automaticaly_set_status_vfp_parse(){
        henry.setPointsVFP(StringUtils.repeat(COMMAND.value(),LENGTH));
        stateManager.parse(henry);
        assertTrue(henry.isStatusVFP());

        henry.setPointsVFP(StringUtils.repeat(NOT_COMMAND.value(),LENGTH));
        stateManager.parse(henry);
        assertFalse(henry.isStatusVFP());

        henry.setPointsVFP("#####67");
        stateManager.parse(henry);
        assertTrue(henry.isStatusVFP());

        henry.setPointsVFP("1234567");
        stateManager.parse(henry);
        assertFalse(henry.isStatusVFP());
    }

    @Test
    void should_parse_command(){
        henry.setPointsVFP("");
        stateManager.command(henry);
        var expect = StringUtils.repeat(NOT_COMMAND.value(), LENGTH-1) + COMMAND.value();
        assertEquals(expect,henry.getPointsVFP());
    }

    @Test
    void should_parse_reset(){
        henry.setPointsVFP("1234567");
        stateManager.reset(henry);
        var expect = StringUtils.repeat(NOT_COMMAND.value(),LENGTH);
        assertEquals(expect,henry.getPointsVFP());
    }

}
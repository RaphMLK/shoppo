package fr.shoppo.mainmsinterface.infrastructure.action;

import fr.shoppo.mainmsinterface.domain.bo.stat.ReadingInput;
import fr.shoppo.mainmsinterface.domain.bo.stat.ReadingInputController;
import fr.shoppo.mainmsinterface.domain.bo.stat.StatLabel;
import fr.shoppo.mainmsinterface.domain.bo.stat.UserType;
import fr.shoppo.mainmsinterface.domain.service.StatService;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;

public class ReadStatAction {

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
}

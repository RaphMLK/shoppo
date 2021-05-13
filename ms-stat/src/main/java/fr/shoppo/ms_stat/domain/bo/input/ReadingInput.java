package fr.shoppo.ms_stat.domain.bo.input;

import fr.shoppo.ms_stat.domain.bo.StatLabel;
import fr.shoppo.ms_stat.domain.bo.UserType;

public class ReadingInput {
    StatLabel label;
    UserType userType;
    Object[] args;

    public ReadingInput() {
        /*binding*/
    }

    public StatLabel getLabel() {
        return label;
    }

    public void setLabel(StatLabel label) {
        this.label = label;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

    public String format(){
        if(label.needParameter() && args.length == 0) return "undefined args where label needs";
        return label.format(args);
    }
}

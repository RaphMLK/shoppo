package fr.shoppo.msclient.infrastructure.service;

import fr.shoppo.msclient.domain.bo.VFPStateFormat;
import fr.shoppo.msclient.domain.service.VFPStateManager;
import fr.shoppo.msclient.infrastructure.entity.Client;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import static fr.shoppo.msclient.domain.bo.VFPStateFormat.*;

@Service
public class VFPManagerImpl implements VFPStateManager {

    int vfpSeuil;

    @Override
    public String command(Client client) {
        var vfpState = parse(client); /*we want to parse the vfp state string before any treatment*/

        if(!vfpState.endsWith(COMMAND.value())){
            client.setPointsVFP(
                    new StringBuilder(vfpState)
                            .replace(LENGTH -1, LENGTH,COMMAND.value()) /* vfpState length always valued at 7*/
                            .toString()
            );
        }

        return statusVFP(client);
    }

    @Override
    public String parse(Client client){
        var vfpState = client.getPointsVFP();

        if(!vfpState.matches(VFPStateFormat.regex(true))){
            var subNewState = vfpState.replaceAll(VFPStateFormat.regex(false,0),NOT_COMMAND.value());
            var newState = StringUtils.leftPad(subNewState, LENGTH,NOT_COMMAND.value());
            /*if too large we want to keep the tail*/
           client.setPointsVFP(newState.length() > LENGTH ? newState.substring(newState.length()- LENGTH) : newState);
        }

        return statusVFP(client);
    }

    @Override
    public String reset(Client client){
        var vfpState = parse(client);

        if(!vfpState.matches(NOT_COMMAND.regex(LENGTH))) {/*unused to modify ____ by ____*/
            var newVfpState =
                    new StringBuilder(vfpState)
                            .append(NOT_COMMAND.value())
                            .substring(1);
            client.setPointsVFP(newVfpState);
        }


        return statusVFP(client);
    }

    @Value("${shoppo.vfp.seuil_status}")
    public void setVfpSeuil(int vfpSeuil) {
        this.vfpSeuil = vfpSeuil;
    }

    private String statusVFP(Client client){
        var state = client.getPointsVFP();
        client.setStatusVFP(StringUtils.countMatches(state, COMMAND.value()) >= vfpSeuil);
        return state;
    }
}

package fr.shoppo.msclient.infrastructure.entity.listener;

import fr.shoppo.msclient.domain.bo.AvantageVFP;
import fr.shoppo.msclient.domain.service.VFPStateManager;
import fr.shoppo.msclient.infrastructure.dao.VfpPlaqueDao;
import fr.shoppo.msclient.infrastructure.dao.VfpTransportDao;
import fr.shoppo.msclient.infrastructure.entity.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.PostLoad;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.util.Arrays;
import java.util.Map;

import static fr.shoppo.msclient.domain.bo.AvantageVFP.PARK;
import static fr.shoppo.msclient.domain.bo.AvantageVFP.TICKET;

@Component
public class VFPStateChecker {

    VFPStateManager vfpStateManager;
    VfpPlaqueDao vfpPlaqueDao;
    VfpTransportDao vfpTransportDao;

    @PreUpdate
    @PrePersist
    void beforeDataManipulation(Client client) {
        vfpStateManager.parse(client);
        /*Si avantage client est different de [NONE | USED] */
        if(Arrays.stream(new AvantageVFP[]{AvantageVFP.NONE,AvantageVFP.USED})
                .noneMatch(e -> e.equals(client.getAvantage()))){
            if(!client.isStatusVFP())
                client.setAvantage(AvantageVFP.LOSE);
            var avantage = client.getAvantage();
            avantage.run(client, Map.of(PARK.name(),vfpPlaqueDao,TICKET.name(),vfpTransportDao));
        }
    }

    @PostLoad
    void parseAtSelection(Client client){
        vfpStateManager.parse(client);
    }

    @Autowired
    public void setVfpStateManager(VFPStateManager vfpStateManager) {
        this.vfpStateManager = vfpStateManager;
    }

    @Autowired
    public void setVfpPlaqueDao(VfpPlaqueDao vfpPlaqueDao) {
        this.vfpPlaqueDao = vfpPlaqueDao;
    }

    @Autowired
    public void setVfpTransportDao(VfpTransportDao vfpTransportDao) {
        this.vfpTransportDao = vfpTransportDao;
    }
}

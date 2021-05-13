package fr.shoppo.ms_commerce.infrastructure.config.mq;

import fr.shoppo.ms_commerce.infrastructure.config.mq.myexchange.UpdateCommerceMqConfig;
import org.springframework.beans.factory.annotation.Autowired;

class UpdateCommerceMqConfigTest extends MqConfigTest{

    UpdateCommerceMqConfig updateCommerceMqConfig;

    @Override
    TimeToLiveConfiguration getInstance() {
        return this.updateCommerceMqConfig;
    }

    @Autowired
    void setUpdateCommerceMqConfig(UpdateCommerceMqConfig updateCommerceMqConfig) {
        this.updateCommerceMqConfig = updateCommerceMqConfig;
    }
}
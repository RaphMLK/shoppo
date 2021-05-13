package fr.shoppo.ms_commerce.infrastructure.config.mq;

import fr.shoppo.ms_commerce.infrastructure.config.mq.myexchange.CreateCommerceMqConfig;
import org.springframework.beans.factory.annotation.Autowired;

class CreateCommerceMqConfigTest extends MqConfigTest{

    CreateCommerceMqConfig createCommerceMqConfig;

    @Override
    TimeToLiveConfiguration getInstance() {
        return this.createCommerceMqConfig;
    }

    @Autowired
    void setCreateCommerceMqConfig(CreateCommerceMqConfig createCommerceMqConfig) {
        this.createCommerceMqConfig = createCommerceMqConfig;
    }
}
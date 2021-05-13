package fr.shoppo.msnotification.infrastructure.config.mq;

import org.springframework.beans.factory.annotation.Autowired;

class CreateClientMqConfigTest extends MqConfigTest{

    CreateCommerceMqConfig createCommerceMqConfig;

    @Override
    TimeToLiveConfiguration getInstance() {
        return this.createCommerceMqConfig;
    }

    @Autowired
    public void setCreateCommerceMqConfig(CreateCommerceMqConfig createCommerceMqConfig) {
        this.createCommerceMqConfig = createCommerceMqConfig;
    }
}
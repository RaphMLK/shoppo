package fr.shoppo.msnotification.infrastructure.config.mq;

import org.springframework.beans.factory.annotation.Autowired;

class AddCommercantMqConfigTest extends MqConfigTest{

    AddCommercantMqConfig addCommercantMqConfig;

    @Override
    TimeToLiveConfiguration getInstance() {
        return this.addCommercantMqConfig;
    }

    @Autowired
    public void setAddCommercantMqConfig(AddCommercantMqConfig addCommercantMqConfig) {
        this.addCommercantMqConfig = addCommercantMqConfig;
    }
}
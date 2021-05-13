package fr.shoppo.ms_commerce.infrastructure.config.mq;

import fr.shoppo.ms_commerce.infrastructure.config.mq.myexchange.AddCommercantMqConfig;
import org.springframework.beans.factory.annotation.Autowired;

class AddCommercantMqConfigTest extends MqConfigTest {

    AddCommercantMqConfig addCommercantMqConfig;

    @Override
    TimeToLiveConfiguration getInstance() {
        return this.addCommercantMqConfig;
    }

    @Autowired
    void setAddCommercantMqConfig(AddCommercantMqConfig addCommercantMqConfig) {
        this.addCommercantMqConfig = addCommercantMqConfig;
    }
}
package fr.shoppo.ms_commerce.infrastructure.config.mq;

import fr.shoppo.ms_commerce.infrastructure.config.mq.myexchange.FindCommercantMqConfig;
import org.springframework.beans.factory.annotation.Autowired;

class FindCommercantMqConfigTest extends MqConfigTest{

    FindCommercantMqConfig findCommercantMqConfig;

    @Override
    TimeToLiveConfiguration getInstance() {
        return this.findCommercantMqConfig;
    }

    @Autowired
    void setFindCommercantMqConfigTest(FindCommercantMqConfig findCommercantMqConfig) {
        this.findCommercantMqConfig = findCommercantMqConfig;
    }
}
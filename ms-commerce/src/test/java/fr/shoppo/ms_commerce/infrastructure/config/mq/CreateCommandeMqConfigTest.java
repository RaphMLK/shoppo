package fr.shoppo.ms_commerce.infrastructure.config.mq;

import fr.shoppo.ms_commerce.infrastructure.config.mq.myexchange.CreateCommandeMqConfig;
import org.springframework.beans.factory.annotation.Autowired;

class CreateCommandeMqConfigTest extends MqConfigTest{

    CreateCommandeMqConfig createCommandeMqConfig;

    @Override
    TimeToLiveConfiguration getInstance() {
        return this.createCommandeMqConfig;
    }

    @Autowired
    void setCreateCommandeMqConfig(CreateCommandeMqConfig createCommandeMqConfig) {
        this.createCommandeMqConfig = createCommandeMqConfig;
    }
}
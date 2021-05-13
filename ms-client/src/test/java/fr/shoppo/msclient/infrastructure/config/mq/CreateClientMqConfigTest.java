package fr.shoppo.msclient.infrastructure.config.mq;

import fr.shoppo.msclient.infrastructure.config.mq.myexchange.CreateClientMqConfig;
import org.springframework.beans.factory.annotation.Autowired;


class CreateClientMqConfigTest extends MqConfigTest{

    CreateClientMqConfig createClientMqConfig;

    @Override
    TimeToLiveConfiguration getInstance() {
        return this.createClientMqConfig;
    }

    @Autowired
    public void setCreateClientMqConfig(CreateClientMqConfig createClientMqConfig) {
        this.createClientMqConfig = createClientMqConfig;
    }
}
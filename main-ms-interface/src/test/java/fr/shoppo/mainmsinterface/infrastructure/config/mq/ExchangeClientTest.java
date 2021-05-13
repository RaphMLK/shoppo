package fr.shoppo.mainmsinterface.infrastructure.config.mq;

import org.springframework.beans.factory.annotation.Autowired;

class ExchangeClientTest extends MqConfigTest{

    ExchangeClientMqConfig exchangeClient;

    @Override
    TimeToLiveConfiguration getInstance() {
        return this.exchangeClient;
    }

    @Autowired
    public void setExchangeClient(ExchangeClientMqConfig exchangeClient) {
        this.exchangeClient = exchangeClient;
    }
}
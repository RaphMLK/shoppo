package fr.shoppo.mainmsinterface.infrastructure.config.mq;

import org.springframework.beans.factory.annotation.Autowired;

class ExchangeAdminTest extends MqConfigTest {

    ExchangeAdminMqConfig exchangeAdmin;

    @Override
    TimeToLiveConfiguration getInstance() {
        return this.exchangeAdmin;
    }

    @Autowired
    public void setExchangeAdmin(ExchangeAdminMqConfig exchangeAdmin) {
        this.exchangeAdmin = exchangeAdmin;
    }
}
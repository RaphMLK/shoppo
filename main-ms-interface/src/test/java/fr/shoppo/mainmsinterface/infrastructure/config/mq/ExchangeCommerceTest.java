package fr.shoppo.mainmsinterface.infrastructure.config.mq;

import org.springframework.beans.factory.annotation.Autowired;

class ExchangeCommerceTest extends MqConfigTest {

    ExchangeCommerceMqConfig exchangeCommerce;

    @Override
    TimeToLiveConfiguration getInstance() {
        return this.exchangeCommerce;
    }

    @Autowired
    public void setExchangeCommerce(ExchangeCommerceMqConfig exchangeCommerce) {
        this.exchangeCommerce = exchangeCommerce;
    }
}
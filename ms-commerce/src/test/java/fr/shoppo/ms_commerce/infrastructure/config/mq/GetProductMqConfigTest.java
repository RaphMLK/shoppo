package fr.shoppo.ms_commerce.infrastructure.config.mq;

import fr.shoppo.ms_commerce.infrastructure.config.mq.myexchange.GetProductMqConfig;
import org.springframework.beans.factory.annotation.Autowired;

class GetProductMqConfigTest extends MqConfigTest{

    GetProductMqConfig getProductMqConfig;

    @Override
    TimeToLiveConfiguration getInstance() {
        return this.getProductMqConfig;
    }

    @Autowired
    void setGetProductMqConfigTest(GetProductMqConfig getProductMqConfig) {
        this.getProductMqConfig = getProductMqConfig;
    }
}
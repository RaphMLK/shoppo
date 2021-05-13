package fr.shoppo.ms_commerce.infrastructure.config.mq;

import fr.shoppo.ms_commerce.infrastructure.config.mq.myexchange.GetProductsCommerceMqConfig;
import org.springframework.beans.factory.annotation.Autowired;

class GetProductsCommerceMqConfigTest extends MqConfigTest{

    GetProductsCommerceMqConfig getProductsCommerceMqConfig;

    @Override
    TimeToLiveConfiguration getInstance() {
        return this.getProductsCommerceMqConfig;
    }

    @Autowired
    void setGetProductsCommerceMqConfig(GetProductsCommerceMqConfig getProductsCommerceMqConfig) {
        this.getProductsCommerceMqConfig = getProductsCommerceMqConfig;
    }
}
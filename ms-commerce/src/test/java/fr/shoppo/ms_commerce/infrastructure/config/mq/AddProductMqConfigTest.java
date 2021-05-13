package fr.shoppo.ms_commerce.infrastructure.config.mq;

import fr.shoppo.ms_commerce.infrastructure.config.mq.myexchange.AddProductMqConfig;
import org.springframework.beans.factory.annotation.Autowired;

class AddProductMqConfigTest extends MqConfigTest {

    AddProductMqConfig addProductMqConfig;

    @Override
    TimeToLiveConfiguration getInstance() {
        return this.addProductMqConfig;
    }

    @Autowired
    void setAddProductMqConfig(AddProductMqConfig addProductMqConfig){
        this.addProductMqConfig = addProductMqConfig;
    }
}
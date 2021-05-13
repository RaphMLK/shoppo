package fr.shoppo.ms_commerce.infrastructure.config.mq;

import fr.shoppo.ms_commerce.infrastructure.config.mq.myexchange.EditProductMqConfig;
import org.springframework.beans.factory.annotation.Autowired;

class EditProductMqConfigTest extends MqConfigTest{

    EditProductMqConfig editProductMqConfig;

    @Override
    TimeToLiveConfiguration getInstance() {
        return this.editProductMqConfig;
    }

    @Autowired
    void setEditProductMqConfigTest(EditProductMqConfig editProductMqConfig) {
        this.editProductMqConfig = editProductMqConfig;
    }
}
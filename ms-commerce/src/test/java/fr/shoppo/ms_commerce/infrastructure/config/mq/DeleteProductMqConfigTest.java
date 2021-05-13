package fr.shoppo.ms_commerce.infrastructure.config.mq;

import fr.shoppo.ms_commerce.infrastructure.config.mq.myexchange.DeleteProductMqConfig;
import org.springframework.beans.factory.annotation.Autowired;

class DeleteProductMqConfigTest extends MqConfigTest{

    DeleteProductMqConfig deleteProductMqConfig;

    @Override
    TimeToLiveConfiguration getInstance() {
        return this.deleteProductMqConfig;
    }

    @Autowired
    void setDeleteProductMqConfigTest(DeleteProductMqConfig deleteProductMqConfig) {
        this.deleteProductMqConfig = deleteProductMqConfig;
    }
}
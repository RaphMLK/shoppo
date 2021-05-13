package fr.shoppo.ms_commerce.infrastructure.config.mq;

import fr.shoppo.ms_commerce.infrastructure.config.mq.myexchange.UpdateQuantityProduitMqConfig;
import org.springframework.beans.factory.annotation.Autowired;

class UpdateQuantityProduitMqConfigTest extends MqConfigTest{

    UpdateQuantityProduitMqConfig updateQuantityProduitMqConfig;

    @Override
    TimeToLiveConfiguration getInstance() {
        return this.updateQuantityProduitMqConfig;
    }

    @Autowired
    void setUpdateQuantityProduitMqConfig(UpdateQuantityProduitMqConfig updateQuantityProduitMqConfig) {
        this.updateQuantityProduitMqConfig = updateQuantityProduitMqConfig;
    }
}
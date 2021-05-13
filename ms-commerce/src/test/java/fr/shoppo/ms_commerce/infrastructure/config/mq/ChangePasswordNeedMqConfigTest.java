package fr.shoppo.ms_commerce.infrastructure.config.mq;

import fr.shoppo.ms_commerce.infrastructure.config.mq.myexchange.ChangePasswordNeedMqConfig;
import org.springframework.beans.factory.annotation.Autowired;

public class ChangePasswordNeedMqConfigTest extends MqConfigTest{

    ChangePasswordNeedMqConfig changePasswordNeedMqConfig;

    @Override
    TimeToLiveConfiguration getInstance() {
        return this.changePasswordNeedMqConfig;
    }

    @Autowired
    void setChangePasswordNeedMqConfig(ChangePasswordNeedMqConfig changePasswordNeedMqConfig) {
        this.changePasswordNeedMqConfig = changePasswordNeedMqConfig;
    }
}

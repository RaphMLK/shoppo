package fr.shoppo.msclient.infrastructure.config.mq;

import fr.shoppo.msclient.infrastructure.config.mq.myexchange.ChangePasswordNeedMqConfig;
import org.springframework.beans.factory.annotation.Autowired;

class ChangePasswordNeedMqConfigTest extends MqConfigTest{

    ChangePasswordNeedMqConfig changePasswordNeedMqConfig;

    @Override
    TimeToLiveConfiguration getInstance() {
        return this.changePasswordNeedMqConfig;
    }

    @Autowired
    public void setCreateChangePasswordNeedMqConfig(ChangePasswordNeedMqConfig changePasswordNeedMqConfig) {
        this.changePasswordNeedMqConfig = changePasswordNeedMqConfig;
    }
}
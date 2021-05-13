package fr.shoppo.msclient.infrastructure.config.mq;

import fr.shoppo.msclient.infrastructure.config.mq.myexchange.ResetPasswordMqConfig;
import org.springframework.beans.factory.annotation.Autowired;

class ResetPasswordMqConfigTest extends MqConfigTest{

    ResetPasswordMqConfig resetPasswordMqConfig;

    @Override
    TimeToLiveConfiguration getInstance() {
        return this.resetPasswordMqConfig;
    }

    @Autowired
    public void setResetPasswordMqConfig(ResetPasswordMqConfig resetPasswordMqConfig) {
        this.resetPasswordMqConfig = resetPasswordMqConfig;
    }
}
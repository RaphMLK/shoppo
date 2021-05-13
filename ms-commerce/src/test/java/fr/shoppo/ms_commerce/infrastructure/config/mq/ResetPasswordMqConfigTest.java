package fr.shoppo.ms_commerce.infrastructure.config.mq;

import fr.shoppo.ms_commerce.infrastructure.config.mq.myexchange.ResetPasswordMqConfig;
import org.springframework.beans.factory.annotation.Autowired;

class ResetPasswordMqConfigTest extends MqConfigTest {

    ResetPasswordMqConfig resetPasswordMqConfig;

    @Override
    TimeToLiveConfiguration getInstance() {
        return this.resetPasswordMqConfig;
    }

    @Autowired
    void setResetPasswordMqConfig(ResetPasswordMqConfig resetPasswordMqConfig) {
        this.resetPasswordMqConfig = resetPasswordMqConfig;
    }
}
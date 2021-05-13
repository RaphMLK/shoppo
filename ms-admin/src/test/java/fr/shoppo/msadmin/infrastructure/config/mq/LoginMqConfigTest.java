package fr.shoppo.msadmin.infrastructure.config.mq;

import fr.shoppo.msadmin.infrastructure.config.mq.myexchange.LoginMqConfig;
import org.springframework.beans.factory.annotation.Autowired;

class LoginMqConfigTest extends MqConfigTest {

    LoginMqConfig loginMqConfig;

    @Override
    TimeToLiveConfiguration getInstance() {
        return this.loginMqConfig;
    }

    @Autowired
    void setLoginMqConfig(LoginMqConfig loginMqConfig) {
        this.loginMqConfig = loginMqConfig;
    }
}
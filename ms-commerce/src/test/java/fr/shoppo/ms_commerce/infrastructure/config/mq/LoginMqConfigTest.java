package fr.shoppo.ms_commerce.infrastructure.config.mq;

import fr.shoppo.ms_commerce.infrastructure.config.mq.myexchange.LoginMqConfig;
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
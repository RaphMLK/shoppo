package fr.shoppo.msclient.infrastructure.config.mq;

import fr.shoppo.msclient.infrastructure.config.mq.myexchange.LoginMqConfig;
import org.springframework.beans.factory.annotation.Autowired;

public class LoginMqConfigTest extends MqConfigTest {

    LoginMqConfig loginMqConfig;

    @Override
    TimeToLiveConfiguration getInstance() {
        return this.loginMqConfig;
    }

    @Autowired
    public void setLoginMqConfig(LoginMqConfig loginMqConfig) {
        this.loginMqConfig = loginMqConfig;
    }
}

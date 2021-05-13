package fr.shoppo.ms_commerce.infrastructure.config;

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.HttpClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

@Configuration
public class RestConfiguration {
    @Bean
    RestTemplate restTemplate(){
        return new RestTemplate();
    }

    @Bean
    public RestTemplate secureRestTemplate() throws KeyStoreException,
                                                    NoSuchAlgorithmException,
                                                    KeyManagementException
    {
        TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;

        var sslContext = org.apache.http.ssl.SSLContexts.custom()
                .loadTrustMaterial(null, acceptingTrustStrategy)
                .build();

        var csf = new SSLConnectionSocketFactory(sslContext);

        var httpClient = HttpClients.custom()
                .setSSLSocketFactory(csf)
                .build();

        var requestFactory =
                new HttpComponentsClientHttpRequestFactory();

        requestFactory.setHttpClient(httpClient);
        return new RestTemplate(requestFactory);
    }
}

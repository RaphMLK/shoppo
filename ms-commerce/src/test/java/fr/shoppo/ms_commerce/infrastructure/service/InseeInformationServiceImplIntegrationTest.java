package fr.shoppo.ms_commerce.infrastructure.service;

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.HttpClients;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.client.RestTemplate;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

import static org.assertj.core.api.Assertions.assertThat;

@TestPropertySource(properties = {
        "insee.url.nomenclature=https://api.insee.fr/metadonnees/nomenclatures/v1",
        "insee.url.sirene=https://api.insee.fr/entreprises/sirene/V3",
        "insee.authorization.bearer=2cf2f136-550b-3320-8cbf-8e77cb993fe0"
})
@RestClientTest(InseeInformationServiceImpl.class)
@AutoConfigureWebClient(registerRestTemplate = true)
class InseeInformationServiceImplIntegrationTest {

    InseeInformationServiceImpl inseeInformationService;

    @Value("${insee.url.nomenclature}")
    String nomenclatureServiceUrl;
    @Value("${insee.url.sirene}")
    String sirenServiceUrl;
    @Value("${insee.authorization.bearer}")
    String bearer;

    @BeforeEach
    void setup() throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        inseeInformationService = new InseeInformationServiceImpl();
        inseeInformationService.setSecureRestTemplate(secureRestTemplate());
        inseeInformationService.setNomenclatureServiceUrl(nomenclatureServiceUrl);
        inseeInformationService.setSirenServiceUrl(sirenServiceUrl);
        inseeInformationService.setBearer(bearer);
    }

    @Test
    void existingCodeNaf() {
        var codeNafString = "27.40Z";

        var codeNaf = inseeInformationService.existingCodeNaf(codeNafString);

        assertThat(codeNaf).isNotNull();
    }

    @Test
    void findCommerce(){
        var codeSiret = "13002358300011";

        var commerce = inseeInformationService.findCommerceBySiret(codeSiret);

        assertThat(commerce).isNotNull();
    }


    /*
    * Insee se base sur un HTTPS, le context ne veut pas s'appliquer en cas de test alors on doit dupliquer.
    * */
    RestTemplate secureRestTemplate() throws KeyStoreException,
            NoSuchAlgorithmException,
            KeyManagementException {
        var acceptingTrustStrategy = (TrustStrategy) (X509Certificate[] chain, String authType) -> true;

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
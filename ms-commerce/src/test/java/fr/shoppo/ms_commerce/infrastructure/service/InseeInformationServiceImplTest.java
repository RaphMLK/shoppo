package fr.shoppo.ms_commerce.infrastructure.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class InseeInformationServiceImplTest {

    InseeInformationServiceImpl inseeInformationService;
    RestTemplate restTemplate;
    @BeforeEach
    void setup(){
        inseeInformationService = new InseeInformationServiceImpl();
        restTemplate = mock(RestTemplate.class);

        inseeInformationService.setSecureRestTemplate(restTemplate);
        when(restTemplate.getForObject(any(),any(),any(),any(),any())).thenReturn(new Object());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "123",
            "1234567890123a",
            "azertyuiopqsdf",
            "1234567890123456",
            "null"
    })
    void should_refuse_if_siret_is_invalid(String codeSiret){
        var commerce = inseeInformationService.findCommerceBySiret(codeSiret);

        assertThat(commerce).isNull();
    }

    @Test
    void should_refuse_if_siret_is_null(){
        var commerce = inseeInformationService.findCommerceBySiret(null);

        assertThat(commerce).isNull();
    }
}
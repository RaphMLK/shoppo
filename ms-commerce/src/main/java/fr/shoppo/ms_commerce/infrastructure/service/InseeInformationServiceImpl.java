package fr.shoppo.ms_commerce.infrastructure.service;

import fr.shoppo.ms_commerce.domain.service.InseeInformationService;
import fr.shoppo.ms_commerce.infrastructure.entity.CodeNaf;
import fr.shoppo.ms_commerce.infrastructure.entity.Commerce;
import fr.shoppo.ms_commerce.infrastructure.json.JsonManagerComponent;
import fr.shoppo.ms_commerce.infrastructure.mapper.CommerceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.function.Function;

import static java.lang.String.format;


@Service
public class InseeInformationServiceImpl implements InseeInformationService {

    RestTemplate secureRestTemplate;

    static String nomenclatureServiceUrl;
    static final String NAF_CODE = "/codes/nafr2/sousClasse/{code}";

    static String sirenServiceUrl;
    static final String SIRET_INFORMATION = "/siret/{siret}";

    static String bearer;

    @Override
    public CodeNaf existingCodeNaf(String codeNaf){

        return getRequestFor(
                HttpEntity::getBody,
                CodeNaf.class,
                format("%s%s",nomenclatureServiceUrl,NAF_CODE),
                codeNaf
        );
    }

    @Override
    public Commerce findCommerceBySiret(String siretCode) {
        if(siretCode != null && siretCode.matches("[0-9]{14}")) // on evite les traitements inutiles
            return getRequestFor(
                    responseEntity -> CommerceMapper.fromJSONObject(
                            JsonManagerComponent.tryToGet(vide->
                                    new JSONObject(responseEntity.getBody()),JSONObject.class)
                    ),
                    String.class,
                    format("%s%s",sirenServiceUrl,SIRET_INFORMATION),
                    siretCode
            );
        return null;
    }

    private <T,K> T getRequestFor(
            Function<ResponseEntity<K>,T> convert,
            Class<K> returnType,
            String url,
            Object... args
    ){
        var headers = new HttpHeaders();
        headers.setBearerAuth(bearer);
        headers.setContentType(MediaType.APPLICATION_JSON);

        var httpRequest = new HttpEntity<>(headers);

        var responseEntity = secureRestTemplate.exchange(
                url,
                HttpMethod.GET,
                httpRequest,
                returnType,
                args
        );
        return convert.apply(responseEntity);
    }


    @Autowired
    public void setSecureRestTemplate(RestTemplate secureRestTemplate) {
        this.secureRestTemplate = secureRestTemplate;
    }

    @Value("${insee.url.nomenclature}")
    public void setNomenclatureServiceUrl(String nomenclatureServiceUrl) {
        InseeInformationServiceImpl.nomenclatureServiceUrl = nomenclatureServiceUrl;
    }

    @Value("${insee.url.sirene}")
    public void setSirenServiceUrl(String sirenServiceUrl) {
        InseeInformationServiceImpl.sirenServiceUrl = sirenServiceUrl;
    }

    @Value("${insee.authorization.bearer}")
    public void setBearer(String bearer) {
        InseeInformationServiceImpl.bearer = bearer;
    }
}

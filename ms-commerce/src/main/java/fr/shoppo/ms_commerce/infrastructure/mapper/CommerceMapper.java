package fr.shoppo.ms_commerce.infrastructure.mapper;

import fr.shoppo.ms_commerce.infrastructure.entity.CodeNaf;
import fr.shoppo.ms_commerce.infrastructure.entity.Commerce;
import fr.shoppo.ms_commerce.infrastructure.entity.embeddable.Adresse;
import fr.shoppo.ms_commerce.infrastructure.json.JsonManagerComponent;
import org.springframework.boot.configurationprocessor.json.JSONObject;

import static java.lang.String.format;

public class CommerceMapper {

    public static Commerce fromJSONObject(JSONObject json){
        var commerce = new Commerce();
        var adresse = new Adresse();
        var codeNaf = new CodeNaf();

        var etablissement = JsonManagerComponent.tryToGet(vide ->
                json.getJSONObject("etablissement"),
                JSONObject.class);

        var uniteLegale = JsonManagerComponent.tryToGet(vide ->
                etablissement.getJSONObject("uniteLegale"),
                JSONObject.class);
        var adresseEtablissement = JsonManagerComponent.tryToGet(vide ->
                etablissement.getJSONObject("adresseEtablissement"),
                JSONObject.class);

        commerce.setSiretCode(JsonManagerComponent.tryToGet(vide ->
                etablissement.getString("siret")));
        commerce.setSirenCode(JsonManagerComponent.tryToGet(vide ->
                etablissement.getString("siren")));
        commerce.setEnseigne(JsonManagerComponent.tryToGet(vide ->
                uniteLegale.getString("denominationUniteLegale")));

        adresse.setCodePostal(JsonManagerComponent.tryToGet(vide ->
                adresseEtablissement.getString("codePostalEtablissement")));
        adresse.setLibelleRue(
                format("%s %s",
                        JsonManagerComponent.tryToGet(vide ->
                                        adresseEtablissement.getString("typeVoieEtablissement"),String.class),
                        JsonManagerComponent.tryToGet(vide ->
                                adresseEtablissement.getString("libelleVoieEtablissement")))
        );
        adresse.setNumeroRue(
                format("%s %s",
                        JsonManagerComponent.tryToGet(vide ->
                                        adresseEtablissement.getString("numeroVoieEtablissement")),

                        JsonManagerComponent.tryToGet(vide ->
                                         adresseEtablissement.getString("complementAdresseEtablissement"),
                                String.class)
                )
        );

        adresse.setVille(JsonManagerComponent.tryToGet(vide ->
                adresseEtablissement.getString("libelleCommuneEtablissement")));

        codeNaf.setCode(JsonManagerComponent.tryToGet(vide ->
                uniteLegale.getString("activitePrincipaleUniteLegale")));

        commerce.setCodeNaf(codeNaf);
        commerce.setAdresse(adresse);
        return commerce;
    }

    CommerceMapper() throws IllegalAccessException {
        throw new IllegalAccessException("This is static context");
    }
}
